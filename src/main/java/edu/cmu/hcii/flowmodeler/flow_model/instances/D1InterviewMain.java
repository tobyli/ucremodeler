package edu.cmu.hcii.flowmodeler.flow_model.instances;

import edu.cmu.hcii.flowmodeler.flow_model.model.FlowModel;

public class D1InterviewMain {
    public static void main(String[] args) {
        FlowModel flowModel = new FlowModel("D1");
        flowModel.setRoleColor("lightblue")
                .setArtifactColor("lightgreen");

        flowModel.addNewFirstLevelRole("CMU")
                .addNewFirstLevelRole("Mechanics");

        flowModel.findEntityByName("CMU")
                .addNewSubArtifact("Transportation division")
                .addNewSubArtifact("6 escort pick up stops");

        flowModel.findEntityByName("Transportation division")
                .addNewSubArtifact("Shuttle services")
                .addNewSubRole("Drivers")
                .addNewSubArtifact("Vehicle");

        flowModel.findEntityByName("Drivers")
                .addNewSubArtifact("Shuttle drivers")
                .addNewSubArtifact("Escort drivers")
                .addNewSubRole("Shift coordinator");

        flowModel.findEntityByName("Shuttle drivers")
                .addNewSubRole("D0");

        //flowModel.addFlowByNames("CMU", "Transportation division", "funds");
        flowModel.addFlowByNames("Shuttle drivers", "Shuttle services", "drives");
        flowModel.addFlowByNames("D0", "Vehicle", "needs");
        flowModel.addFlowByNames("D0", "Shift coordinator", "work order");
        flowModel.addFlowByNames("Escort drivers", "6 escort pick up stops", "pick up from");
        flowModel.addFlowByNames("Vehicle", "Mechanics", "needs repair from");
        flowModel.addFlowByNames("Mechanics", "Vehicle", "long time for repair");

        flowModel.saveGoJSResultJSON();

    }
}