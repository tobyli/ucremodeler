package edu.cmu.hcii.flowmodeler.flow_model.instances;

import edu.cmu.hcii.flowmodeler.flow_model.model.FlowModel;

/**
 * test
 */
public class D0InterviewMain {
    public static void main(String[] args) {

        FlowModel flowModel = new FlowModel("LtInterview");
        flowModel.setRoleColor("lightblue")
                .setArtifactColor("lightgreen");

        flowModel.addNewFirstLevelRole("CMU")
                .addNewFirstLevelRole("Port Authority")
                .addNewFirstLevelRole("Federal Office")
                .addNewFirstLevelRole("PA State");

        flowModel.findEntityByName("CMU")
                .addNewSubRole("Police")
                .addNewSubRole("GSA")
                .addNewSubRole("Committee")
                .addNewSubRole("Parking & Transportation")
                .addNewSubRole("Students")
                .addNewSubArtifact("Calendar")
                .addNewSubRole("Transportation service");

        flowModel.findEntityByName("Police")
                .addNewSubRole("L0")
                .addNewSubRole("L1")
                .addNewSubRole("L2")
                .addNewSubRole("Chief")
                .addNewSubRole("Special Ops")
                .addNewSubRole("Transportation Division");

        flowModel.findEntityByName("Special Ops")
                .addNewSubRole("Police without guns");


        flowModel.findEntityByName("Transportation Division")
                .addNewSubRole("SGT")
                .addNewSubRole("Shift coordinator")
                .addNewSubRole("Drivers")
                .addNewSubRole("Dispatcher")
                .addNewSubArtifact("Zones")
                .addNewSubArtifact("Schedule driver")
                .addNewSubArtifact("Schedule maintenance")
                .addNewSubArtifact("Schedule route")
                .addNewSubArtifact("Emergency")
                .addNewSubArtifact("Bus")
                .addNewSubArtifact("Shuttle")
                .addNewSubArtifact("Escort")
                .addNewSubArtifact("Worktime regulation")
                .addNewSubArtifact("Database");

        flowModel.findEntityByName("Zones")
                .addNewSubArtifact("Red Zone")
                .addNewSubArtifact("Yellow Zone")
                .addNewSubArtifact("Blue Zone")
                .addNewSubArtifact("Green Zone");

        flowModel.findEntityByName("Parking & Transportation")
                .addNewSubArtifact("Parking")
                .addNewSubArtifact("Road issues")
                .addNewSubRole("Director");

        flowModel.findEntityByName("Committee")
                .addExistingEntityByName("L0")
                .addExistingEntityByName("Director");



        flowModel.findEntityByName("Students")
                .addNewSubRole("Riders");

        //test adding flows
        flowModel.addFlowByNames("L0", "L1", "seeks advice")
                .addFlowByNames("Chief", "Drivers", "interviews")
                .addFlowByNames("L0", "Special Ops", "manages")
                .addFlowByNames("L0", "Transportation Division", "manages")
                .addFlowByNames("L0", "Database", "reviews")
                .addFlowByNames("L0", "Shift coordinator", "talks to")
                .addFlowByNames("SGT", "Worktime regulation", "monitors")
                .addFlowByNames("SGT", "Emergency", "deals with")
                .addFlowByNames("GSA", "Committee", "works with")
                .addFlowByNames("GSA", "Schedule route", "studies with survey")
                .addFlowByNames("L2", "Schedule route", "studies with survey")
                .addFlowByNames("Shift coordinator", "Schedule route", "manages")
                .addFlowByNames("Shift coordinator", "Schedule driver", "manages")
                .addFlowByNames("Schedule maintenance", "Bus", "manages")
                .addFlowByNames("Escort", "Bus", "uses")
                .addFlowByNames("Shuttle", "Bus", "uses")
                .addFlowByNames("Dispatcher", "Drivers", "assigns")
                .addFlowByNames("Dispatcher", "Database", "enters")
                .addFlowByNames("Escort", "Zones", "constrained by")
                .addFlowByNames("Drivers", "Escort", "drives")
                .addFlowByNames("Drivers", "Shuttle", "drives")
                .addFlowByNames("Riders", "Escort", "uses")
                .addFlowByNames("Riders", "Shuttle", "uses")
                .addFlowByNames("Students", "Transportation service", "pays")
                .addFlowByNames("Transportation service", "Port Authority", "pays")
                .addFlowByNames("Transportation service", "Parking & Transportation", "pays")
                .addFlowByNames("Transportation service", "Transportation Division", "pays")
                .addFlowByNames("PA State", "Drivers", "regulates")
                .addFlowByNames("Federal Office", "Worktime regulation", "regulates");

        /*
        System.out.println(new Gson().toJson(model));
        System.out.println(new Gson().toJson(model.getGoJSNodeList()));
        */

        flowModel.saveToJsonFile();
        flowModel.saveGoJSResultJSON();
    }
}
