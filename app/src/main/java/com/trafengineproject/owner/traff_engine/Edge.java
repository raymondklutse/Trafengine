package com.trafengineproject.owner.traff_engine;

/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;


public class Edge {

    private Map<String, LinkedHashSet<Node>> map = new HashMap();

    private final String id;
    private final String source;
    private final String destination;
    private final int weight;

    //constructor
    public Edge(String id, String source, String destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;

        LinkedHashSet<Node> adjacent = map.get(source);
        if(adjacent==null) {
            adjacent = new LinkedHashSet();
            map.put(id, adjacent);
        }

        //adjacent.add(destination);

    }
    //method to get ID of edge
    public String getId() {
        return id;
    }
    //method to get destination vertex
    public String getDestination() {
        return destination;
    }
    //method to get source vertex
    public String getSource() {
        return source;
    }

    //method to get weight of edge
    public int getWeight() {
        return weight;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        {
            return true;
        }

        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }

        Edge other = (Edge) obj;
        if (id == null) {
            if (other.id != null)
            {
                return false;
            }
        }
        else if (!id.equals(other.id))
        {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return id ;
    }

}

