package de.zebrajaeger.maven.projectgenerator.jar;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Node extends Item {
    private Map<String, Resource> resources = new HashMap<>();
    private Map<String, Node> nodes = new HashMap<>();
    private Map<String, Item> items = new HashMap<>();

    public Node(String name) {
        super(name);
    }

    public void add(Item item){
        String name = item.getName();
        if(item.isNode()){
            nodes.put(name, (Node) item);
        } else {
            resources.put(name, (Resource) item);
        }
        items.put(name, item);
    }

    @Override
    public boolean isResource() {
        return false;
    }

    @Override
    public boolean isNode() {
        return true;
    }
}
