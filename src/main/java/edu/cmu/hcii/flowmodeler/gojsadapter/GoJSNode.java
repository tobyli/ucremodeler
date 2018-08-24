package edu.cmu.hcii.flowmodeler.gojsadapter;

import edu.cmu.hcii.flowmodeler.flow_model.model.Entity;
import edu.cmu.hcii.flowmodeler.sequence_model.model.Conditional;
import edu.cmu.hcii.flowmodeler.sequence_model.model.Node;
import edu.cmu.hcii.flowmodeler.sequence_model.model.Step;

public class GoJSNode {
    Integer key;
    String text;
    String breakdownLabel;
    String evidence;
    String color;
    String category;
    boolean isGroup;
    Integer group;

    public GoJSNode(Entity entity, String color, String category){
        this.key = entity.getEntityId();
        this.text = entity.getEntityText();
        if(entity.getEvidence() != null) {
            this.text += (" [" + "E: " + entity.getEvidence() + "]");
        }
        if(entity.isBreakdown()) {
            this.text += " ⚡";
        }
        this.category = category;
        this.isGroup = entity.getSubEntities() != null && entity.getSubEntities().size() > 0;
        this.evidence = entity.getEvidence();
        if(this.isGroup){
            //making all groups black for now
            this.color = "black";
        } else {
            this.color = color;
        }
    }

    public GoJSNode(Node node, String color){
        this.key = node.getId();
        this.text = node.getName();
        if(node.isBreakdown()){
            //this.text = this.text + "*BREAKDOWN*";
            this.setBreakdownLabel("⚡");
        }
        if(node.getEvidence() != null && node.getEvidence().length() > 0){
            this.setEvidence(node.getEvidence());
        }
        if(node instanceof Conditional){
            this.category = "diamond";
        } else if (node instanceof Step) {
            this.category = "step";
        }
        //this.category = category;
        this.isGroup = false;
        this.color = color;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public void setBreakdownLabel(String breakdownLabel) {
        this.breakdownLabel = breakdownLabel;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
}
