package com.trafengineproject.owner.traff_engine;

/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DepthFirst {

    public List<String> path = new ArrayList<String>();
    public List<Edge> edges = new ArrayList<Edge>();
    public List<Integer> totalcostlist = new ArrayList<Integer>();

    public Map<Integer, List> pathsMap = new HashMap<Integer, List>();

    private String End_Node;

    public void depthFirst(Graph graph, LinkedList<String> visited,String End_Node) {
        this.End_Node = End_Node;

        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());

        // examine adjacent nodes
        for (String node : nodes) {

            //Check if node has already been visited
            if (visited.contains(node)) {
                continue;
            }
            //Check for the End of the path and print
            if (node.equals(End_Node)) {
                visited.add(node);
                printPath(visited,graph);


                visited.removeLast();
                break;
            }



        }
        for (String node : nodes) {
            if (visited.contains(node) || node.equals(End_Node)) {
                continue;
            }
            visited.addLast(node);
            depthFirst(graph, visited, End_Node);
            visited.removeLast();
        }





    }

    private void printPath(LinkedList<String> visited,Graph graph1) {

        //Contains list of path elements
        path= new ArrayList<String>();



        for (String node : visited) {

            System.out.print(node);
            System.out.print("  ");

            path.add(node);
        }
        int t_cost = totalcost(graph1.edgeList,path);
        pathsMap.put(t_cost, path);



//        printpath(path);

    }

    //Method to find the total cost on a path
    public int  totalcost (List< Edge > edges,List<String> path){

        int totalcost= 0;
        int weight;
        for (int i=0;i<path.size()-1;i++)
        {
            for (int j = 0;j<edges.size();j++ )

            {
//            System.out.println( "\n "+ "The source node is : "+  edges.get(j).getSource() + "\n");
//            System.out.println( "\n "+ "The weight is node is : "+  edges.get(j).getWeight()+ "\n");
                if (edges.get(j).getSource().equals(path.get(i))&& edges.get(j).getDestination().equals(path.get(i+1)))
                {

                    weight = edges.get(j).getWeight();
                    totalcost+= weight;

                }

            }

        }
        totalcostlist.add(totalcost);

        return totalcost ;

    }
    public void printtotalcostlist(List<Integer> t_list){

        System.out.print( "\n" +"The total cost list is  " + t_list + "\n");

    }
    public void printpath(List<String> path){

        System.out.print( "\n" +"The path is  " + path + "\n");

    }
    public void printtotalcost(int totalcost){

        System.out.print( "\n" +"The total cost is  " + totalcost + "\n");

    }

    public List <Integer> gettotalcostlist(){

        return this.totalcostlist;
    }

    public List <String> getAllPaths(){

        return this.path;
    }

    public int findhighestcost(List<Integer> a){
        int max = 0;

        for(int i=0; i<a.size(); i++) {
            if(i==0) max= a.get(i);
            if(a.get(i) > max) {
                max = a.get(i);
            }
        }
        System.out.print( "\n" +"The highest cost is " + max + "\n");

        return max;
    }


    public List <String> getPathWithHighestTotalCost(){
        int maxvalue = findhighestcost(totalcostlist);

        System.out.print("\n" +"The path element with highest cost is " + pathsMap.get(maxvalue)+"\n");

        return pathsMap.get(maxvalue);
    }


}



















