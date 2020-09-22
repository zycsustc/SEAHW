package com.sea.hw.train.modules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    @Test
    void shouldConstructCorrectEdge() {
        Vertex start = new Vertex("A");
        Vertex end = new Vertex("B");
        int weight = 5;
        String id = "id";
        Edge edge = new Edge(id, start, end, weight);

        assertEquals(weight, edge.getWeight());
        assertEquals(start, edge.getSource());
        assertEquals(end, edge.getDestination());
    }
}
