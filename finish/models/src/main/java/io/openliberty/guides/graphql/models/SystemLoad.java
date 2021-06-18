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
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Type;

// tag::type[]
@Type("systemLoad")
// end::type[]
// tag::description[]
@Description("Information of system usage")
// end::description[]
// tag::class[]
public class SystemLoad {

    @NonNull
    private String hostname;
    
    @NonNull
    private SystemLoadData data;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public SystemLoadData getData() {
        return data;
    }

    public void setData(SystemLoadData data) {
        this.data = data;
    }

}
// end::class[]
