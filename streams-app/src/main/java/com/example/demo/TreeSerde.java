package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class TreeSerde implements Serde<Tree<Integer>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Serializer<Tree<Integer>> serializer() {
        return new TreeSerializer();
    }

    @Override
    public Deserializer<Tree<Integer>> deserializer() {
        return new Deserializer<>() {
            @SneakyThrows
            @Override
            public Tree<Integer> deserialize(String s, byte[] bytes) {
                return objectMapper.readValue(bytes, new TypeReference<>() {
                });
            }
        };
    }
}
