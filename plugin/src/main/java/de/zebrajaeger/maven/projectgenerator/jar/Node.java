package de.zebrajaeger.maven.projectgenerator.jar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Node extends Item {
    private Map<String, Resource> resources = new HashMap<>();
    private Map<String, Node> nodes = new HashMap<>();

    public Node(String name) {
        super(name);
    }

    public Node() {
        super(null);
    }

    public void add(Item item) {
        String name = item.getName();
        if (item.isNode()) {
            if (!nodes.containsKey(name)) {
                nodes.put(name, (Node) item);
            }
        } else {
            resources.put(name, (Resource) item);
        }
    }

    public void add(List<String> path, Item item) {
        if (path.size() == 1) {
            add(item);
        } else if (path.size() > 1) {
            Node node = getOrCreateNode(path.get(0));
            node.add(path.subList(1, path.size()), item);
        }
    }

    private Node getOrCreateNode(String name) {
        Node node = nodes.get(name);
        if (node == null) {
            node = new Node(name);
            nodes.put(name, node);
        }
        return node;
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
