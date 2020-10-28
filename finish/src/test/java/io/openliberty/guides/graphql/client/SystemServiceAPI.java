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
package io.openliberty.guides.graphql.client;

import io.openliberty.guides.graphql.models.SystemInfo;

import io.smallrye.graphql.client.typesafe.api.GraphQlClientApi;

import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

// tag::graphQLClientApi[]
@GraphQlClientApi
// end::graphQLClientApi[]
public interface SystemServiceAPI {
    
    // tag::query[]
    @Query("system")
    // end::query[]
    SystemInfo getSystemInfo();
    
    // tag::mutation[]
    @Mutation("editNote")
    // end::mutation[]
    // tag::editNote[]
    boolean editNote(@Name("note") String note);
    // end::editNote[]
}
