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
package io.openliberty.guides.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.GraphQLException;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import io.openliberty.guides.models.SystemLoad;

@GraphQLApi
@ApplicationScoped
public class InventoryResource {

    private static Logger logger = Logger.getLogger(InventoryResource.class.getName());

    @Inject
    private InventoryManager manager;
    
    // Get a single system
    @Query("system")
    public SystemLoad getSystem(@Name("hostname") String hostname) throws GraphQLException {
        if (manager.getSystem(hostname).isPresent()) {
            return manager.getSystem(hostname).get();
        } else {
            throw new GraphQLException("");
        }
    }
    
    // Get a list of all the hostnames available
    @Query("hostnames")
    public List<String> getHostnames() {
        return manager.getSystems();
    }
    
    // Add note to system
    @Mutation
    public boolean addNote(String hostname, String note) throws GraphQLException {
        if (manager.updateNote(hostname, note)) {
            return true;
        } else {
            throw new GraphQLException(hostname + " not found in inventory");
        }
    }

    @Incoming("systemLoad")
    public void updateStatus(SystemLoad sl)  {
        System.out.println("Inventory received " + sl);
        String hostname = sl.getHostname();
        Double loadAverage = sl.getLoadAverage();
        if (manager.getSystem(hostname).isPresent()) {
            logger.info("Host " + hostname + " was updated: " + sl);
            manager.updateLoadAverage(hostname, loadAverage);
        } else {
            logger.info("Host " + hostname + " was added: " + sl);
            manager.upsertSystem(sl);
        }
    }
}
