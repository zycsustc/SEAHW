package com.sea.hw.train.modules;

import java.util.*;

public class TrainSystemCalculator {
    private final Graph graph;
    private final LinkedList<Vertex> visitedList = new LinkedList<>();
    public ArrayList<String> resultPaths = new ArrayList<>();

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
            return shortestPathCalculator.getShortestPathSameStartAndEnd(start);
        }
    }

    public ArrayList<String> getPathsByConditionOnStopsSameStartAndEnd(
            Vertex source, java.lang.String Condition, int number) {
        ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator(graph);
        if (Condition.equals("Equal")) {
            getAllPathsWithExactStops(source, source, number);
            return resultPaths;
        } else {
            ArrayList<LinkedList<Vertex>> paths = shortestPathCalculator.getPathsSameStartAndEnd(source);
            ArrayList<java.lang.String> result = new ArrayList<>();
            for (LinkedList<Vertex> path : paths) {
                if (path.size() <= number + 1) {
                    result.add(path.toString());
                }
            }
            return result;
        }
    }

    public void getAllPathsWithExactStops(Vertex startVertex, Vertex endVertex, int stops) {
        visitedList.add(startVertex);
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(startVertex)) {
                if (edge.getDestination().equals(endVertex) && visitedList.size() == stops) {
                    resultPaths.add(visitedList.toString().substring(0, visitedList.toString().lastIndexOf("]")) + ", " + endVertex + "]");
                    continue;
                }
                if (visitedList.size() <= stops) {
                    getAllPathsWithExactStops(edge.getDestination(), endVertex, stops);
                }
            }
        }
        visitedList.remove(startVertex);
    }
}
