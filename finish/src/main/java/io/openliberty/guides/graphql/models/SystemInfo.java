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

@Type("System")
@Description("Information about a single system")
public class SystemInfo {

    @Name("timezone")
    private String timezone;

    @NonNull
    @Name("username")
    private String username;

    @Name("note")
    private String note;

    public SystemInfo() {
        super();
    }
    
    public SystemInfo(
            String timezone, 
            String username) {
        this.timezone = timezone;
        this.username = username;
    }

    @Name("note")
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Name("timezone")
    public String getTimezone() {
        return this.timezone;
    }

    @Name("username")
    public String getUsername() {
        return this.username;
    }
}
