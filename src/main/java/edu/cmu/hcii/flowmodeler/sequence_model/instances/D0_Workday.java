package edu.cmu.hcii.flowmodeler.sequence_model.instances;

import edu.cmu.hcii.flowmodeler.sequence_model.model.SequenceModel;

public class D0_Workday {
    public static void main(String[] args) {
        SequenceModel model = new SequenceModel("D0_Workday")
                .setStepColor("lightyellow")
                .setConditionalColor("lightpink");


        model.setFirstNode(model.getStep("park away from garage").setEvidence("D0-33"))
                .setNextNode(model.getStep("walk over / get a ride to police station").setEvidence("D0-34"))
                .setNextNode(model.getStep("check in + paper work").setEvidence("D0-35"))
                .setNextNode(model.getStep("go to garage").setEvidence("D0-36"))
                .setNextNode(model.getStep("maintenance check").setEvidence("D0-39"))
                .setNextNode(model.getConditional("needs work done?").setEvidence("D0-40"))
                .setTrueBranchNode(model.getStep("give to shift coordinator").setEvidence("D0-41"))
                .setNextNode(model.getConditional("has gas?").setEvidence("D0-37"));

        model.findStepByName("park away from garage").setBreakdown(true);
        model.findStepByName("go to garage").setBreakdown(true);

        model.findConditionalByName("needs work done?")
                .setFalseBranchNode(model.getConditional("has gas?"))
                .setTrueBranchNode(model.getStep("go to morewood").setEvidence("D0-42"))
                .setNextNode(model.getStep("do route A/B").setEvidence("D0-56"))
                .setTriggerNode(model.getStep("last route / end shift").setEvidence("D0-43"), "2:45pm");


        model.findStepByName("do route A/B")
                .setNextNode(model.getStep("write down passenger count").setEvidence("D0-55"))
                .setNextNode(model.getStep("restroom / break").setEvidence("D0-47"))
                .setTriggerNode(model.getStep("get gas @ shell"), "no gas", "D0-37")
                .setNextNode(model.getStep("go to morewood"));

        model.getStep("last route / end shift")
                .setNextNode(model.getStep("fill more paperwork").setEvidence("D0-44"))
                .setNextNode(model.getStep("hand off bus").setEvidence("D0-45"))
                .setNextNode(model.getStep("return paper work").setEvidence("D0-46"));

        model.getConditional("has gas?")
                .setFalseBranchNode(model.getStep("get gas @ shell").setEvidence("D0-38"))
                .setNextNode(model.getStep("go to morewood"));


        model.saveGoJSResultJSON();

    }
}

