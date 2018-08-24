package edu.cmu.hcii.flowmodeler.flow_model.model;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    int entityId;
    String entityName;
    String entityText;
    transient FlowModel parentModel;
    List<Entity> subEntities;
    String evidence;
    boolean isBreakdown;

    public Entity(int entityId, String entityName, String entityText, String evidence, Boolean isBreakdown){
        this.entityId = entityId;
        this.entityName = entityName;
        this.entityText = entityText;
        this.subEntities = null;
        this.evidence = evidence;
        this.isBreakdown = isBreakdown;
    }

    private void addSubEntity(Entity entity){
        if (subEntities == null){
            subEntities = new ArrayList<>();
        }
        subEntities.add(entity);
    }


    public Entity addNewSubArtifact(String entityName, String entityText, String evidence, boolean isBreakdown){
        Artifact newArtifact = new Artifact(parentModel.getNextEntityId(), entityName, entityText, evidence, isBreakdown);
        addSubEntity(newArtifact);
        if(parentModel != null){
            parentModel.processNewEntity(newArtifact);
        }
        return this;
    }

    public Entity addNewSubArtifact(String entityText){
        return addNewSubArtifact(entityText, false);
    }


    public Entity addNewSubArtifact(String entityText, boolean isBreakdown){
        return addNewSubArtifact(entityText, null, isBreakdown);
    }

    public Entity addNewSubArtifact(String entityText, String evidence, boolean isBreakdown){
        return addNewSubArtifact(entityText, entityText, evidence, isBreakdown);
    }

    public Entity addNewSubArtifact(String entityText, String evidence){
        return addNewSubArtifact(entityText, entityText, evidence, false);
    }

    public Entity addNewSubRole(String entityName, String entityText, String evidence, boolean isBreakdown){
        Role newRole = new Role(parentModel.getNextEntityId(), entityName, entityText, evidence, isBreakdown);
        addSubEntity(newRole);
        if(parentModel != null){
            parentModel.processNewEntity(newRole);
        }
        return this;
    }

    public Entity addNewSubRole(String entityText){
        return addNewSubRole(entityText, null, false);
    }

    public Entity addNewSubRole(String entityText, boolean isBreakdown){
        return addNewSubRole(entityText, null, isBreakdown);
    }

    public Entity addNewSubRole(String entityText, String evidence, boolean isBreakdown){
        return addNewSubRole(entityText, entityText, evidence, isBreakdown);
    }

    public Entity addNewSubRole(String entityText, String evidence){
        return addNewSubRole(entityText, entityText, evidence, false);
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

    public String getEvidence() {
        return evidence;
    }

    public void setBreakdown(boolean breakdown) {
        isBreakdown = breakdown;
    }

    public boolean isBreakdown() {
        return isBreakdown;
    }
}
