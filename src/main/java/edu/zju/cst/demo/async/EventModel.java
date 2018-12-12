package edu.zju.cst.demo.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
    private EventType type;
    private int actorID;
    private int entityType;
    private int entityID;
    private int entityOwnerID;

    private Map<String, String> exts = new HashMap<>();

    public EventModel() {
    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public String getExt(String key) {
        return exts.get(key);
    }

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorID() {
        return actorID;
    }

    public EventModel setActorID(int actorID) {
        this.actorID = actorID;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityID() {
        return entityID;
    }

    public EventModel setEntityID(int entityID) {
        this.entityID = entityID;
        return this;
    }

    public int getEntityOwnerID() {
        return entityOwnerID;
    }

    public EventModel setEntityOwnerID(int entityOwnerID) {
        this.entityOwnerID = entityOwnerID;
        return this;
    }
}
