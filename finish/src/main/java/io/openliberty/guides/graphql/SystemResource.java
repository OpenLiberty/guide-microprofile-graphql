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

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

import io.openliberty.guides.graphql.models.JavaInfo;
import io.openliberty.guides.graphql.models.OperatingSystem;
import io.openliberty.guides.graphql.models.SystemInfo;

@GraphQLApi
public class SystemResource {

    @Query("system")
    @NonNull
    @Description("Gets information about the system")
    public SystemInfo getSystemInfo() {
        Properties rawProperties = System.getProperties();
        SystemInfo output = new SystemInfo(
                rawProperties.getProperty("user.timezone"), 
                rawProperties.getProperty("user.name"));
        if (rawProperties.containsKey("note")) {
            output.setNote(rawProperties.getProperty("note"));
        }
        return output;
    }

    @Mutation("editNote")
    @Description("Changes the note set for the system")
    public boolean editNote(@Name("note") String note) {
        System.setProperty("note", note);
        return true;
    }
    
    // Nested objects, these are more expensive to obtain
    
    @NonNull
    public OperatingSystem operatingSystem(@Source @Name("system") SystemInfo systemInfo) {
        Properties rawProperties = System.getProperties();
        return new OperatingSystem(
                rawProperties.getProperty("os.arch"), 
                rawProperties.getProperty("os.name"), 
                rawProperties.getProperty("os.version")
        );
    }
    
    @NonNull
    public JavaInfo java (@Source @Name("system") SystemInfo systemInfo) {
        Properties rawProperties = System.getProperties();
        return new JavaInfo(
                rawProperties.getProperty("java.version"), 
                rawProperties.getProperty("java.vendor")
        );
    }
}
