package com.sea.hw.train.modules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    @Test
    void shouldConstructCorrectEdge() {
        Vertex source = new Vertex("A");
        Vertex target = new Vertex("B");
        int weight = 5;
        String id = "id";
        Edge edge = new Edge(id, source, target, weight);

        assertEquals(weight, edge.getWeight());
        assertEquals(source, edge.getSource());
        assertEquals(target, edge.getDestination());
    }
}
