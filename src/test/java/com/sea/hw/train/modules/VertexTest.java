package com.sea.hw.train.modules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VertexTest {
    @Test
    void shouldConstructCorrectVertex() {
        String id = "A";
        Vertex vertex = new Vertex(id);

        assertEquals(id, vertex.getId());
    }
}
