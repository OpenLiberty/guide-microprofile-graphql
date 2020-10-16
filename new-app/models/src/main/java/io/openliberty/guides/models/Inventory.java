package io.openliberty.guides.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import javax.validation.constraints.NotNull;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class Inventory {

    private static final Jsonb jsonb = JsonbBuilder.create();
    
    @NotNull
    private List<SystemLoad> systems;
    
    public Inventory() {
        this.systems = new ArrayList<SystemLoad>();
    }
    
    public Inventory(List<SystemLoad> systems) {
        this.systems = systems;
    }
    
    public int getSize() {
        return this.systems.size();
    }
    
    public List<SystemLoad> getSystems() {
        return this.systems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory i = (Inventory) o;
        return Objects.equals(systems, i.systems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systems);
    }
    
    @Override
    public String toString() {
        return "Inventory: " + jsonb.toJson(this);
    }
    
    public static class SystemLoadSerializer implements Serializer<Object> {
        @Override
        public byte[] serialize(String topic, Object data) {
          return jsonb.toJson(data).getBytes();
        }
    }

    public static class SystemLoadDeserializer implements Deserializer<Inventory> {
        @Override
        public Inventory deserialize(String topic, byte[] data) {
            if (data == null)
                return null;
            return jsonb.fromJson(new String(data), Inventory.class);
        }
    }
}
