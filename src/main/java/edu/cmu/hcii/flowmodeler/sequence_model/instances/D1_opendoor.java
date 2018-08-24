package edu.cmu.hcii.flowmodeler.sequence_model.instances;

import edu.cmu.hcii.flowmodeler.sequence_model.model.SequenceModel;

public class D1_opendoor {
    public static void main(String[] args) {
        SequenceModel model = new SequenceModel("D1_opendoor")
                .setStepColor("lightyellow")
                .setConditionalColor("lightpink");

        model.setFirstNode(model.getStep("Open door"))
                .setNextNode(model.getConditional("Show ID?").setEvidence("D1-40"))
                .setTrueBranchNode(model.getConditional("Does face match picture?").setEvidence("D1-41"))
                .setTrueBranchNode(model.getStep("Permit to enter").setEvidence("D1-41/42"))
                .setNextNode(model.getStep("Get destination").setEvidence("D1-22"))
                .setNextNode(model.getStep("Mark on sheet").setEvidence("D1-22"));

        model.findConditionalByName("Show ID?")
                .setFalseBranchNode(model.getConditional("First time or not?").setEvidence("D1-44"))
                .setFalseBranchNode(model.getConditional("Is guest?").setEvidence("D1-42"))
                .setTrueBranchNode(model.getStep("Permit to enter"));

        model.findConditionalByName("First time or not?")
                .setTrueBranchNode(model.getStep("Give warning").setEvidence("D1-44"))
                .setNextNode(model.getStep("Permit to enter"));

        model.findConditionalByName("Is guest?")
                .setFalseBranchNode(model.getStep("Not permitted to enter").setEvidence("D1-41"));

        model.findConditionalByName("Does face match picture?")
                .setFalseBranchNode(model.getStep("Not permitted to enter").setEvidence("D1-41"));

        model.saveGoJSResultJSON();

    }
}

