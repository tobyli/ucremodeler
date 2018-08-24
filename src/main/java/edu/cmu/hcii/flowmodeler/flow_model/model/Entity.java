package edu.cmu.hcii.flowmodeler.flow_model.model;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    int entityId;
    String entityName;
    String entityText;
    transient FlowModel parentModel;
    List<Entity> subEntities;

    public Entity(int entityId, String entityName, String entityText){
        this.entityId = entityId;
        this.entityName = entityName;
        this.entityText = entityText;
        this.subEntities = null;
    }

    private void addSubEntity(Entity entity){
        if (subEntities == null){
            subEntities = new ArrayList<>();
        }
        subEntities.add(entity);
    }


    public Entity addNewSubArtifact(String entityName, String entityText){
        Artifact newArtifact = new Artifact(parentModel.getNextEntityId(), entityName, entityText);
        addSubEntity(newArtifact);
        if(parentModel != null){
            parentModel.processNewEntity(newArtifact);
        }
        return this;
    }

    public Entity addNewSubArtifact(String entityText){
        return addNewSubArtifact(entityText, entityText);
    }

    public Entity addNewSubRole(String entityName, String entityText){
        Role newRole = new Role(parentModel.getNextEntityId(), entityName, entityText);
        addSubEntity(newRole);
        if(parentModel != null){
            parentModel.processNewEntity(newRole);
        }
        return this;
    }

    public Entity addNewSubRole(String entityText){
        return addNewSubRole(entityText, entityText);
    }

    public Entity addExistingEntityByName(String entityName){
        Entity entity = parentModel.findEntityByName(entityName);
        if(entity != null) {
            addSubEntity(entity);
        } else {
            throw new RuntimeException("Can't find existing entity " + entityName);
        }
        return this;

    }

    public Entity addExistingEntityById(Integer id){
        Entity entity = parentModel.findEntityById(id);
        if(entity != null) {
            addSubEntity(entity);
        } else {
            throw new RuntimeException("Can't find existing entity " + entityName);
        }
        return this;
    }

    protected void setParentModel(FlowModel parentModel) {
        this.parentModel = parentModel;
    }

    public int getEntityId() {
        return entityId;
    }

    public List<Entity> getSubEntities() {
        return subEntities;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityText() {
        return entityText;
    }
}
