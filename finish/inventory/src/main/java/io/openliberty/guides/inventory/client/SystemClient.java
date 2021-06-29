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
package io.openliberty.guides.inventory.client;

import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;

import io.openliberty.guides.graphql.models.SystemInfo;
import io.openliberty.guides.graphql.models.SystemLoad;
import io.smallrye.graphql.client.typesafe.api.GraphQlClientApi;

// tag::clientApi[]
@GraphQlClientApi
// end::clientApi[]
public interface SystemClient {

    // tag::systemInfo[]
    SystemInfo system(@Name("hostname") String hostname);
    // end::systemInfo[]

    // tag::systemLoad[]
    SystemLoad[] systemLoad(@Name("hostnames") String[] hostnames);
    // end::systemLoad[]

    // tag::mutationTag[]
    @Mutation
    // end::mutationTag[]
    // tag::editNote[]
    boolean editNote(@Name("hostname") String hostname, @Name("note") String note);
    // end::editNote[]

}
