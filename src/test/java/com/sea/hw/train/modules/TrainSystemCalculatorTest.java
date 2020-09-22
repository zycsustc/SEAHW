package com.sea.hw.train.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class TrainSystemCalculatorTest {
    private Graph graph;
    private TrainSystemCalculator trainSystemCalculator;

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
        trainSystemCalculator = new TrainSystemCalculator(graph);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoBtoC() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("B");
        stops.add("C");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        assertEquals("ROUTE FIND", trainSystemCalculator.getExactPath(path));
        assertEquals(9, trainSystemCalculator.getDistanceByPath(path));
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoD() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("D");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        assertEquals("ROUTE FIND", trainSystemCalculator.getExactPath(path));
        assertEquals(5, trainSystemCalculator.getDistanceByPath(path));
    }

    @Test
    void shouldReturnNoSuchRouteMessageExactlyAtoEtoD() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("E");
        stops.add("D");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        assertEquals("NO SUCH ROUTE", trainSystemCalculator.getExactPath(path));
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

        ArrayList<String> paths = trainSystemCalculator.getPathsByConditionOnStopsSameStartAndEnd(start, "Max", 3);

        assertEquals(2, paths.size());
    }

    @Test
    void shouldReturnNumberOfPathFromCtoCWithExactlyThreeStops() {
        Vertex start = graph.getVertexInVertices("C");

        ArrayList<String> paths = trainSystemCalculator.getPathsByConditionOnStopsSameStartAndEnd(start, "Equal", 3);

        assertEquals(1, paths.size());
    }

    @Test
    void shouldFindAllPathsFromAtoCWithFourStops() {
        Vertex start = graph.getVertexInVertices("A");
        Vertex end = graph.getVertexInVertices("C");

        trainSystemCalculator.getAllPathsWithExactStops(start, end, 4);

        assertEquals(3, trainSystemCalculator.resultPaths.size());
    }

    @Test
    void shouldFindAllPathsFromCtoCWithMaxDistance30() {
        Vertex source = graph.getVertexInVertices("C");
        Vertex target = graph.getVertexInVertices("C");

        trainSystemCalculator.getAllPathsWithMaxDistance(source, target, 30);

        assertEquals(7, trainSystemCalculator.resultPaths.size());
    }

    @Test
    void shouldFindAllPathsFromAtoCWithMaxDistance15() {
        Vertex source = graph.getVertexInVertices("A");
        Vertex target = graph.getVertexInVertices("C");

        trainSystemCalculator.getAllPathsWithMaxDistance(source, target, 15);
        assertEquals(3, trainSystemCalculator.resultPaths.size());
    }
}
