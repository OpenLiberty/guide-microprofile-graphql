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
package io.openliberty.guides.graphql.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

import io.openliberty.guides.graphql.models.JavaInfo;
import io.openliberty.guides.graphql.models.OperatingSystem;
import io.openliberty.guides.graphql.models.SystemLoadData;

@RegisterProvider(UnknownUriExceptionMapper.class)
public interface SystemClient extends AutoCloseable {

    @GET
    @Path("/properties/{property}")
    @Produces(MediaType.TEXT_PLAIN)
    String queryProperty(@PathParam("property") String property)
        throws UnknownUriException, ProcessingException;

    @GET
    @Path("/properties/java")
    @Produces(MediaType.APPLICATION_JSON)
    JavaInfo java()
        throws UnknownUriException, ProcessingException;

    @POST
    @Path("/properties/note")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    Response editNote(String text)
        throws UnknownUriException, ProcessingException;

    @GET
    @Path("/management/operatingSystem")
    @Produces(MediaType.APPLICATION_JSON)
    OperatingSystem getOperatingSystem()
        throws UnknownUriException, ProcessingException;

    @GET
    @Path("/management/systemLoad")
    @Produces(MediaType.APPLICATION_JSON)
    SystemLoadData getSystemLoad()
        throws UnknownUriException, ProcessingException;

}
