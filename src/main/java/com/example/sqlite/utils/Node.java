package com.example.sqlite.utils;

import java.util.ArrayList;
import java.util.List;

public class Node implements VisitableNode {

    private List<Node> children = new ArrayList<>();
    private Node parent = null;
    private String data = null;

    public static Node parseNode(String data){
        return new Node(data);
    }

    public Node(String data) {
        this.data = data;
    }

    public Node(String data, Node parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setParent(Node parent) {
        //parent.addChild(this);
        this.parent = parent;
    }

    public void addChild(String data) {
        Node child = new Node(data);
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(int index, Node child) {
        child.setParent(this);
        this.children.add(index, child);
    }

    public void addChildren(List<Node> children) {
        for (Node child : children) this.addChild(child);
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public void removeParent() {
        this.parent = null;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitNode(this);
    }
}