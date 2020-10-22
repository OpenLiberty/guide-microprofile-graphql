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

@Type("Java")
@Description("Information about a Java installation")
public class JavaInfo {

    private String vendor = null;

    @NonNull
    private String version;

    public JavaInfo(String version) {
        this.version = version;
    }

    public JavaInfo(String version, String vendor) {
        this(version);
        this.vendor = vendor;
    }

    public String getVendor() {
        return this.vendor;
    }

    public String getVersion() {
        return this.version;
    }
}
