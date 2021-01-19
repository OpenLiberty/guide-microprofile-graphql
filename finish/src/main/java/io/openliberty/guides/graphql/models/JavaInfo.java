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
@Type("java")
// end::type[]
// tag::description[]
@Description("Information about a Java installation")
// end::description[]
public class JavaInfo {

    // tag::name1[]
    @Name("vendor")
    // end::name1[]
    private String vendor;

    // tag::nonnull[]
    @NonNull
    // end::nonnull[]
    // tag::name2[]
    @Name("version")
    // end::name2[]
    private String version;

    public JavaInfo(Properties systemProperties) {
        this.version = systemProperties.getProperty("java.version");
        this.vendor = systemProperties.getProperty("java.vendor");
    }

    // tag::getVendor[]
    public String getVendor() {
        return this.vendor;
    }
    // end::getVendor[]

    // tag::getVersion[]
    public String getVersion() {
        return this.version;
    }
    // end::getVersion[]

}
