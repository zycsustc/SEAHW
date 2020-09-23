package com.sea.hw.train;

import com.sea.hw.train.constant.DefaultConstant;
import com.sea.hw.train.constant.MessageConstant;
import com.sea.hw.train.module.Graph;
import com.sea.hw.train.module.TrainSystemCalculator;
import com.sea.hw.train.module.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class TrainManagementSystem {
    DefaultConstant defaultConstant = new DefaultConstant();
    MessageConstant messageConstant = new MessageConstant();
    
    public void runTrainSystemCalculator() {
        Scanner scanner = new Scanner(System.in);
        Graph graph;
        System.out.println("Do you want to use default graph or input customized graph? Y/N");
        boolean usingDefault = scanner.nextLine().equals(messageConstant.YES);
        if (usingDefault) {
            graph = new Graph(defaultConstant.defaultGraph);
            TrainSystemCalculator trainSystemCalculator = new TrainSystemCalculator(graph);
            runTests(trainSystemCalculator, graph);
        } else {
            System.out.println("Please input your graph like 'AB5,BC4':");
            String input = scanner.nextLine();
            graph = new Graph(input);
            System.out.println(graph.toString());
            System.out.println("The graph you input is: \nFurther function to be implemented later......");
        }
    }

    private void runTests(TrainSystemCalculator trainSystemCalculator, Graph graph) {
        System.out.println("running tests......");
        printPathDistance("A-B-C", trainSystemCalculator, graph);
        printPathDistance("A-D", trainSystemCalculator, graph);
        printPathDistance("A-D-C", trainSystemCalculator, graph);
        printPathDistance("A-E-B-C-D", trainSystemCalculator, graph);
        printPathDistance("A-E-D", trainSystemCalculator, graph);
        printShortestDistance("A", "C", graph, trainSystemCalculator);
        printShortestDistance("B", "B", graph, trainSystemCalculator);
        printNumberOfPathConditionedOnStopNumber("C", "C", "Max", 3, graph, trainSystemCalculator);
        printNumberOfPathConditionedOnStopNumber("A", "C", "Equal", 4, graph, trainSystemCalculator);
        printNumberOfPathMaxDistance("C", "C", 30, graph, trainSystemCalculator);
    }

    private void printPathDistance(String stops, TrainSystemCalculator trainSystemCalculator, Graph graph) {
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(getStops(stops));
        String findResult = trainSystemCalculator.getExactPath(path);
        String printedMessage;
        if (findResult.equals(messageConstant.ROUTE_FOUND)) {
            printedMessage = "Route " + stops + " found, the distance is: " + trainSystemCalculator.getDistanceByPath(path);
        } else {
            printedMessage = findResult + ": " + stops;
        }
        System.out.println(printedMessage);
    }

    private void printShortestDistance(String start, String end, Graph graph, TrainSystemCalculator trainSystemCalculator) {
        LinkedList<Vertex> path = trainSystemCalculator.getShortestPath(graph.getVertexInVertices(start), graph.getVertexInVertices(end));
        int distance = graph.getDistanceByPathInGraph(path);
        String printedMessage = "The shortest path from " + start + " to " + end + " is " + path.toString() + " , distance: " + distance;
        System.out.println(printedMessage);
    }

    private void printNumberOfPathConditionedOnStopNumber(String start, String end, String Condition, int stopNumbers, Graph graph, TrainSystemCalculator trainSystemCalculator) {
        int numberOfPath = trainSystemCalculator.getNumberOfPathConditionedOnStops(graph.getVertexInVertices(start), graph.getVertexInVertices(end), Condition, stopNumbers);
        String printedMessage = "There are totally "+numberOfPath+ " paths with condition: "+Condition+" stop numbers: "+ stopNumbers;
        System.out.println(printedMessage);
    }

    private void printNumberOfPathMaxDistance(String start, String end, int maxDistance, Graph graph, TrainSystemCalculator trainSystemCalculator){
        int numberOfPath = trainSystemCalculator.getNumberOfPathWithMaxDistance(graph.getVertexInVertices(start), graph.getVertexInVertices(end), maxDistance);
        String printedMessage = "There are totally "+numberOfPath+" paths start from "+start+" to "+end+" with max distance "+maxDistance;
        System.out.println(printedMessage);
    }

    private ArrayList<String> getStops(String inputStops) {
        String[] stops = inputStops.split("-");
        return new ArrayList<>(Arrays.asList(stops));
    }
}
