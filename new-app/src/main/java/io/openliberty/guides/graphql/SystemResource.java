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

import java.util.Properties;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import io.openliberty.guides.graphql.models.JavaInfo;
import io.openliberty.guides.graphql.models.OperatingSystem;
import io.openliberty.guides.graphql.models.SystemInfo;

@GraphQLApi
public class SystemResource {

    @Query("system")
    public SystemInfo getSystemInfo() {
        Properties rawProperties = System.getProperties();
        OperatingSystem os = new OperatingSystem(
                rawProperties.getProperty("os.arch"), 
                rawProperties.getProperty("os.name"), 
                rawProperties.getProperty("os.version"));
        JavaInfo java = new JavaInfo(
                rawProperties.getProperty("java.version"), 
                rawProperties.getProperty("java.vendor"));
        SystemInfo output = new SystemInfo(os, java, 
                rawProperties.getProperty("user.timezone"), 
                rawProperties.getProperty("user.name"));
        if (rawProperties.containsKey("note")) {
            output.setNote(rawProperties.getProperty("note"));
        }
        return output;
    }

    @Mutation("editNote")
    public boolean editNote(String note) {
        System.setProperty("note", note);
        return true;
    }
}
