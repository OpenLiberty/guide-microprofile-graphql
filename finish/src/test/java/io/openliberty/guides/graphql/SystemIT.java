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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

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

    private static String url;

    private static final Jsonb JSONB = JsonbBuilder.create();

    @BeforeAll
    public static void setUp() {
        String port = System.getProperty("http.port");
        url = "http://localhost:" + port + "/graphql";
    }

    @SuppressWarnings("unchecked")
    // tag::test1[]
    @Test
    // end::test1[]
    @Order(1)
    // tag::testGet[]
    public void testGetSystem() throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(
            "{ \"query\": " +
                "\"query { " +
                    "system { " +
                      "username timezone " +
                      "java { version vendorName } " +
                      "operatingSystem {arch name version} " +
                    "} " +
                "}\" " +
            "}",
            ContentType.create("application/json", Consts.UTF_8));
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        String responseString = EntityUtils.toString(response.getEntity());
        Map<String, Object> result = JSONB.fromJson(responseString, Map.class);
        assertTrue(result.containsKey("data"), "No response data received");
        assertFalse(result.containsKey("error"), "Response has errors");
        Map<String, Object> data = (Map<String, Object>) result.get("data");
        Map<String, Object> system = (Map<String, Object>) data.get("system");
        assertNotNull(system, "Response is not for system query");
        // Verify fields
        Properties systemProperties = System.getProperties();
        assertEquals(systemProperties.getProperty("user.name"),
                (String) system.get("username"),
                "Usernames don't match");
    }
    // end::testGet[]

    @SuppressWarnings("unchecked")
    // tag::test2[]
    @Test
    // end::test2[]
    @Order(2)
    // tag::testEdit[]
    public void testEditNote() throws ClientProtocolException, IOException {
        String expectedNote = "Time: " + System.currentTimeMillis();

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost mutation = new HttpPost(url);
        StringEntity mutationBody = new StringEntity(
          "{ " + 
            "\"query\": \"mutation ($noteArg: String!) {editNote(note: $noteArg)}\"," +
            "\"variables\": {\"noteArg\": \"" + expectedNote + "\"} " +
          "}",
          ContentType.create("application/json", Consts.UTF_8));
        mutation.setEntity(mutationBody);
        HttpResponse mutateResponse = httpClient.execute(mutation);
        String mutateResString = EntityUtils.toString(mutateResponse.getEntity());
        Map<String, Object> mutateJson = JSONB.fromJson(mutateResString, Map.class);
        assertFalse(mutateJson.containsKey("error"), "Mutation has errors");

        HttpPost query = new HttpPost(url);
        StringEntity queryBody = new StringEntity(
            "{ \"query\": " +
                "\"query { " +
                    "system { note } " +
                "}\"" +
            "}",
            ContentType.create("application/json", Consts.UTF_8));
        query.setEntity(queryBody);
        HttpResponse queryResponse = httpClient.execute(query);
        String queryResponseString = EntityUtils.toString(queryResponse.getEntity());
        Map<String, Object> queryJson = JSONB.fromJson(queryResponseString, Map.class);
        assertTrue(queryJson.containsKey("data"), "No response data received");
        assertFalse(queryJson.containsKey("error"), "Response has errors");
        Map<String, Object> data = (Map<String, Object>) queryJson.get("data");
        Map<String, Object> system = (Map<String, Object>) data.get("system");
        assertNotNull(system, "Response is not for system query");
        assertEquals(expectedNote,
                (String) system.get("note"),
                "Response does not contain expected note");
    }
    // end::testEdit[]
}
