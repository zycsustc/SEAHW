package com.sea.hw.train.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private Graph graph;

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

        graph = new Graph(vertices, edges);
    }

    @Test
    void shouldConstructCorrectGraph() {
        assertEquals(edges, graph.getEdges());
        assertEquals(vertices, graph.getVertices());
    }

    @Test
    void shouldConstructCorrectGraphGivenOneStringInput() {
        Graph graph = new Graph("AB5,BC6");

        assertEquals(edges.toString(), graph.getEdges().toString());
        assertEquals(vertices.toString(), graph.getVertices().toString());
    }

    @Test
    void shouldGetCorrectVertexInGraphById() {
        Vertex vertexFromGraph = graph.getVertexInVertices("A");

        assertEquals(vertices.get(0), vertexFromGraph);
    }

    @Test
    void shouldGetCorrectLinkedVertexByStopsInGraph() {
        ArrayList<String> stopsIdList = new ArrayList<>();
        stopsIdList.add("A");
        stopsIdList.add("B");
        stopsIdList.add("C");

        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stopsIdList);

        assertEquals(vertices.get(0), path.get(0));
        assertEquals(vertices.get(1), path.get(1));
        assertEquals(vertices.get(2), path.get(2));
    }

    @Test
    void shouldGetCorrectDistanceBetweenTwoVerticesInGraph(){
        assertEquals(5, graph.getDistanceBetweenTwoVertices(vertices.get(0), vertices.get(1)));
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoEtoBtoCtoD() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("B");
        stops.add("C");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);
        assertEquals(11, graph.getDistanceByPathInGraph(path));
    }
}
