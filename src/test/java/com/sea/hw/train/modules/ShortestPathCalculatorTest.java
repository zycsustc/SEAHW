package com.sea.hw.train.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathCalculatorTest {
    private Graph graph;
    private ShortestPathCalculator shortestPathCalculator;

    @BeforeEach
    void setUp() {
        ArrayList<Vertex> vertexes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");
        Vertex vertexC = new Vertex("C");
        Vertex vertexD = new Vertex("D");
        Vertex vertexE = new Vertex("E");

        vertexes.add(vertexA);
        vertexes.add(vertexB);
        vertexes.add(vertexC);
        vertexes.add(vertexD);
        vertexes.add(vertexE);

        edges.add(new Edge("Edge_0", vertexA, vertexB, 5));
        edges.add(new Edge("Edge_1", vertexB, vertexC, 4));
        edges.add(new Edge("Edge_2", vertexC, vertexD, 8));
        edges.add(new Edge("Edge_3", vertexD, vertexC, 8));
        edges.add(new Edge("Edge_4", vertexD, vertexE, 6));
        edges.add(new Edge("Edge_5", vertexA, vertexD, 5));
        edges.add(new Edge("Edge_6", vertexC, vertexE, 2));
        edges.add(new Edge("Edge_7", vertexE, vertexB, 3));
        edges.add(new Edge("Edge_8", vertexA, vertexE, 7));

        graph = new Graph(vertexes, edges);
        shortestPathCalculator = new ShortestPathCalculator(graph);
    }

    @Test
    void shouldReturnShortestDistanceDifferentStartAndEnd() {
        Vertex start = graph.getVertexInVertices("A");
        Vertex end = graph.getVertexInVertices("C");
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("B");
        stops.add("C");
        LinkedList<Vertex> expectedPath = graph.getLinkedVertexByStopsInGraph(stops);

        shortestPathCalculator.initShortestPathCalculator(start);
        LinkedList<Vertex> path = shortestPathCalculator.getShortestPathDifferentStartAndEnd(end);

        assertEquals(expectedPath, path);
    }


}
