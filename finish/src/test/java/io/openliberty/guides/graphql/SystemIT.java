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
package io.openliberty.guides.graphql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SystemIT {
    
    private static String URL;
    
    @BeforeAll
    public static void setUp() {
        // tag::client[]
        String port = System.getProperty("http.port");
        URL = "http://localhost:" + port + "/graphql";
        // end::client[]
    }
    
    // tag::test1[]
    @Test
    // end::test1[]
    @Order(1)
    // tag::testGet[]
    public void testGetSystem() throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(URL);
        StringEntity entity = new StringEntity(
                "{ \"query\": " + 
                    "\"query { " + 
                        "system { " + 
                            "username timezone " +
                            "java { version vendor } " +
                            "operatingSystem {arch name version} " +
                        "} " +
                    "}" +
                "\"}", 
                ContentType.create("application/json", Consts.UTF_8));
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        assertNotNull(response.getEntity(), "No system info received");
        assertFalse(EntityUtils.toString(response.getEntity()).contains("error"), 
                "Response has errors");
    }
    // end::testGet[]
    
    // tag::test2[]
    @Test
    // end::test2[]
    @Order(2)
    // tag::testEdit[]
    public void testEditNote() throws ClientProtocolException, IOException {
        String expectedNote = "Time: " + System.currentTimeMillis();
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost mutation = new HttpPost(URL);
        StringEntity mutationBody = new StringEntity(
                "{ \"query\": " +
                    "\"mutation ($noteArg: String!) {" +
                        "editNote(note: $noteArg)" +
                    "}\", " +
                    "\"variables\": {\"" +
                        "noteArg\": \""+ expectedNote +"\"" +
                    "}" +
                "}", 
                ContentType.create("application/json", 
                Consts.UTF_8));
        mutation.setEntity(mutationBody);
        HttpResponse mutateResponse = httpClient.execute(mutation);
        assertNotNull(mutateResponse.getEntity());
        
        HttpPost query = new HttpPost(URL);
        StringEntity queryBody = new StringEntity(
                "{ \"query\": " + 
                    "\"query { " + 
                        "system { " + 
                            "note" + 
                        "} " + 
                    "}" +
                "\"}", 
                ContentType.create("application/json", 
                Consts.UTF_8));
        query.setEntity(queryBody);
        HttpResponse queryResponse = httpClient.execute(query);
        assertNotNull(queryResponse.getEntity(), "No system info received");
        assertTrue(EntityUtils.toString(queryResponse.getEntity()).contains(expectedNote), 
                "Response does not contain expected note");
    }
    // end::testEdit[]
}
