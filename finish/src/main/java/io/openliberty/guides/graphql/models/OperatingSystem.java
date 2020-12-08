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
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Type;

// tag::type[]
@Type("OperatingSystem")
// end::type[]
// tag::description[]
@Description("Information about an operating system")
// end::description[]
public class OperatingSystem {

    // tag::nonnull1[]
    @NonNull
    // end::nonnull1[]
    // tag::name1[]
    @Name("arch")
    // end::name1[]
    private String arch;
    
    // tag::nonnull2[]
    @NonNull
    // end::nonnull2[]
    // tag::name2[]
    @Name("name")
    // end::name2[]
    private String name;
    
    // tag::nonnull3[]
    @NonNull
    // end::nonnull3[]
    // tag::name3[]
    @Name("version")
    // end::name3[]
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
