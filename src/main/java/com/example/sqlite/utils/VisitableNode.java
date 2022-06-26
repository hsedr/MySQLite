package com.example.sqlite.utils;

public interface VisitableNode {
    void accept(Visitor visitor);
}
