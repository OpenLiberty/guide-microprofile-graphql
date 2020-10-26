package io.openliberty.guides.graphql.client;

import io.openliberty.guides.graphql.models.SystemInfo;

import io.smallrye.graphql.client.typesafe.api.GraphQlClientApi;

import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQlClientApi
public interface SystemServiceAPI {
    
    @Query
    SystemInfo getSystemInfo();
    
    @Mutation
    boolean editNote(@Name("note") String note);
}
