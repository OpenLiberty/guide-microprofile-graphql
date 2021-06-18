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

    private static final OperatingSystemMXBean osMean = 
                             ManagementFactory.getOperatingSystemMXBean();

    private static final MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();

    @GET
    @Path("/operatingSystem")
    @Produces(MediaType.APPLICATION_JSON)
    public OperatingSystem getOperatingSystem() {
        OperatingSystem operatingSystem = new OperatingSystem();
        operatingSystem.setArch(osMean.getArch());
        operatingSystem.setName(osMean.getName());
        operatingSystem.setVersion(osMean.getVersion());
        return operatingSystem;
    }

    @GET
    @Path("/systemLoad")
    @Produces(MediaType.APPLICATION_JSON)
    public SystemLoadData getSystemLoad() {
        SystemLoadData systemLoadData = new SystemLoadData();
        systemLoadData.setProcessors(osMean.getAvailableProcessors());
        systemLoadData.setLoadAverage(osMean.getSystemLoadAverage());
        systemLoadData.setHeapSize(memBean.getHeapMemoryUsage().getMax());
        systemLoadData.setHeapUsed(memBean.getHeapMemoryUsage().getUsed());
        return systemLoadData;
    }
}
