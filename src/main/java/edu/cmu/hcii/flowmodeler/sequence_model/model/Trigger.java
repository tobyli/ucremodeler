package edu.cmu.hcii.flowmodeler.sequence_model.model;

public class Trigger {
    Node triggeredNode;
    String text;
    public Trigger(String text, Node triggeredNode){
        this.text = text;
        this.triggeredNode = triggeredNode;
    }

    public String getText() {
        return text;
    }
}
