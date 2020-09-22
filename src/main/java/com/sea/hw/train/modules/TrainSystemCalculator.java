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

    public int getNumberOfPathConditionedOnStops(Vertex start, Vertex end, String Condition, int number) {
        if(start.getId().equals(end.getId())){
            return getPathsByConditionOnStopsSameStartAndEnd(start, Condition, number).size();
        } else {
            getAllPathsWithExactStops(start, end, number);
            return resultPaths.size();
        }
    }

    private ArrayList<String> getPathsByConditionOnStopsSameStartAndEnd(
            Vertex start, String Condition, int number) {
        ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator(graph);
        if (Condition.equals("Equal")) {
            getAllPathsWithExactStops(start, start, number);
            return resultPaths;
        } else {
            ArrayList<LinkedList<Vertex>> paths = shortestPathCalculator.getPathsSameStartAndEnd(start);
            ArrayList<java.lang.String> result = new ArrayList<>();
            for (LinkedList<Vertex> path : paths) {
                if (path.size() <= number + 1) {
                    result.add(path.toString());
                }
            }
            return result;
        }
    }

    private void getAllPathsWithExactStops(Vertex startVertex, Vertex endVertex, int stopNumber) {
        visitedList.add(startVertex);
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(startVertex)) {
                if (edge.getDestination().equals(endVertex) && visitedList.size() == stopNumber) {
                    resultPaths.add(visitedList.toString().substring(0, visitedList.toString().lastIndexOf("]")) + ", " + endVertex + "]");
                    continue;
                }
                if (visitedList.size() <= stopNumber) {
                    getAllPathsWithExactStops(edge.getDestination(), endVertex, stopNumber);
                }
            }
        }
        visitedList.remove(startVertex);
    }

    public void getAllPathsWithMaxDistance(Vertex startVertex, Vertex endVertex, int maxDistance) {
        visitedList.add(startVertex);
        int distance = getDistanceByPath(visitedList);
        if (distance < maxDistance) {
            for (Edge edge : graph.getEdges()) {
                if (edge.getSource().equals(startVertex)) {
                    if (edge.getDestination().equals(endVertex) && distance + edge.getWeight() < maxDistance) {
                        resultPaths.add(visitedList.toString().substring(0, visitedList.toString().lastIndexOf("]")) + ", " + endVertex + "]");
                    }
                    getAllPathsWithMaxDistance(edge.getDestination(), endVertex, maxDistance);
                }
            }
        }
        visitedList.remove(visitedList.size() - 1);
    }
}
