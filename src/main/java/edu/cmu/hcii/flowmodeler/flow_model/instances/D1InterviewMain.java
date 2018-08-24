package edu.cmu.hcii.flowmodeler.flow_model.instances;

import edu.cmu.hcii.flowmodeler.flow_model.model.FlowModel;

public class D1InterviewMain {
    public static void main(String[] args) {
        FlowModel flowModel = new FlowModel("D1");
        flowModel.setRoleColor("violet")
                .setArtifactColor("aquamarine");

        flowModel.addNewFirstLevelRole("CMU", "E1", false)
                .addNewFirstLevelRole("Mechanics");

        flowModel.findEntityByName("CMU")
                .addNewSubArtifact("Transportation division", "E2", true)
                .addNewSubArtifact("Escort stops", true);

        flowModel.findEntityByName("Transportation division")
                .addNewSubArtifact("Shuttle services")
                .addNewSubRole("Drivers")
                .addNewSubArtifact("Vehicle");

        flowModel.findEntityByName("Drivers")
                .addNewSubArtifact("Shuttle drivers")
                .addNewSubArtifact("Escort drivers")
                .addNewSubRole("Shift coordinator");

        flowModel.findEntityByName("Shuttle drivers")
                .addNewSubRole("D0", "E3");

        //flowModel.addFlowByNames("CMU", "Transportation division", "funds");
        flowModel.addFlowByNames("Shuttle drivers", "Shuttle services", "drives", "LE1", true);
        flowModel.addFlowByNames("D0", "Vehicle", "needs", true);
        flowModel.addFlowByNames("D0", "Shift coordinator", "work order");
        flowModel.addFlowByNames("Escort drivers", "Escort stops", "pick up from");
        flowModel.addFlowByNames("Vehicle", "Mechanics", "needs repair from");
        flowModel.addFlowByNames("Mechanics", "Vehicle", "long time for repair");

        flowModel.saveGoJSResultJSON();

    }
}