package edu.cmu.hcii.flowmodeler.sequence_model.instances;

import edu.cmu.hcii.flowmodeler.sequence_model.model.SequenceModel;

public class PickingUpDroppingOffMain {
    public static void main(String[] args) {
        SequenceModel model = new SequenceModel("PickingUpDroppingOff");

        model.setFirstNode(model.getStep("drive"))
                .setTriggerNode(model.getConditional("does the bus need to stop?").setEvidence("D0-53"), "approaching stop", "D0-53")
                .setTrueBranchNode(model.getStep("stop"))
                .setNextNode(model.getConditional("do people need to get off?").setEvidence("D0-54"))
                .setTrueBranchNode(model.getStep("people get off").setEvidence("D0-54"))
                .setNextNode(model.getConditional("is there any passenger waiting?").setEvidence("D0-57"))
                .setTrueBranchNode(model.getConditional("is the bus full?").setEvidence("D0-57"))
                .setTrueBranchNode(model.getStep("drive"));

        model.getConditional("do people need to get off?")
                .setFalseBranchNode(model.getConditional("is there any passenger waiting?"));

        model.getConditional("does the bus need to stop?")
                .setFalseBranchNode(model.getStep("drive"));

        model.getConditional("is there any passenger waiting?")
                .setFalseBranchNode(model.getStep("drive"));

        model.getConditional("is the bus full?")
                .setFalseBranchNode(model.getStep("people get in"))
                .setNextNode(model.getConditional("does rider have id?").setEvidence("D0-50"))
                .setTrueBranchNode(model.getStep("permit to enter").setEvidence("D0-51"))
                .setNextNode(model.getConditional("is there any passenger waiting?"));

        model.getConditional("does rider have id?")
                .setFalseBranchNode(model.getConditional("are you a friend?").setEvidence("D0-51"))
                .setTrueBranchNode(model.getStep("permit to enter"));

        model.getConditional("are you a friend?")
                .setFalseBranchNode(model.getConditional("do i want to let them in?").setEvidence("D0-52"))
                .setTrueBranchNode(model.getStep("permit to enter"));

        model.getConditional("do i want to let them in?")
                .setFalseBranchNode(model.getStep("not permit to enter"))
                .setNextNode(model.getConditional("is there any passenger waiting?"));

        model.saveGoJSResultJSON();

    }


}
