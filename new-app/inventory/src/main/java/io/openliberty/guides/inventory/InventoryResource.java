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
    
    // Get multiple systems
    @Query("systems")
    public List<SystemLoad> getSystems(@Name("hostnames") String[] hostnames) throws GraphQLException {
        List<SystemLoad> output = new ArrayList<SystemLoad>();
        List<String> missingHosts = new ArrayList<String>();
        
        for (String hostname : hostnames) {
            Optional<SystemLoad> sl = manager.getSystem(hostname);
            if (sl.isPresent()) {
                output.add(sl.get());
            } else {
                missingHosts.add(hostname);
            }
        }
        
        if (missingHosts.size() > 0) {
            String errorDetails = String.join(", ", missingHosts);
            logger.severe("Cannot find following hosts: " + errorDetails);
            throw new GraphQLException(
                    "Cannot find following hosts: " + errorDetails,
                    output
            );
        }
        
        return output;
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
        String hostname = sl.getHostname();
        if (manager.getSystem(hostname).isPresent()) {
            logger.info("Host " + hostname + " was updated: " + sl);
        } else {
            logger.info("Host " + hostname + " was added: " + sl);
        }
        manager.upsertSystem(sl);
    }
}
