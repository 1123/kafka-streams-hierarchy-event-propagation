package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.stream.Collectors;

record EventAndTree(String event, Tree<Integer> tree) {}

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
@Slf4j
public class SampleStreamsApp {

	public static void main(String[] args) {
		SpringApplication.run(SampleStreamsApp.class, args);
	}

	@Bean
	public KStream<Integer, String> kStream(StreamsBuilder kStreamBuilder) {
		KTable<String, Tree<Integer>> treeTable = kStreamBuilder.table("trees", Consumed.with(new Serdes.StringSerde(), new TreeSerde()));
		//treeTable.toStream().peek((k,v)-> log.info(v.toString()));
		KStream<String, String> treeEvents = kStreamBuilder.stream(
				"tree-events",
				Consumed.with(new Serdes.StringSerde(), new Serdes.StringSerde())
		).peek((k,v) -> log.info(v));
		var result = treeEvents.join(treeTable, EventAndTree::new)
				//.peek((k,v) -> log.info("joined: {}", v.toString()))
				.flatMap(
						(k,v)  -> v.tree().allLeaves().stream().map(
								child -> new KeyValue<>(child, v.event())
						).collect(Collectors.toList()))
				.peek((k,v) -> log.info("flattened: {}: {}", k, v));
		result.to("leaf-events", Produced.with(new Serdes.IntegerSerde(), new Serdes.StringSerde()));
		return result;
	}



}

