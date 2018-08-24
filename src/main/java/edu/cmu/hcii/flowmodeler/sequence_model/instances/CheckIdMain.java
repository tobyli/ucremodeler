package edu.cmu.hcii.flowmodeler.sequence_model.instances;

import edu.cmu.hcii.flowmodeler.sequence_model.model.SequenceModel;

public class CheckIdMain {
    public static void main(String[] args) {
        SequenceModel model = new SequenceModel("CheckId");
        model.setFirstNode(model.getStep("open door"))
                .setTriggerNode(model.getConditional("show id?"), "T: check id")
                .setTrueBranchNode(model.getConditional("does face match the id?"))
                .setTrueBranchNode(model.getStep("permit to enter"))
                .setNextNode(model.getStep("get destination"))
                .setNextNode(model.getStep("mark on sheet"));

        model.getConditional("show id?")
                .setFalseBranchNode(model.getConditional("first time?"))
                .setTrueBranchNode(model.getStep("permit to enter"));

        model.getConditional("does face match the id?")
                .setFalseBranchNode(model.getStep("deny"));

        model.getConditional("first time?")
                .setFalseBranchNode(model.getConditional("guest?"))
                .setFalseBranchNode(model.getStep("deny"));

        model.getConditional("guest?")
                .setTrueBranchNode(model.getStep("permit to enter"));

        model.saveGoJSResultJSON();

    }
}