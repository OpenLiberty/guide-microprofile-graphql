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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.openliberty.guides.graphql.models.OperatingSystem;
import io.openliberty.guides.graphql.models.SystemLoadData;

@ApplicationScoped
@Path("management")
public class SystemManagementResource {

    private static final OperatingSystemMXBean OS_MEAN =
                             ManagementFactory.getOperatingSystemMXBean();

    private static final MemoryMXBean MEM_BEAN = ManagementFactory.getMemoryMXBean();

    // tag::operatingSystem[]
    @GET
    @Path("/operatingSystem")
    @Produces(MediaType.APPLICATION_JSON)
    public OperatingSystem getOperatingSystem() {
        OperatingSystem operatingSystem = new OperatingSystem();
        operatingSystem.setArch(OS_MEAN.getArch());
        operatingSystem.setName(OS_MEAN.getName());
        operatingSystem.setVersion(OS_MEAN.getVersion());
        return operatingSystem;
    }
    // end::operatingSystem[]

    // tag::systemLoad[]
    @GET
    @Path("/systemLoad")
    @Produces(MediaType.APPLICATION_JSON)
    public SystemLoadData getSystemLoad() {
        SystemLoadData systemLoadData = new SystemLoadData();
        systemLoadData.setProcessors(OS_MEAN.getAvailableProcessors());
        systemLoadData.setLoadAverage(OS_MEAN.getSystemLoadAverage());
        systemLoadData.setHeapSize(MEM_BEAN.getHeapMemoryUsage().getMax());
        systemLoadData.setHeapUsed(MEM_BEAN.getHeapMemoryUsage().getUsed());
        return systemLoadData;
    }
    // end::systemLoad[]
}
