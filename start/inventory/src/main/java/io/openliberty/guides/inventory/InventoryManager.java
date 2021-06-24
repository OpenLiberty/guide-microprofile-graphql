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
package io.openliberty.guides.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import io.openliberty.guides.graphql.models.SystemInfo;
import io.openliberty.guides.inventory.model.InventoryList;

// tag::ApplicationScoped[]
@ApplicationScoped
// end::ApplicationScoped[]
public class InventoryManager {

    private Map<String, SystemInfo> systems = 
                Collections.synchronizedMap(new HashMap<String, SystemInfo>());

    public void put(String hostname, SystemInfo systemInfo) {
        systems.put(hostname, systemInfo);
    }

    public SystemInfo get(String hostname) {
        return systems.get(hostname);
    }

    public void reset() {
        systems.clear();
    }

    public InventoryList list() {
        return new InventoryList(systems.values());
    }

    public String[] hostnames() {
        return systems.keySet().toArray(new String[systems.size()]);
    }
}