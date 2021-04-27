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
// tag::class[]
public class JavaInfo {

    // tag::name[]
    @Name("vendorName")
    // end::name[]
    private String vendor;

    // tag::nonnull[]
    @NonNull
    // end::nonnull[]
    private String version;

    // tag::getVendor[]
    public String getVendor() {
        return this.vendor;
    }
    // end::getVendor[]

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    // tag::getVersion[]
    public String getVersion() {
        return this.version;
    }
    // end::getVersion[]

    public void setVersion(String version) {
        this.version = version;
    }

}
// end::class[]
