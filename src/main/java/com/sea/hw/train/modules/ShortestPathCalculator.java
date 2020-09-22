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

    public void initShortestPathCalculator(Vertex source) {
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



    public ArrayList<LinkedList<Vertex>> getPathsSameStartAndEnd(Vertex start, ShortestPathCalculator shortestPathCalculator) {
        ArrayList<LinkedList<Vertex>> paths = new ArrayList<>();
        List<Vertex> adjacentVertices = getAdjacentVertices(start);
        for (Vertex newStart : adjacentVertices) {
            shortestPathCalculator.initShortestPathCalculator(newStart);
            if (shortestPathCalculator.getShortestPathDifferentStartAndEnd(start) != null) {
                LinkedList<Vertex> wholePath = shortestPathCalculator.getShortestPathDifferentStartAndEnd(start);
                wholePath.add(0, start);
                paths.add(wholePath);
            }
        }
        return paths;
    }

    public List<Vertex> getAdjacentVertices(Vertex vertex) {
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

    private int getDistanceBetweenTwoVertices(Vertex vertex, Vertex target) {
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(vertex)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        return Integer.MAX_VALUE;
    }

    private boolean isSettled(Vertex vertex) {
        return settledVertices.contains(vertex);
    }

}
