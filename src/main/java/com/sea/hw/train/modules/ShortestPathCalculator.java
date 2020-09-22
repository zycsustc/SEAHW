package com.sea.hw.train.modules;

import java.util.*;

public class ShortestPathCalculator {
    private final Graph graph;
    private Map<Vertex, Vertex> predecessors;
    private Set<Vertex> settledVertices;
    private Set<Vertex> unSettledVertices;
    private Map<Vertex, Integer> distance;

    public ShortestPathCalculator(Graph graph) {
        this.graph = graph;
    }

    public void initShortestPathCalculator(Vertex start) {
        settledVertices = new HashSet<>();
        unSettledVertices = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(start, 0);
        unSettledVertices.add(start);
        while (unSettledVertices.size() > 0) {
            Vertex vertex = getMinimum(unSettledVertices);
            settledVertices.add(vertex);
            unSettledVertices.remove(vertex);
            findMinimalDistances(vertex);
        }
    }

    public LinkedList<Vertex> getShortestPathDifferentStartAndEnd(Vertex end) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = end;
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

    public LinkedList<Vertex> getShortestPathSameStartAndEnd(Vertex start) {
        int minDistance = Integer.MAX_VALUE;
        LinkedList<Vertex> shortestPath = new LinkedList<>();
        ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator(this.graph);
        ArrayList<LinkedList<Vertex>> paths = shortestPathCalculator.getPathsSameStartAndEnd(start);
        if (paths.size() <= 0) {
            return null;
        }
        for (LinkedList<Vertex> path : paths) {
            int distance = graph.getDistanceByPathInGraph(path);
            minDistance = Math.min(distance, minDistance);
            shortestPath = minDistance == distance ? path : shortestPath;
        }
        return shortestPath;
    }

    public ArrayList<LinkedList<Vertex>> getPathsSameStartAndEnd(Vertex start) {
        ArrayList<LinkedList<Vertex>> paths = new ArrayList<>();
        List<Vertex> adjacentVertices = getAdjacentVertices(start);
        for (Vertex newStart : adjacentVertices) {
            initShortestPathCalculator(newStart);
            if (getShortestPathDifferentStartAndEnd(start) != null) {
                LinkedList<Vertex> wholePath = getShortestPathDifferentStartAndEnd(start);
                wholePath.add(0, start);
                paths.add(wholePath);
            }
        }
        return paths;
    }

    private List<Vertex> getAdjacentVertices(Vertex vertex) {
        List<Vertex> adjacentVertices = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(vertex)) {
                adjacentVertices.add(edge.getDestination());
            }
        }
        return adjacentVertices;
    }

    private void findMinimalDistances(Vertex vertex) {
        List<Vertex> adjacentVertices = getVertexNeighbors(vertex);
        for (Vertex end : adjacentVertices) {
            if (getShortestDistance(end) > getShortestDistance(vertex)
                    + graph.getDistanceBetweenTwoVertices(vertex, end)) {
                distance.put(end, getShortestDistance(vertex)
                        + graph.getDistanceBetweenTwoVertices(vertex, end));
                predecessors.put(end, vertex);
                unSettledVertices.add(end);
            }
        }
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

    private boolean isSettled(Vertex vertex) {
        return settledVertices.contains(vertex);
    }

}
