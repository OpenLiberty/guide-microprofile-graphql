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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.enterprise.context.ApplicationScoped;

import io.openliberty.guides.models.SystemLoad;

@ApplicationScoped
public class InventoryManager {
    
    private Map<String, SystemLoad> systems = Collections.synchronizedMap(new TreeMap<String, SystemLoad>());

    public void upsertSystem(SystemLoad systemLoad) {
        String hostname = systemLoad.getHostname();
        systems.put(hostname, systemLoad);
    }

    public Optional<SystemLoad> getSystem(String hostname) {
        SystemLoad sl = systems.get(hostname);
        return Optional.ofNullable(sl);
    }

    public List<String> getSystems() {
        return new ArrayList<String>(systems.keySet());
    }

    public void resetSystems() {
        systems.clear();
    }
}