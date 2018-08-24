package edu.cmu.hcii.flowmodeler.flow_model.model;

public class Flow {
    public Flow(Entity fromEntity, Entity toEntity, String label){
        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
        this.label = label;
        this.fromEntityId = fromEntity.entityId;
        this.toEntityId = toEntity.entityId;
        this.fromEntityName = fromEntity.entityName;
        this.toEntityName = toEntity.entityName;
    }
    transient Entity fromEntity;
    transient Entity toEntity;
    String label;
    Integer fromEntityId;
    Integer toEntityId;
    String fromEntityName;
    String toEntityName;
}
