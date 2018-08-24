package edu.cmu.hcii.flowmodeler.sequence_model.instances;

import edu.cmu.hcii.flowmodeler.sequence_model.model.SequenceModel;

public class PickingUpDroppingOffMain {
    public static void main(String[] args) {
        SequenceModel model = new SequenceModel("PickingUpDroppingOff");

        model.setFirstNode(model.getStep("drive"))
                .setTriggerNode(model.getConditional("does the bus need to stop?"), "approaching stop", "D0-00")
                .setTrueBranchNode(model.getStep("stop"))
                .setNextNode(model.getConditional("do people need to get off?"))
                .setTrueBranchNode(model.getStep("people get off"))
                .setNextNode(model.getConditional("is there any passenger waiting?"))
                .setTrueBranchNode(model.getConditional("is the bus full?"))
                .setTrueBranchNode(model.getStep("drive"));

        model.getConditional("do people need to get off?")
                .setFalseBranchNode(model.getConditional("is there any passenger waiting?"));

        model.getConditional("does the bus need to stop?")
                .setFalseBranchNode(model.getStep("drive"));

        model.getConditional("is there any passenger waiting?")
                .setFalseBranchNode(model.getStep("drive"));

        model.getConditional("is the bus full?")
                .setFalseBranchNode(model.getStep("people get in"))
                .setNextNode(model.getConditional("does rider have id?"))
                .setTrueBranchNode(model.getStep("permit to enter"))
                .setNextNode(model.getConditional("is there any passenger waiting?"));

        model.getConditional("does rider have id?")
                .setFalseBranchNode(model.getConditional("are you a friend?"))
                .setTrueBranchNode(model.getStep("permit to enter"));

        model.getConditional("are you a friend?")
                .setFalseBranchNode(model.getConditional("do i want to let them in?"))
                .setTrueBranchNode(model.getStep("permit to enter"));

        model.getConditional("do i want to let them in?")
                .setFalseBranchNode(model.getStep("kick off"))
                .setNextNode(model.getConditional("is there any passenger waiting?"));

        model.saveGoJSResultJSON();

    }


}
