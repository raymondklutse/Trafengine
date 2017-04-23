package com.trafengineproject.owner.traff_engine;

/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;




public class Graph {

    public List<Edge> edgeList = new ArrayList<Edge>();
    public List<Node> nodeList = new ArrayList<Node>();

    private Map<String, LinkedHashSet<String>> map = new HashMap();
    private String nodeid;
    private String nodename;
    private char nodelabel;


    //Method to add an edge to the graph
    public void addEdge(String edgeId,String node1, String node2,int weight) {


        LinkedHashSet<String> adjacent = map.get(node1);
        if(adjacent==null) {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
        edgeList.add(new Edge (edgeId,node1,node2,weight));
        //edgeList.add(new Edge(edgeId , new Node(nodeid, nodename, nodelabel), new Node(nodeid, nodename, nodelabel), weight));
    }
    //Method call to add a two way edge
    public void addTwoWayVertex(String edgeId,String node1, String node2, int weight) {

        addEdge(edgeId, node1, node2, weight);
        addEdge(edgeId, node2, node1, weight);
    }

    public boolean isConnected(String node1, String node2) {

        Set adjacent = map.get(node1);
        if(adjacent==null) {
            return false;
        }
        return adjacent.contains(node2);
    }

    public LinkedList<String> adjacentNodes(String last) {

        LinkedHashSet<String> adjacent = map.get(last);
        if(adjacent==null) {
            return new LinkedList();
        }
        return new LinkedList<String>(adjacent);
    }

    // display the ArrayList's elements on the console
    public void displayedges( List< Edge > edges, String header ){
        System.out.print( "     " + header + "     " + "\n"); // display header

        // display each element in items
        for( Edge edge : edges )

            System.out.println(edge.getId() + "  " + edge.getSource() + "  " + edge.getDestination() + "  " + edge.getWeight() + "\n");
        System.out.println();


    }

}

