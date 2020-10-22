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
package io.openliberty.guides.graphql.models;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Type;

@Type("OperatingSystem")
@Description("Information about an operating system")
public class OperatingSystem {

    @NonNull
    private String arch;
    
    @NonNull
    private String name;
    
    @NonNull
    private String version;
    
    public OperatingSystem(String arch, String name, String version) {
        this.arch = arch;
        this.name = name;
        this.version = version;
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
