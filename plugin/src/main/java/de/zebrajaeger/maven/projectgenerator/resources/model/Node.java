package de.zebrajaeger.maven.projectgenerator.resources.model;

import de.zebrajaeger.maven.projectgenerator.resources.path.ResourcePath;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Lars Brandt, Silpion IT Solutions GmbH
 */
public class Node extends Item {
    private Map<String, Resource> resources = new HashMap<>();
    private Map<String, Node> nodes = new HashMap<>();

    public Node() {
        super(null, null);
    }

    public Node(String name) {
        super(null, name);
    }

    public Node(Node parent, String name) {
        super(parent, name);
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
        item.setParent(this);
        if (path.size() == 1) {
            add(item);
        } else if (path.size() > 1) {
            Node node = getOrCreateNode(path.get(0));
            node.add(path.subList(1, path.size()), item);
        }
    }

    public Item getItem(ResourcePath path) {
        if (path.getSize() == 0) {
            return this;
        } else if (path.getSize() == 1) {
            Resource resource = resources.get(path.getFirst());
            if (resource != null) {
                return resource;
            }
            Node node = nodes.get(path.getFirst());
            return node;
        } else if (path.getSize() > 1) {
            Node node = nodes.get(path.getFirst());
            if (node == null) {
                return null;
            }
            return node.getItem(path.withoutFirst());
        }
        return null;
    }

    public List<Item> getItems(boolean recursive) {
        List<Item> result = new LinkedList<>();

        result.addAll(resources.values());
        if (recursive) {
            nodes.values().stream()
                    .forEach(node -> {
                        result.add(node);
                        result.addAll(node.getItems(true));
                    });
        }

        return result;
    }

    private Node getOrCreateNode(String name) {
        Node node = nodes.get(name);
        if (node == null) {
            node = new Node(this, name);
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

    @Override
    public boolean isRoot() {
        return getParent()==null;
    }
}
