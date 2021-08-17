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

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.openliberty.guides.graphql.models.JavaInfo;
import io.openliberty.guides.graphql.models.OperatingSystem;

@ApplicationScoped
@Path("/")
public class SystemPropertiesResource {

    // tag::queryProperty[]
    @GET
    @Path("properties/{property}")
    @Produces(MediaType.TEXT_PLAIN)
    public String queryProperty(@PathParam("property") String property) {
        return System.getProperty(property);
    }
    // end::queryProperty[]

    // tag::java[]
    @GET
    @Path("properties/java")
    @Produces(MediaType.APPLICATION_JSON)
    public JavaInfo java() {
        JavaInfo javaInfo = new JavaInfo();
        javaInfo.setVersion(System.getProperty("java.version"));
        javaInfo.setVendor(System.getProperty("java.vendor"));
        return javaInfo;
    }
    // end::java[]

    // tag::operatingSystem[]
    @GET
    @Path("properties/os")
    @Produces(MediaType.APPLICATION_JSON)
    public OperatingSystem getOperatingSystem() {
        OperatingSystem operatingSystem = new OperatingSystem();
        operatingSystem.setArch(System.getProperty("os.arch"));
        operatingSystem.setName(System.getProperty("os.name"));
        operatingSystem.setVersion(System.getProperty("os.version"));
        return operatingSystem;
    }
    // end::operatingSystem[]

    // tag::note[]
    @POST
    @Path("note")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editNote(String text) {
        System.setProperty("note", text);
        return Response.ok().build();
    }
    // end::note[]

}
