package edu.cmu.hcii.flowmodeler.gojsadapter;
import java.util.*;
public class GoJSResult {
    List<GoJSNode> nodes;
    List<GoJSLink> links;

    public GoJSResult(List<GoJSNode> nodes, List<GoJSLink> links){
        this.nodes = nodes;
        this.links = links;
    }
}
