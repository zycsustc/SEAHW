package com.sea.hw.train.module;

import com.sea.hw.train.constant.ConditionConstant;
import com.sea.hw.train.constant.MessageConstant;
import com.sea.hw.train.exception.InvalidConditionException;

import java.util.*;

public class TrainSystemCalculator {
    private final Graph graph;
    private final LinkedList<Vertex> visitedList = new LinkedList<>();
    private final ArrayList<String> resultPaths = new ArrayList<>();
    private final ConditionConstant conditionConstant = new ConditionConstant();
    private final MessageConstant messageConstant = new MessageConstant();

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
                return messageConstant.ROUTE_NOT_FOUND;
            }
        }
        return messageConstant.ROUTE_FOUND;
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

    public int getNumberOfPathConditionedOnStops(Vertex start, Vertex end, String Condition, int stopNumber) {
        resultPaths.clear();
        if(start.getId().equals(end.getId())){
            try {
                return getPathsByConditionOnStopsSameStartAndEnd(start, Condition, stopNumber).size();
            } catch (InvalidConditionException e) {
                e.printStackTrace();
            }
        } else {
            getAllPathsWithExactStops(start, end, stopNumber);
            return resultPaths.size();
        }
        return Integer.MAX_VALUE;
    }

    public int getNumberOfPathWithMaxDistance(Vertex start, Vertex end, int maxDistance) {
        resultPaths.clear();
        getAllPathsWithMaxDistance(start, end, maxDistance);
        return resultPaths.size();
    }

    private ArrayList<String> getPathsByConditionOnStopsSameStartAndEnd(
            Vertex start, String Condition, int stopNumber) throws InvalidConditionException {
        ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator(graph);
        try {
            if (Condition.equals(conditionConstant.EQUAL)) {
                getAllPathsWithExactStops(start, start, stopNumber);
                return resultPaths;
            } else {
                ArrayList<LinkedList<Vertex>> paths = shortestPathCalculator.getPathsSameStartAndEnd(start);
                ArrayList<java.lang.String> result = new ArrayList<>();
                for (LinkedList<Vertex> path : paths) {
                    if (path.size() <= stopNumber + 1) {
                        result.add(path.toString());
                    }
                }
                return result;
            }
        } catch (Exception e) {
            throw new InvalidConditionException("Invalid condition type");
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

    private void getAllPathsWithMaxDistance(Vertex startVertex, Vertex endVertex, int maxDistance) {
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
