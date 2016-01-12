
import java.util.*;

/* COMP 251 Assignment 2, Question 2
  Ariana Aimani 260501657

*/



public class Kruskal{


/*kruskal method takes a graph as input and returns its minimum spanning tree (MST)*/

    public static WGraph kruskal(WGraph g){
    
    
     WGraph mst=new WGraph();
     int numNodes=g.getNbNodes();
     
     DisjointSets dsjSets= new DisjointSets(numNodes);  // creating a new disjoint set with the number of nodes
    
        ArrayList<Edge> edges = g.listOfEdgesSorted(); //making an array list of edges with the sorted edges.
        for(int i = 0; i<edges.size(); i++){       //looping through
          if (IsSafe(dsjSets, edges.get(i))) {     //doing the IsSafe
          mst.addEdge(edges.get(i));               // adding edges to the MST
          dsjSets.union(edges.get(i).nodes[0], edges.get(i).nodes[1]); //doing the union.
        }
        }
       
        return mst;
    }

    
    public static Boolean IsSafe(DisjointSets p, Edge e){
    
     int nodeI = e.nodes[0]; //edge e connects nodes I and J
     int nodeJ = e.nodes[1];
     if(p.find(nodeI)!=p.find(nodeJ)){
      return true; //safe to add edge btwn IJ
     }
     return false; 

    
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
