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
package io.openliberty.guides.inventory.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.openliberty.guides.graphql.models.SystemInfo;

public class InventoryList {

    private List<SystemInfo> systems = null;

    public InventoryList(Collection<SystemInfo> systems) {
        this.systems = new ArrayList<SystemInfo>(systems);
    }

    public List<SystemInfo> getSystems() {
        return systems;
    }

    public int getTotal() {
        return systems.size();
    }
}
