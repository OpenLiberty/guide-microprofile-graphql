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
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Type;

@Type("System")
@Description("A single system's information")
public class SystemLoad {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @NonNull
    private String hostname;

    @NonNull
    private Double loadAverage;

    private String note;

    public SystemLoad() {
        super();
    }

    @JsonbCreator
    public SystemLoad(@JsonbProperty("hostname") String hostname, 
            @JsonbProperty("loadAverage") Double loadAverage, 
            @JsonbProperty("note") String note) {
        this.hostname = hostname;
        this.loadAverage = loadAverage;
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
        return Objects.equals(this.hostname, sl.hostname)
                && Objects.equals(this.loadAverage, sl.loadAverage)
                && Objects.equals(this.note, sl.note);
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
            System.out.println(new String(data));
            if (data == null)
                return null;
            return jsonb.fromJson(new String(data), SystemLoad.class);
        }
    }
}