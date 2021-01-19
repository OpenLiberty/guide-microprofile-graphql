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
package io.openliberty.guides.graphql.models;

import java.util.Properties;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Type;

// tag::type[]
@Type("operatingSystem")
// end::type[]
// tag::description[]
@Description("Information about an operating system")
// end::description[]
public class OperatingSystem {

    @NonNull
    @Name("arch")
    private String arch;
    
    @NonNull
    @Name("name")
    private String name;
    
    @NonNull
    @Name("version")
    private String version;
    
    public OperatingSystem(Properties systemProperties) {
        this.arch = systemProperties.getProperty("os.arch");
        this.name = systemProperties.getProperty("os.name");
        this.version = systemProperties.getProperty("os.version");
    }
    
    public String getArch() {
        return this.arch;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getVersion() {
        return this.version;
    }

}
