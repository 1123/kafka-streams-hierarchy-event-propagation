package com.example.demo.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TreeSender {

    @Autowired
    private KafkaTemplate<String, Tree<Integer>> containerTemplate;

    @Autowired
    private KafkaTemplate<String, String> eventTemplate;

    @Scheduled(initialDelay = 1000, fixedDelay = 15000)
    public void sendContainers() {
        log.info("sending tree");
        containerTemplate.send("trees",
                "3",
                new Tree<>(
                        List.of(
                              new Tree<>(List.of(), List.of(4)),
                              new Tree<>(List.of(), List.of(5)),
                              new Tree<>(List.of(), List.of(6))
                        ), List.of(7)
                )
        );
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void sendTreeEvents() {
        log.info("sending tree event");
        eventTemplate.send("tree-events",
                "3",
                UUID.randomUUID().toString()
        );
    }

}
