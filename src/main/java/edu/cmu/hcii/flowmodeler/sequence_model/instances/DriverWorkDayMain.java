package edu.cmu.hcii.flowmodeler.sequence_model.instances;

import edu.cmu.hcii.flowmodeler.sequence_model.model.SequenceModel;

public class DriverWorkDayMain {
    public static void main(String[] args) {
        SequenceModel model = new SequenceModel("DriverWorkDay")
                .setStepColor("lightyellow")
                .setConditionalColor("lightpink");


        model.setFirstNode(model.getStep("park away from garage"))
                .setNextNode(model.getStep("walk over / get a ride to police station"))
                .setNextNode(model.getStep("check in + paper work"))
                .setNextNode(model.getStep("go to garage"))
                .setNextNode(model.getStep("maintenance check"))
                .setNextNode(model.getConditional("needs work done?"))
                .setTrueBranchNode(model.getStep("give to shift coordinator"))
                .setNextNode(model.getStep("get other bus"))
                .setNextNode(model.getConditional("has gas?"));

        model.findStepByName("park away from garage").setBreakdown(true);
        model.findStepByName("go to garage").setBreakdown(true);

        model.findConditionalByName("needs work done?")
                .setFalseBranchNode(model.getConditional("has gas?"))
                .setTrueBranchNode(model.getStep("go to morewood"))
                .setNextNode(model.getConditional("has passengers?"))
                .setTrueBranchNode(model.getStep("write down passenger count"))
                .setNextNode(model.getStep("restroom / break"))
                .setNextNode(model.getStep("do route A/B"))
                .setTriggerNode(model.getStep("last route / end shift"), "2:45pm")
                .setNextNode(model.getStep("fill more paperwork"))
                .setTriggerNode(model.getStep("hand off bus"), "3:15pm")
                .setNextNode(model.getStep("return paper work"));

        model.getConditional("has passengers?")
                .setFalseBranchNode(model.getStep("restroom / break"));

        model.getStep("do route A/B")
                .setNextNode(model.getStep("go to morewood"));

        model.getStep("go to morewood")
                .setTriggerNode(model.getStep("get gas @ shell"), "run out of gas");


        model.getConditional("has gas?")
                .setFalseBranchNode(model.getStep("get gas @ shell"))
                .setNextNode(model.getStep("go to morewood"));

        //add evidences

        model.getConditional("has gas?")
                .setEvidence("Test evidence 1");

        model.getStep("go to morewood")
                .setEvidence("Test evidence 2");

        model.saveGoJSResultJSON();

    }
}
