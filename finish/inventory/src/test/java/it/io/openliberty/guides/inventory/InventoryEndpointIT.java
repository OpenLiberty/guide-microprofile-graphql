// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package it.io.openliberty.guides.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class InventoryEndpointIT {

    private static final String PORT = System.getProperty("http.port");
    private static final String URL = "http://localhost:" + PORT + "/";

    private static Client client;

    @BeforeAll
    private static void setup() {
        client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);
    }

    @AfterAll
    public static void teardown() {
        client.close();
    }

    @Test
    @Order(1)
    public void testInventorySystemsLocalhost() {
        WebTarget target = client.target(URL + "inventory/systems/localhost");
        Response response = target.request().get();
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        validSystem(response.readEntity(JsonObject.class));
        response.close();
    }

    @Test
    @Order(2)
    public void testInventorySystems() {
        WebTarget target = client.target(URL + "inventory/systems");
        Response response = target.request().get();
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        JsonObject systems = response.readEntity(JsonObject.class);
        JsonArray systemsList = systems.getJsonArray("systems");
        assertEquals(1,
            systems.getJsonNumber("total").intValue(),
            "total should match: " + systems);
        validSystem(systemsList.get(0).asJsonObject());
        response.close();
    }

    @Test
    @Order(3)
    public void testInventorySystemsLocalhostNote() {
        WebTarget target = client.target(URL + "inventory/systems/localhost/note");
        Response response = target.request().post(Entity.text("test edit note"));
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        target = client.target(URL + "inventory/systems/localhost");
        response = target.request().get();
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        JsonObject system = response.readEntity(JsonObject.class);
        assertEquals("test edit note",
                system.getString("note"),
                     "note should match: " + system);
        response.close();
    }

    @Test
    @Order(4)
    public void testInventorySystemsSystemLoad() {
        WebTarget target = client.target(URL + "inventory/systems/systemLoad");
        Response response = target.request().get();
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        JsonArray systemLoads = response.readEntity(JsonArray.class);
        assertEquals(1,
                systemLoads.size(),
                "array size should match: " + systemLoads);
        JsonObject systemLoad = systemLoads.get(0).asJsonObject();
        assertEquals("localhost",
        		systemLoad.getString("hostname"),
                     "hostname should match: " + systemLoad);
        JsonObject data = systemLoads.get(0).asJsonObject().getJsonObject("data");
        assertNotNull(data.getJsonNumber("heapUsed"),
                     "heapUsed should not be null: " + systemLoad);
        assertNotNull(data.getJsonNumber("loadAverage"),
                "loadAverage should not be null: " + systemLoad);
        response.close();
    }

    private void validSystem(JsonObject system) {
        assertEquals(System.getProperty("user.name"),
                system.getString("username"),
                     "user.name should match: " + system);
        JsonObject java = system.getJsonObject("java");
        assertEquals(System.getProperty("java.vendor"),
                java.getString("vendor"),
                     "vendor should match: " + java);
        JsonObject operatingSystem = system.getJsonObject("operatingSystem");
        assertEquals(System.getProperty("os.arch"),
                operatingSystem.getString("arch"),
                     "arch should match: " + operatingSystem);
    }

}
