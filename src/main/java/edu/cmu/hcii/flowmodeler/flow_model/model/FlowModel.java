package edu.cmu.hcii.flowmodeler.flow_model.model;


import com.google.gson.Gson;
import edu.cmu.hcii.flowmodeler.gojsadapter.GoJSLink;
import edu.cmu.hcii.flowmodeler.gojsadapter.GoJSNode;
import edu.cmu.hcii.flowmodeler.gojsadapter.GoJSResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FlowModel {
    String name;
    transient Map<Integer, Entity> entityIdEntitiesMap;
    transient Map<String, Entity> entityNameEntitiesMap;
    transient Set<Entity> allEntities;
    Set<Flow> allFlows;
    String roleColor = "lightblue";
    String artifactColor = "lightgreen";

    List<Entity> firstLevelEntity;

    public FlowModel(String name) {
        this.name = name;
        this.entityIdEntitiesMap = new HashMap<>();
        this.entityNameEntitiesMap = new HashMap<>();
        this.allEntities = new HashSet<>();
        this.allFlows = new HashSet<>();
        this.firstLevelEntity = new ArrayList<>();
    }

    protected void processNewEntity(Entity entity) {
        entity.setParentModel(this);
        entityIdEntitiesMap.put(entity.entityId, entity);
        entityNameEntitiesMap.put(entity.entityName, entity);
        allEntities.add(entity);
    }

    protected Integer getNextEntityId() {
        return allEntities.size() + 1;
    }

    public Entity findEntityByName(String name) {
        return entityNameEntitiesMap.get(name);
    }

    protected Entity findEntityById(Integer id) {
        return entityIdEntitiesMap.get(id);
    }


    public FlowModel addNewFirstLevelArtifact(String entityName, String entityText) {
        Artifact newArtifact = new Artifact(getNextEntityId(), entityName, entityText);
        firstLevelEntity.add(newArtifact);
        processNewEntity(newArtifact);
        return this;
    }

    public FlowModel addNewFirstLevelArtifact(String entityText) {
        return addNewFirstLevelArtifact(entityText, entityText);
    }

    public FlowModel addNewFirstLevelRole(String entityName, String entityText) {
        Role newRole = new Role(getNextEntityId(), entityName, entityText);
        firstLevelEntity.add(newRole);
        processNewEntity(newRole);
        return this;
    }

    public FlowModel addNewFirstLevelRole(String entityText) {
        return addNewFirstLevelRole(entityText, entityText);
    }

    public FlowModel addFlowByNames(String fromName, String toName, String label) {
        Entity fromEntity = findEntityByName(fromName);
        Entity toEntity = findEntityByName(toName);
        if (fromEntity != null && toEntity != null) {
            allFlows.add(new Flow(fromEntity, toEntity, label));
        } else {
            System.err.println("ERROR: Can't find an entity with the specified name(s): " + (fromEntity == null ? fromName + " " : "") + (toEntity == null ? toName : ""));
            throw new RuntimeException();
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public void saveGoJSResultJSON() {
        writeStringtoFile(new Gson().toJson(new GoJSResult(getGoJSNodeList(), getGoJSLinkList())), this.getName() + "_gojs.json");
    }

    public void saveToJsonFile() {
        writeStringtoFile(new Gson().toJson(this), this.getName() + ".json");
    }

    static public void writeStringtoFile(String string, String fileName) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(fileName);
            bw = new BufferedWriter(fw);
            bw.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<GoJSLink> getGoJSLinkList() {
        List<GoJSLink> results = new ArrayList<>();
        for (Flow flow : allFlows) {
            GoJSLink link = new GoJSLink(flow.fromEntityId, flow.toEntityId, flow.label, "black");
            results.add(link);
        }
        return results;
    }

    public List<GoJSNode> getGoJSNodeList() {
        List<GoJSNode> results = new ArrayList<>();
        for (Entity entity : firstLevelEntity) {
            GoJSNode node = new GoJSNode(entity, getColor(entity), getCategory(entity));
            results.add(node);
            results.addAll(getChildGoJSNodeList(entity));
        }
        return results;
    }

    private String getCategory(Entity entity) {
        if (entity instanceof Artifact) {
            return "artifact";
        }
        if (entity instanceof Role) {
            return "role";
        }
        return null;
    }

    private String getColor(Entity entity){
        if (entity instanceof Artifact) {
            return artifactColor;
        } else {
            return roleColor;
        }
    }

    public FlowModel setRoleColor(String roleColor) {
        this.roleColor = roleColor;
        return this;
    }

    public FlowModel setArtifactColor(String artifactColor) {
        this.artifactColor = artifactColor;
        return this;
    }

    private List<GoJSNode> getChildGoJSNodeList(Entity parentEntity) {
        List<GoJSNode> results = new ArrayList<>();
        if (parentEntity.getSubEntities() != null) {
            for (Entity entity : parentEntity.getSubEntities()) {
                try {
                    GoJSNode node = new GoJSNode(entity, getColor(entity), getCategory(entity));
                    node.setGroup(parentEntity.entityId);
                    results.add(node);
                    results.addAll(getChildGoJSNodeList(entity));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }
}
