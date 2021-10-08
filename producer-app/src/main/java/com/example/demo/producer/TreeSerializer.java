package com.example.demo.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class TreeSerializer implements Serializer<Tree<Integer>> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public byte[] serialize(String s, Tree<Integer> integerTree) {
        return objectMapper.writeValueAsBytes(integerTree);
    }
}
