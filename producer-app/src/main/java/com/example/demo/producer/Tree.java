package com.example.demo.producer;

import java.util.List;

// Custom tree structure internal nodes do not carry labels.
// This structure was chosen since it is easier to serialize deserialize with Jackson Objectmapper.
// Good enough for the purposes of this demo.
public record Tree<T>(List<Tree<T>> children, List<T> leaves) { }
