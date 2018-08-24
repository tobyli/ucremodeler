package edu.cmu.hcii.flowmodeler.flow_model.model;

public class Flow {
    transient Entity fromEntity;
    transient Entity toEntity;
    String label;
    Integer fromEntityId;
    Integer toEntityId;
    String fromEntityName;
    String toEntityName;
    String evidence;
    boolean isBreakdown;

    public Flow(Entity fromEntity, Entity toEntity, String label, String evidence, boolean isBreakdown){
        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
        this.label = label;
        this.fromEntityId = fromEntity.entityId;
        this.toEntityId = toEntity.entityId;
        this.fromEntityName = fromEntity.entityName;
        this.toEntityName = toEntity.entityName;
        this.evidence = evidence;
        this.isBreakdown = isBreakdown;
    }

    public Flow setEvidence(String evidence) {
        this.evidence = "E: " + evidence;
        return this;
    }

    public void setBreakdown(boolean breakdown) {
        isBreakdown = breakdown;
    }

    public boolean isBreakdown() {
        return isBreakdown;
    }
}
