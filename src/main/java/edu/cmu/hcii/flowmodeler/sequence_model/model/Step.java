package edu.cmu.hcii.flowmodeler.sequence_model.model;

public class Step extends Node {
    Node nextNode;


    public Step(String name){
        super(name, name);
    }

    public Step(String name, String text){
        super(name, text);
    }

    public Step(String name, String text, Node nextNode){
        this(name, text);
        this.nextNode = nextNode;
    }

    public Conditional setNextNode(Conditional nextNode, String evidence) {
        return setTriggerNode(nextNode, null, evidence);
    }

    public Step setNextNode(Step nextNode, String evidence) {
        return setTriggerNode(nextNode, null, evidence);
    }

    public Conditional setNextNode(Conditional nextNode) {
        this.nextNode = nextNode;
        nextNode.setPreviousNode(this);
        if(! getModel().allNodes.contains(nextNode)) {
            getModel().processNewNode(nextNode);
        }
        return nextNode;
    }

    public Step setNextNode(Step nextNode) {
        this.nextNode = nextNode;
        nextNode.setPreviousNode(this);
        if(! getModel().allNodes.contains(nextNode)) {
            getModel().processNewNode(nextNode);
        }
        return nextNode;
    }

    public Conditional setTriggerNode(Conditional triggerNode, String text){
        return setTriggerNode(triggerNode, text, null);
    }

    public Conditional setTriggerNode(Conditional triggerNode, String text, String evidence) {
        if(this.trigger != null){
            if(this.trigger.getText().startsWith("E:")){
                String newText = "";
                if(text != null){
                    newText += ("T: " + text + " ");
                }
                if(evidence != null){
                    newText += ("E: " + evidence);
                }
                else {
                    newText += this.trigger.getText();
                }
                this.trigger = new Trigger(newText, triggerNode);
            }
        } else {
            String newText = "";
            if(text != null){
                newText += ("T: " + text + " ");
            }
            if(evidence != null){
                newText += ("E: " + evidence);
            }
            this.trigger = new Trigger(newText, triggerNode);
        }
        triggerNode.setPreviousNode(this);
        if(! getModel().allNodes.contains(triggerNode)) {
            getModel().processNewNode(triggerNode);
        }
        return triggerNode;
    }


    public Step setTriggerNode(Step triggerNode, String text){
        return setTriggerNode(triggerNode, text, null);
    }

    public Step setTriggerNode(Step triggerNode, String text, String evidence) {
        if(this.trigger != null){
            if(this.trigger.getText().startsWith("E:")){
                String newText = "";
                if(text != null){
                    newText += ("T: " + text + " ");
                }
                if(evidence != null){
                    newText += ("E: " + evidence);
                }
                else {
                    newText += this.trigger.getText();
                }
                this.trigger = new Trigger(newText, triggerNode);
            }
        } else {
                String newText = "";
                if(text != null){
                    newText += ("T: " + text + " ");
                }
                if(evidence != null){
                    newText += ("E: " + evidence);
                }
                this.trigger = new Trigger(newText, triggerNode);
        }
        triggerNode.setPreviousNode(this);
        if(! getModel().allNodes.contains(triggerNode)) {
            getModel().processNewNode(triggerNode);
        }
        return triggerNode;
    }

    public Step setEvidence(String evidence) {
        this.evidence = evidence;
        return this;
    }
}
