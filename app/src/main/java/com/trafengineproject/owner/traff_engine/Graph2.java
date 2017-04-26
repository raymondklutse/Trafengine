package com.trafengineproject.owner.traff_engine;

import java.util.List;

/**
 * Created by Owner G on 26/04/2017.
 */

public class Graph2 {
    private final List<Node> nodes;
    private final List<Edge> edges;

    //constructor
    public Graph2(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }


    //method to get vertexes
    public List<Node> getVertexes() {
        return nodes;
    }

    //method to get edges
    public List<Edge> getEdges() {
        return edges;
    }

}
