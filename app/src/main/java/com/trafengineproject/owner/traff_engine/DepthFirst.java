package com.trafengineproject.owner.traff_engine;

/**
 * Created by Raymond Klutse Ewoenam on 21/04/2017.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DepthFirst {

    public List<String> addtopath = new ArrayList<String>();
    public List<String> path = new ArrayList<String>();
    public List<Integer> totalcostlist = new ArrayList<Integer>();


    private String End_Node;


    public void depthFirst(Graph graph, LinkedList<String> visited, String End_Node) {
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

    private void printPath(LinkedList<String> visited, Graph graph) {
        int t_cost = totalcost(graph.edgeList);
//        String list = addtopath;
//
//        int maxvalue = findhighestcost(totalcostlist);

        //Contains list of path elemetns
        addtopath= new ArrayList<String>();



        for (String node : visited) {

            System.out.print(node);
            System.out.print("  ");
            addtopath.add(node);

        }

        path.add(End_Node);


        System.out.print( "\n" +"Path is  " + addtopath+ "\n");

        System.out.print( "\n" +"......................................"+ "\n");

        System.out.print( "\n" +"The total cost is " + t_cost+ "\n");


        System.out.print( "\n" +"______________________________________________"+ "\n");

        System.out.println();
    }

    public int  totalcost (List< Edge > edges){
//        totalcostlist = new ArrayList<Integer>();
        int totalcost= 0;
        int weight;
        for (int i = 0;i<addtopath.size()-1;i++)
        {
            for (int j = 0;j<edges.size() ;j++ )

            {

                if (edges.get(j).getSource().equals(addtopath.get(i))&& edges.get(j).getDestination().equals(addtopath.get(i+1)))
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
        System.out.print( "\n" +"The highest value is " + max + "\n");

        return max;
    }
    public List <String> getPathWithHighestTotalCost(){
        int highestcost = findhighestcost(totalcostlist);

        return this.path;
    }


}



















