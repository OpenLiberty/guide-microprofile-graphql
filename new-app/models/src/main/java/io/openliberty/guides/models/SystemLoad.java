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
package io.openliberty.guides.models;

import java.util.Objects;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import javax.validation.constraints.NotNull;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class SystemLoad {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @NotNull
    private String hostname;

    @NotNull
    private Double loadAverage;

    private String note;

    public SystemLoad() {}

    public SystemLoad(String hostname, Double loadAverage) {
        this.hostname = hostname;
        this.loadAverage = loadAverage;
    }

    public SystemLoad(String hostname, Double loadAverage, String note) {
        this(hostname, loadAverage);
        this.note = note;
    }
    
    public String getHostname() {
        return this.hostname;
    }
    
    public Double getLoadAverage() {
        return this.loadAverage;
    }
    
    public void setLoadAverage(Double newLoad) {
        this.loadAverage = newLoad;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemLoad)) return false;
        SystemLoad sl = (SystemLoad) o;
        return Objects.equals(hostname, sl.hostname)
                && Objects.equals(loadAverage, sl.loadAverage)
                && Objects.equals(note, sl.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname, loadAverage, note);
    }
    
    @Override
    public String toString() {
        return "SystemLoad: " + jsonb.toJson(this);
    }
    
    public static class SystemLoadSerializer implements Serializer<Object> {
        @Override
        public byte[] serialize(String topic, Object data) {
          return jsonb.toJson(data).getBytes();
        }
    }

    public static class SystemLoadDeserializer implements Deserializer<SystemLoad> {
        @Override
        public SystemLoad deserialize(String topic, byte[] data) {
            if (data == null)
                return null;
            return jsonb.fromJson(new String(data), SystemLoad.class);
        }
    }
}