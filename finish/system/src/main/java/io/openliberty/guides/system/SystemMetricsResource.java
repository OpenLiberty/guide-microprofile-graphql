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

import io.openliberty.guides.graphql.models.SystemLoadData;
import io.openliberty.guides.graphql.models.SystemMetrics;

@ApplicationScoped
@Path("metrics")
public class SystemMetricsResource {

    private static final OperatingSystemMXBean OS_MEAN =
                             ManagementFactory.getOperatingSystemMXBean();

    private static final MemoryMXBean MEM_BEAN = ManagementFactory.getMemoryMXBean();

    // tag::systemMetrics[]
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SystemMetrics getSystemMetrics() {
        SystemMetrics metrics = new SystemMetrics();
        metrics.setProcessors(OS_MEAN.getAvailableProcessors());
        metrics.setHeapSize(MEM_BEAN.getHeapMemoryUsage().getMax());
        metrics.setNonHeapSize(MEM_BEAN.getNonHeapMemoryUsage().getMax());
        return metrics;
    }
    // end::systemMetrics[]

    // tag::systemLoad[]
    @GET
    @Path("/systemLoad")
    @Produces(MediaType.APPLICATION_JSON)
    public SystemLoadData getSystemLoad() {
        SystemLoadData systemLoadData = new SystemLoadData();
        systemLoadData.setLoadAverage(OS_MEAN.getSystemLoadAverage());
        systemLoadData.setHeapUsed(MEM_BEAN.getHeapMemoryUsage().getUsed());
        systemLoadData.setNonHeapUsed(MEM_BEAN.getNonHeapMemoryUsage().getUsed());
        return systemLoadData;
    }
    // end::systemLoad[]
}
