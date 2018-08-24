package edu.cmu.hcii.flowmodeler.sequence_model.instances;

import edu.cmu.hcii.flowmodeler.sequence_model.model.SequenceModel;

public class D1_Workday {
    public static void main(String[] args) {
        SequenceModel model = new SequenceModel("D1_Workday")
                .setStepColor("lightyellow")
                .setConditionalColor("lightpink");

        model.setFirstNode(model.getStep("Arrive at Police Station to check in").setEvidence("D1-1"))
                .setNextNode(model.getStep("Get clipboard, keys, etc.").setEvidence("D1-8/9"))
                .setNextNode(model.getStep("Drive to garage").setEvidence("D1-2"))
                .setNextNode(model.getStep("Park own car").setEvidence("D1-3"))
                .setNextNode(model.getStep("Pick up the bus at Clyde street").setEvidence("D1-10"))
                .setNextNode(model.getStep("Start driving shift").setEvidence("D1-65"))
                .setNextNode(model.getStep("Pick up students at Escort stop").setEvidence("D1-14"))
                .setNextNode(model.getConditional("Is it full?").setEvidence("D1-15"))
                .setTrueBranchNode(model.getStep("Start dropping off based on custom route").setEvidence("D1-4/39"))
                .setNextNode(model.getConditional("Is it time for next run?").setEvidence("D1-33"))
                .setTrueBranchNode(model.getConditional("Skipped people in previous run?").setEvidence("D1-16"))
                .setTrueBranchNode(model.getStep("Pick up on last skipped pickup point").setEvidence("D1-16/17"))
                .setNextNode(model.getStep("Pick up students at Escort stop"));

        model.findConditionalByName("Is it full?")
                .setFalseBranchNode(model.getStep("Load people").setEvidence("D1-15"))
                .setNextNode(model.getStep("Pick up students at Escort stop"));

        model.findConditionalByName("Is it time for next run?")
                .setFalseBranchNode(model.getConditional("Shift over?").setEvidence("D1-63"))
                .setFalseBranchNode(model.getConditional("Is it lunch time?").setEvidence("D1-64"))
                .setFalseBranchNode(model.getStep("Break").setEvidence("D1-33"))
                .setNextNode(model.getConditional("Skipped people in previous run?"));

        model.findConditionalByName("Shift over?")
                .setTrueBranchNode(model.getStep("Return clipboard, keys, bus").setEvidence("D1-63"));

        model.findConditionalByName("Is it lunch time?")
                .setTrueBranchNode(model.getStep("Have lunch and notify dispatch").setEvidence("D1-57"))
                .setNextNode(model.getStep("Switch to All Zones").setEvidence("D1-29"))
                .setNextNode(model.getStep("Pick up students at Escort stop"));

        model.findStepByName("Start dropping off based on custom route")
                .setTriggerNode(model.getStep("Start dropping off based on custom route"), "Forget people (D1-58)");

        model.findStepByName("Start dropping off based on custom route")
                .setTriggerNode(model.getStep("Call dispatch"), "Road closed/construction (D1-52); Skipped people (D1-48)")
                .setNextNode(model.getStep("Start dropping off based on custom route"));

        model.findStepByName("Pick up the bus at Clyde street").setBreakdown(true);
        model.findConditionalByName("Skipped people in previous run?").setBreakdown(true);
        model.findStepByName("Switch to All Zones").setBreakdown(true);
        model.findStepByName("Start dropping off based on custom route").setBreakdown(true);

        model.saveGoJSResultJSON();

    }
}

