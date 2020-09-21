package com.sea.hw.train.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    @BeforeEach
    void setUp(){
        vertices = new ArrayList<>();
        edges = new ArrayList<>();

        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");
        Vertex vertexC = new Vertex("C");

        vertices.add(vertexA);
        vertices.add(vertexB);
        vertices.add(vertexC);

        edges.add(new Edge("Edge_0", vertexA, vertexB, 5));
        edges.add(new Edge("Edge_1", vertexB, vertexC, 6));
    }

    @Test
    void shouldConstructCorrectGraph() {
        Graph graph = new Graph(vertices, edges);

        assertEquals(edges, graph.getEdges());
        assertEquals(vertices, graph.getVertices());
    }

    @Test
    void shouldConstructCorrectGraphGivenOneStringInput() {
        Graph graph = new Graph("AB5,BC6");

        assertEquals(edges.toString(), graph.getEdges().toString());
        assertEquals(vertices.toString(), graph.getVertices().toString());
    }
}
