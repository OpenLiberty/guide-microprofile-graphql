// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.openliberty.guides.graphql.client.SystemServiceAPI;
import io.openliberty.guides.graphql.models.SystemInfo;
import io.smallrye.graphql.client.typesafe.api.GraphQlClientBuilder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SystemIT {
    
    // tag::inject[]
    @Inject
    private static SystemServiceAPI systemServiceApi;
    // end::inject[]
    
    @BeforeAll
    public static void setUp() {
        // tag::client[]
        systemServiceApi = GraphQlClientBuilder
                .newBuilder()
                .endpoint("http://localhost:9080/graphql")
                .build(SystemServiceAPI.class);
        // end::client[]
    }
    
    // tag::test1[]
    @Test
    // end::test1[]
    @Order(1)
    // tag::testGet[]
    public void testGetSystem() {
        SystemInfo sys = systemServiceApi.getSystemInfo();
        assertNotNull(sys, "No system info received");
    }
    // end::testGet[]
    
    // tag::test2[]
    @Test
    // end::test2[]
    @Order(2)
    // tag::testEdit[]
    public void testEditNote() {
        String expectedNote = "Time: " + System.currentTimeMillis();
        assertTrue(systemServiceApi.editNote(expectedNote));
        String actualNote = systemServiceApi.getSystemInfo().getNote();
        assertEquals(expectedNote, actualNote, "Returned note not the same as input note");
    }
    // end::testEdit[]
}
