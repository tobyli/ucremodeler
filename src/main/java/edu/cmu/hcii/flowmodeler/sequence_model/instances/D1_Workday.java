package edu.cmu.hcii.flowmodeler.sequence_model.instances;

import edu.cmu.hcii.flowmodeler.sequence_model.model.SequenceModel;

public class D1_Workday {
    public static void main(String[] args) {
        SequenceModel model = new SequenceModel("D1_Workday")
                .setStepColor("lightyellow")
                .setConditionalColor("lightpink");

        model.saveGoJSResultJSON();

    }
}

