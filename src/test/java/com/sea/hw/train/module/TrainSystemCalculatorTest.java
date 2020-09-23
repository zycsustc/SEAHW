package com.sea.hw.train.module;

import com.sea.hw.train.constant.DefaultConstant;
import com.sea.hw.train.constant.MessageConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class TrainSystemCalculatorTest {
    private Graph graph;
    private TrainSystemCalculator trainSystemCalculator;
    private final MessageConstant messageConstant = new MessageConstant();
    private final DefaultConstant defaultConstant = new DefaultConstant();

    @BeforeEach
    void setUp() {
        graph = new Graph(defaultConstant.defaultGraph);
        trainSystemCalculator = new TrainSystemCalculator(graph);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoBtoC() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("B");
        stops.add("C");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        assertEquals(messageConstant.ROUTE_FOUND, trainSystemCalculator.getExactPath(path));
        assertEquals(9, trainSystemCalculator.getDistanceByPath(path));
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoD() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("D");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        assertEquals(messageConstant.ROUTE_FOUND, trainSystemCalculator.getExactPath(path));
        assertEquals(5, trainSystemCalculator.getDistanceByPath(path));
    }

    @Test
    void shouldReturnNoSuchRouteMessageExactlyAtoEtoD() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("E");
        stops.add("D");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        assertEquals(messageConstant.ROUTE_NOT_FOUND, trainSystemCalculator.getExactPath(path));
    }

    @Test
    void shouldReturnShortestDistanceFromAtoC() {
        Vertex start = graph.getVertexInVertices("A");
        Vertex end = graph.getVertexInVertices("C");

        LinkedList<Vertex> path = trainSystemCalculator.getShortestPath(start, end);
        int distance = trainSystemCalculator.getDistanceByPath(path);

        assertEquals(9, distance);
    }

    @Test
    void shouldReturnShortestDistanceFromBtoB() {
        Vertex start = graph.getVertexInVertices("B");

        LinkedList<Vertex> path = trainSystemCalculator.getShortestPath(start, start);
        int distance = trainSystemCalculator.getDistanceByPath(path);

        assertEquals(9, distance);
    }

    @Test
    void shouldReturnNumberOfPathFromCtoCWithMaximumThreeStops(){
        Vertex start = graph.getVertexInVertices("C");
        int stopNumbers = 3;

        assertEquals(2, trainSystemCalculator.getNumberOfPathConditionedOnStops(start, start, "Max", stopNumbers));
    }

    @Test
    void shouldReturnNumberOfPathFromCtoCWithExactlyThreeStops() {
        Vertex start = graph.getVertexInVertices("C");
        int stopNumbers = 3;

        assertEquals(1, trainSystemCalculator.getNumberOfPathConditionedOnStops(start, start, "Equal", stopNumbers));
    }

    @Test
    void shouldFindAllPathsFromAtoCWithFourStops() {
        Vertex start = graph.getVertexInVertices("A");
        Vertex end = graph.getVertexInVertices("C");
        int stopNumber = 4;

        assertEquals(3, trainSystemCalculator.getNumberOfPathConditionedOnStops(start, end, "Equal", stopNumber));
    }

    @Test
    void shouldFindAllPathsFromCtoCWithMaxDistance30() {
        Vertex start = graph.getVertexInVertices("C");
        Vertex end = graph.getVertexInVertices("C");

        assertEquals(7, trainSystemCalculator.getNumberOfPathWithMaxDistance(start, end, 30));
    }

    @Test
    void shouldFindAllPathsFromAtoCWithMaxDistance15() {
        Vertex start = graph.getVertexInVertices("A");
        Vertex end = graph.getVertexInVertices("C");

        assertEquals(3, trainSystemCalculator.getNumberOfPathWithMaxDistance(start, end, 15));
    }
}
