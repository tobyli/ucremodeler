package edu.cmu.hcii.flowmodeler.sequence_model.model;

import com.google.gson.Gson;
import edu.cmu.hcii.flowmodeler.gojsadapter.GoJSLink;
import edu.cmu.hcii.flowmodeler.gojsadapter.GoJSNode;
import edu.cmu.hcii.flowmodeler.gojsadapter.GoJSResult;

import java.util.*;

import static edu.cmu.hcii.flowmodeler.flow_model.model.FlowModel.writeStringtoFile;

public class SequenceModel {

    String name;
    transient Map<Integer, Node> nodeIdNodeMap;
    transient Map<String, Node> nodeNameNodeMap;
    transient Set<Node> allNodes;
    Node firstNode;
    String stepColor = "lightblue";
    String conditionalColor = "lightgreen";

    public SequenceModel(String name) {
        this.name = name;
        this.nodeIdNodeMap = new HashMap<>();
        this.nodeNameNodeMap = new HashMap<>();
        this.allNodes = new HashSet<>();
    }

    public Step setFirstNode(Step firstNode) {
        this.firstNode = firstNode;
        processNewNode(firstNode);
        return firstNode;
    }

    public Conditional setFirstNode(Conditional firstNode) {
        this.firstNode = firstNode;
        processNewNode(firstNode);
        return firstNode;
    }

    protected void processNewNode(Node newNode) {
        newNode.setModel(this);
        newNode.setId(getNextNodeId());
        this.nodeIdNodeMap.put(getNextNodeId(), newNode);
        nodeNameNodeMap.put(newNode.name, newNode);
        allNodes.add(newNode);
    }

    protected Integer getNextNodeId() {
        return allNodes.size() + 1;
    }

    public Step findStepByName(String name) {
        if(nodeNameNodeMap.get(name) != null && nodeNameNodeMap.get(name) instanceof Step) {
            return (Step) nodeNameNodeMap.get(name);
        }
        return null;
    }

    public Conditional findConditionalByName(String name) {
        if(nodeNameNodeMap.get(name) != null && nodeNameNodeMap.get(name) instanceof Conditional) {
            return (Conditional) nodeNameNodeMap.get(name);
        }
        return null;
    }

    protected Node findNodeById(Integer id) {
        return nodeIdNodeMap.get(id);
    }

    public List<GoJSNode> getGoJSNodeList() {
        List<GoJSNode> results = new ArrayList<>();
        for (Node node : allNodes) {
            String color = "lightgray";
            if(node instanceof Step){
                color = stepColor;
            } else if (node instanceof Conditional) {
                color = conditionalColor;
            }
            GoJSNode goJSNode = new GoJSNode(node, color);
            results.add(goJSNode);
        }
        return results;
    }

    public List<GoJSLink> getGoJSLinkList() {
        List<GoJSLink> results = new ArrayList<>();
        for (Node node : allNodes) {
            if (node instanceof Step){
                if (((Step) node).nextNode != null) {
                    GoJSLink link = new GoJSLink(node.id, ((Step) node).nextNode.id, "", "black");
                    link.setCategory("nolabel");
                    results.add(link);
                }

                if (node.trigger != null){
                    GoJSLink link = new GoJSLink(node.id, node.trigger.triggeredNode.id,  node.trigger.text, "black");
                    link.setCategory("label");
                    results.add(link);
                }
            } else if (node instanceof Conditional){
                if(((Conditional) node).trueBranch != null) {
                    GoJSLink link = new GoJSLink(node.id, ((Conditional) node).trueBranch.id, "Y", "black");
                    link.setCategory("label");
                    results.add(link);
                }
                if(((Conditional) node).falseBranch != null) {
                    GoJSLink link = new GoJSLink(node.id, ((Conditional) node).falseBranch.id, "N", "black");
                    link.setCategory("label");
                    results.add(link);
                }
            }
        }
        return results;
    }

    public String getName() {
        return name;
    }

    public void saveGoJSResultJSON() {
        writeStringtoFile(new Gson().toJson(new GoJSResult(getGoJSNodeList(), getGoJSLinkList())), this.getName() + "_gojs.json");
    }

    public Step getStep(String name){
        if (nodeNameNodeMap.containsKey(name) && nodeNameNodeMap.get(name) instanceof Step){
            return (Step)nodeNameNodeMap.get(name);
        } else {
            return new Step(name);
        }
    }

    public Conditional getConditional(String name){
        if (nodeNameNodeMap.containsKey(name) && nodeNameNodeMap.get(name) instanceof Conditional){
            return (Conditional)nodeNameNodeMap.get(name);
        } else {
            return new Conditional(name);
        }
    }

    public SequenceModel setStepColor(String stepColor) {
        this.stepColor = stepColor;
        return this;
    }

    public SequenceModel setConditionalColor(String conditionalColor) {
        this.conditionalColor = conditionalColor;
        return this;
    }
}
