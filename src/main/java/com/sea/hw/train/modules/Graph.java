package com.sea.hw.train.modules;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public Graph(ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public Graph(String input) {
        String[] rawEdge = input.split(",");
        List<String> existedVertices = new ArrayList<>();
        for(String raw: rawEdge){
            String startId = raw.split("")[0];
            String endId = raw.split("")[1];
            int weight = Integer.parseInt(raw.split("")[2]);
            if(!existedVertices.contains(startId)){
                existedVertices.add(startId);
                vertices.add(new Vertex(startId));
            }
            if(!existedVertices.contains(endId)){
                existedVertices.add(endId);
                vertices.add(new Vertex(endId));
            }
            edges.add(new Edge(raw, getVertexInVertices(startId), getVertexInVertices(endId), weight));
        }
    }

    public LinkedList<Vertex> getLinkedVertexByStopsInGraph(ArrayList<String> stops) {
        LinkedList<Vertex> path = new LinkedList<>();
        for (String stop : stops) {
            path.add(getVertexInVertices(stop));
        }
        return path;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Vertex getVertexInVertices(String id){
        for(Vertex vertex: vertices){
            if (vertex.getId().equals(id)){
                return vertex;
            }
        }
        return null;
    }

    public int getDistanceBetweenTwoVertices(Vertex vertex, Vertex target) {
        for (Edge edge :edges) {
            if (edge.getSource().equals(vertex)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        return Integer.MAX_VALUE;
    }

    public int getDistanceByPathInGraph(LinkedList<Vertex> path) {
        int distance = 0;
        int vertexIndex = 0;
        Vertex nextVertex;
        if (path.size() < 2) {
            return distance;
        }
        for (Vertex vertex : path) {
            nextVertex = path.get(vertexIndex + 1);
            distance += getDistanceBetweenTwoVertices(vertex, nextVertex);
            vertexIndex += 1;
            if (vertexIndex == path.size() - 1) {
                return distance;
            }
        }
        return distance;
    }
}
