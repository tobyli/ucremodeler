package edu.cmu.hcii.flowmodeler.sequence_model.model;

public class Conditional extends Node {
    public Conditional(String name){
        super(name, name);
        this.condition = name;
    }

    public Conditional(String name, String text, String condition){
        super(name, text);
        this.condition = condition;
    }

    public Conditional(String name, String text, String condition, Node trueBranch, Node falseBranch){
        this(name, text, condition);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    String condition;
    Node trueBranch;
    Node falseBranch;

    public Conditional setTrueBranchNode(Conditional nextNode) {
        this.trueBranch = nextNode;
        nextNode.setPreviousNode(this);
        if(! getModel().allNodes.contains(nextNode)) {
            getModel().processNewNode(nextNode);
        }
        return nextNode;
    }

    public Step setTrueBranchNode(Step nextNode) {
        this.trueBranch = nextNode;
        nextNode.setPreviousNode(this);
        if(! getModel().allNodes.contains(nextNode)) {
            getModel().processNewNode(nextNode);
        }
        return nextNode;
    }

    public Conditional setFalseBranchNode(Conditional nextNode) {
        this.falseBranch = nextNode;
        nextNode.setPreviousNode(this);
        if(! getModel().allNodes.contains(nextNode)) {
            getModel().processNewNode(nextNode);
        }
        return nextNode;
    }

    public Step setFalseBranchNode(Step nextNode) {
        this.falseBranch = nextNode;
        nextNode.setPreviousNode(this);
        if(! getModel().allNodes.contains(nextNode)) {
            getModel().processNewNode(nextNode);
        }
        return nextNode;
    }

    public Conditional setEvidence(String evidence) {
        this.evidence = evidence;
        return this;
    }
}
