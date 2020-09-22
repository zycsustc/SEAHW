package com.sea.hw.train.modules;

import java.util.*;

public class TrainSystemCalculator {
    private final Graph graph;
    private Map<Vertex, Vertex> predecessors;
    private Set<Vertex> settledVertices;
    private Set<Vertex> unSettledVertices;
    private Map<Vertex, Integer> distance;

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

    public void execute(Vertex source) {
        settledVertices = new HashSet<>();
        unSettledVertices = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0);
        unSettledVertices.add(source);
        while (unSettledVertices.size() > 0) {
            Vertex vertex = getMinimum(unSettledVertices);
            settledVertices.add(vertex);
            unSettledVertices.remove(vertex);
            findMinimalDistances(vertex);
        }
    }

    public LinkedList<Vertex> getShortestPathDifferentStartAndEnd(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = target;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }

    private void findMinimalDistances(Vertex vertex) {
        List<Vertex> adjacentVertices = getVertexNeighbors(vertex);
        for (Vertex target : adjacentVertices) {
            if (getShortestDistance(target) > getShortestDistance(vertex)
                    + getDistanceBetweenTwoVertices(vertex, target)) {
                distance.put(target, getShortestDistance(vertex)
                        + getDistanceBetweenTwoVertices(vertex, target));
                predecessors.put(target, vertex);
                unSettledVertices.add(target);
            }
        }

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

    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        return Objects.requireNonNullElse(d, Integer.MAX_VALUE);
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private List<Vertex> getVertexNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(vertex)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private boolean isSettled(Vertex vertex) {
        return settledVertices.contains(vertex);
    }
}
