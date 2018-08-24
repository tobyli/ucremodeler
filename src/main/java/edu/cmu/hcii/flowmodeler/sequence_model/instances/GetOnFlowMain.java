package edu.cmu.hcii.flowmodeler.sequence_model.instances;

import edu.cmu.hcii.flowmodeler.sequence_model.model.SequenceModel;

public class GetOnFlowMain {
    public static void main(String[] args) {
        SequenceModel model = new SequenceModel("GetOnBus");
        model.setFirstNode(model.getStep("open doors"))
                .setNextNode(model.getStep("check ids"))
                .setNextNode(model.getConditional("show id?"))
                .setTrueBranchNode(model.getStep("permit to enter"))
                .setNextNode(model.getStep("drive"));

        model.findConditionalByName("show id?")
                .setFalseBranchNode(model.getConditional("id issued?"))
                .setTrueBranchNode(model.getConditional("do I want to let them in?"))
                .setTrueBranchNode(model.getStep("permit to enter"));

        model.findConditionalByName("do I want to let them in?")
                .setFalseBranchNode(model.getStep("no bus"));

        model.findConditionalByName("id issued?")
                .setFalseBranchNode(model.getConditional("will it be issued?"))
                .setTrueBranchNode(model.getConditional("do I want to let them in?"));

        model.findConditionalByName("will it be issued?")
                .setFalseBranchNode(model.getConditional("friend"))
                .setTrueBranchNode(model.getStep("permit to enter"));

        model.findConditionalByName("friend")
                .setFalseBranchNode(model.getStep("no bus"));

        model.saveGoJSResultJSON();


    }
}