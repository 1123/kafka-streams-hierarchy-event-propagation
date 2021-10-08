package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

public record Tree<T>(List<Tree<T>> children, List<T> leaves) {

    public List<T> allLeaves() {
        var result = children.stream().flatMap(child -> child.allLeaves().stream()).collect(Collectors.toList());
        result.addAll(leaves);
        return result;
    }
}
