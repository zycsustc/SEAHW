package com.sea.hw.train.module;

import com.sea.hw.train.constant.DefaultConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathCalculatorTest {
    private Graph graph;
    private ShortestPathCalculator shortestPathCalculator;
    private final DefaultConstant defaultConstant = new DefaultConstant();

    @BeforeEach
    void setUp() {
        graph = new Graph(defaultConstant.defaultGraph);
        shortestPathCalculator = new ShortestPathCalculator(graph);
    }

    @Test
    void shouldReturnShortestPathDifferentStartAndEnd() {
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

    @Test
    void shouldReturnShortestPathSameStartAndEnd() {
        Vertex start = graph.getVertexInVertices("B");
        ArrayList<String> stops = new ArrayList<>();
        stops.add("B");
        stops.add("C");
        stops.add("E");
        stops.add("B");
        LinkedList<Vertex> expectedPath = graph.getLinkedVertexByStopsInGraph(stops);
        LinkedList<Vertex> path = shortestPathCalculator.getShortestPathSameStartAndEnd(start);

        assertEquals(expectedPath, path);
    }
}
