package edu.cmu.hcii.flowmodeler.flow_model.model;


public class Artifact extends Entity {
    public Artifact(int entityId, String entityName, String entityText, String evidence, boolean isBreakdown){
        super(entityId, entityName, entityText, evidence, isBreakdown);
    }
    Artifact setEvidence (String evidence){
        this.evidence = "E: " + evidence;
        return this;
    }

}
