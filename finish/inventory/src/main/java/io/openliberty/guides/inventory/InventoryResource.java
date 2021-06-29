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
package io.openliberty.guides.inventory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.openliberty.guides.graphql.models.SystemInfo;
import io.openliberty.guides.graphql.models.SystemLoad;
import io.openliberty.guides.inventory.client.SystemClient;
import io.openliberty.guides.inventory.model.InventoryList;
import io.smallrye.graphql.client.typesafe.api.GraphQlClientBuilder;

@ApplicationScoped
@Path("/systems")
public class InventoryResource {

    @Inject
    InventoryManager manager;

    // tag::clientBuilder[]
    private SystemClient sc = GraphQlClientBuilder.newBuilder()
                              .build(SystemClient.class);
    // end::clientBuilder[]

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public InventoryList listContents() {
        return manager.list();
    }

    @GET
    @Path("/{hostname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSystemInfoForHost(@PathParam("hostname") String hostname) {
        SystemInfo systemInfo = manager.get(hostname);
        if (systemInfo == null) {
            try {
                systemInfo = sc.system(hostname);
                manager.put(hostname, systemInfo);
            } catch (Exception e) {
                return Response.status(Status.NOT_FOUND).entity(
                           "{ \"error\" : \"Unknown hostname or the system service "
                           + "may not be running on " + hostname + "\" }").build();
            }
        }
        return Response.ok(systemInfo).build();
    }

    @POST
    @Path("/{hostname}/note")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editNote(@PathParam("hostname") String hostname, String text) {
        sc.editNote(hostname, text);
        SystemInfo systemInfo = manager.get(hostname);
        if (systemInfo != null) {
            // Refresh the inventory
            systemInfo = sc.system(hostname);
            manager.put(hostname, systemInfo);
        }
        return Response.ok().build();
    }

    @GET
    @Path("/systemLoad")
    @Produces(MediaType.APPLICATION_JSON)
    public SystemLoad[] getSystemLoad() {
        return sc.systemLoad(manager.hostnames());
    }

    @POST
    @Path("/reset")
    public void reset() {
        manager.reset();
    }
}
