import java.util.*;

public class BellmanFord {

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;


    BellmanFord(WGraph g, int source) throws Exception {

        this.source = source;
        int nbNodes = g.getNbNodes(); //just making it easier so we dont have to type g.getNbNodes ALL THE TIME.
        distances = new int[nbNodes];   //obviously.
        predecessors = new int[nbNodes]; //well duh .

        //initialize distance to source as 0 and distance to all other nodes to infinity
        for (int i = 0; i < nbNodes; i++) {
            distances[i] = Integer.MAX_VALUE;
            predecessors[i] = -1; //initialize all entries of predecessors to -1 since -1 is not a node in the graph
        }
        distances[source] = 0;


        //relax edges in graph
        for (Edge e : g.getEdges()) {
            int u = e.nodes[0];
            int v = e.nodes[1];
            int w = e.weight;

            if (distances[v] > distances[u] + w && distances[u] != Integer.MAX_VALUE) {
                distances[v] = distances[u] + w;
                predecessors[v] = u;
            }
        }

        //check for negative weighted cycles - throw exception if there is one
        for (Edge e : g.getEdges()) {
            int u = e.nodes[0];
            int v = e.nodes[1];
            int w = e.weight;

            if ((distances[v] > distances[u] + w) && (distances[v] != Integer.MAX_VALUE)) {
                throw new IllegalArgumentException("The graph has negative weighted cycles.");
            }
        }
    }

    public int[] shortestPath(int destination) throws Exception {

        int curr = destination; //curr will keep track of where we are xactly in the path.
        int count = 1; //count is goint to store  shortest path. initialize to 1  (just destination vertex)

        //to count number of nodes in the path. moving backwards in teh array to.
        while (curr != source) {
            if (curr != -1) {
                curr = predecessors[curr];
                count++; //augment count by 1 to indicate we added one more node to path
            } else { //occurs when curr = -1
                throw new IllegalArgumentException("No path was found.");
            }
        }

        //when while loop terminates without exception then we have found a path
        int[] shortestPath = new int[count];
        curr = destination; //work backwards through nodes of path starting at destination and fill in path
        for (int j = count - 1; j >= 0; j--) {
            shortestPath[j] = curr;
            curr = predecessors[curr];
        }
        return shortestPath;
    }

    public void printPath(int destination) {
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++) {
                int next = path[i];
                if (next == destination) {
                    System.out.println(destination);
                } else {
                    System.out.print(next + "-->");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        String file = args[0];
        WGraph g = new WGraph(file);
        try {
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
