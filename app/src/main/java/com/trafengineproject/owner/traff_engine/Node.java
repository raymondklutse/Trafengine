package com.trafengineproject.owner.traff_engine;

/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */


public class Node {
    final private String id;
    final private String name;
    final private char label;
    final private double v_latitude;
    final private double v_longitude;
    final private boolean wasvisited;



    //constructor
    public Node(String id, String name,char label,double v_latitude,double v_longitude){

        this.id = id;
        this.name = name;
        this.label = label;
        this.v_latitude = v_latitude;
        this.v_longitude = v_longitude;
        wasvisited = false;


    }
    //mehtod to get ID of vertex
    public String getId() {
        return id;
    }

    //mehtod to get Name of vertex
    public String getName() {
        return name;
    }
    //mehtod to get label of vertex
    public char getlabel() {
        return label;
    }


//mehtod to get latitude of vertex
public double getvertexlatitude(){
            return v_latitude;
      }
//mehtod to get longitude of vertex
      public double getvertexlongitude(){
           return v_longitude;
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

        Node other = (Node) obj;
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
        return name;
    }
}

