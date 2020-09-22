package com.sea.hw.train.modules;

import java.util.LinkedList;

public class TrainSystemCalculator {
    private final Graph graph;

    public TrainSystemCalculator(Graph graph) {
        this.graph = graph;
    }

    public int getDistanceByPath(LinkedList<Vertex> path) {
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

    public String getExactPath(LinkedList<Vertex> exactPath) {
        for (Vertex vertex : exactPath) {
            if (vertex.equals(exactPath.getLast())) {
                break;
            } else if (getDistanceBetweenTwoVertices(vertex, exactPath.get(exactPath.indexOf(vertex) + 1)) == Integer.MAX_VALUE) {
                return "NO SUCH ROUTE";
            }
        }
        return "ROUTE FIND";
    }

    private int getDistanceBetweenTwoVertices(Vertex vertex, Vertex target) {
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(vertex)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        return Integer.MAX_VALUE;
    }
}
