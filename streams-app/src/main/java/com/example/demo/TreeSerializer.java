package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serializer;

public class TreeSerializer implements Serializer<Tree<Integer>> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public byte[] serialize(String s, Tree<Integer> integerTree) {
        return objectMapper.writeValueAsBytes(integerTree);
    }
}
