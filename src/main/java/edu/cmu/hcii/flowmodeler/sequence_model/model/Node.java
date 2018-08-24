package edu.cmu.hcii.flowmodeler.sequence_model.model;

public class Node {
    Integer id;
    String name;
    String text;
    Node previousNode;
    Trigger trigger;
    Intent intent;
    SequenceModel model;
    String evidence;

    Boolean isBreakdown = false;

    public Node (String name, String text) {
        this.name = name;
        this.text = text;
    }

    public Node (String name, String text, Node previousNode) {
        this(name, text);
        this.previousNode = previousNode;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public boolean isBreakdown(){
        return isBreakdown;
    }
    public void setBreakdown(Boolean breakdown) {
        isBreakdown = breakdown;
    }



    public String getEvidence() {
        return evidence;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public SequenceModel getModel() {
        return model;
    }

    public void setModel(SequenceModel model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
