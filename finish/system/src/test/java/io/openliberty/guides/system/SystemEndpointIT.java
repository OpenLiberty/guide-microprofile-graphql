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
package io.openliberty.guides.system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class SystemEndpointIT {

    private static final String PORT = System.getProperty("http.port");
    private static final String URL = "http://localhost:" + PORT + "/";
    private static final String NOTE = "test edit note";

    private static Client client;

    @BeforeAll
    private static void setup() {
        client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);
    }

    @Test
    @Order(1)
    public void testEditNote() throws MalformedURLException {
        WebTarget target = client.target(URL + "system/properties/note");
        Response response = target.request().post(Entity.text("test edit note"));
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());
        response.close();
    }

    @Test
    @Order(2)
    public void testGetProperties() throws MalformedURLException {
        WebTarget target = client.target(URL + "system/properties");
        Response response = target.request().get();
        assertEquals(200, response.getStatus(),
                     "Incorrect response code from " + target.getUri().getPath());

        JsonObject system = response.readEntity(JsonObject.class);
        assertEquals(System.getProperty("user.name"),
                     system.getString("username"),
                     "The system property for for the local and remote "
                     + "user name should match");
        assertEquals(NOTE,
                     system.getString("note"),
                     "The note was not set correctly");
        JsonObject os = system.getJsonObject("operatingSystem");
        assertEquals(System.getProperty("os.name"),
                     os.getString("name"),
                     "The system property for the local and remote "
                     + "OS name should match");
        JsonObject java = system.getJsonObject("java");
        assertEquals(System.getProperty("java.vendor"),
                     java.getString("vendor"),
                     "The system property for the local and remote "
                     + "java vendor should match");

        response.close();
    }
}
