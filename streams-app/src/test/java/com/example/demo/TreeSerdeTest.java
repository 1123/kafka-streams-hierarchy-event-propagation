package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class TreeSerdeTest {

	@Test
	void test() {
		TreeSerde treeSerde = new TreeSerde();
		Tree<Integer> tree = new Tree<>(List.of(), List.of(1,2,3));
		var serialized = treeSerde.serializer().serialize("t1", tree);
		var deserialized = treeSerde.deserializer().deserialize("t1", serialized);
		log.info(deserialized.toString());
	}

}
