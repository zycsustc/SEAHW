package com.sea.hw.train.modules;

import java.util.*;

public class TrainSystemCalculator {
    private final Graph graph;

    public TrainSystemCalculator(Graph graph) {
        this.graph = graph;
    }

    public int getDistanceByPath(LinkedList<Vertex> path) {
        return graph.getDistanceByPathInGraph(path);
    }

    public String getExactPath(LinkedList<Vertex> exactPath) {
        for (Vertex vertex : exactPath) {
            if (vertex.equals(exactPath.getLast())) {
                break;
            } else if (graph.getDistanceBetweenTwoVertices(vertex, exactPath.get(exactPath.indexOf(vertex) + 1)) == Integer.MAX_VALUE) {
                return "NO SUCH ROUTE";
            }
        }
        return "ROUTE FIND";
    }

    public LinkedList<Vertex> getShortestPath(Vertex start, Vertex end) {
        ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator(this.graph);
        if(!start.getId().equals(end.getId())){
            shortestPathCalculator.initShortestPathCalculator(start);
            return shortestPathCalculator.getShortestPathDifferentStartAndEnd(end);
        } else {
            return getShortestPathSameStartAndEnd(start);
        }
    }

    private LinkedList<Vertex> getShortestPathSameStartAndEnd(Vertex start) {
        int minDistance = Integer.MAX_VALUE;
        LinkedList<Vertex> shortestPath = new LinkedList<>();
        ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator(this.graph);
        ArrayList<LinkedList<Vertex>> paths = shortestPathCalculator.getPathsSameStartAndEnd(start, shortestPathCalculator);
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
}
