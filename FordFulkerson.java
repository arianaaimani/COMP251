import java.io.*;
import java.util.*;




public class FordFulkerson {

    static boolean isPathA(ArrayList<Integer> Stack, Integer source, WGraph graph)
    {
        boolean isPathA = true;
        if (Stack.contains(graph.getSource()) && Stack.size() == 1)
        {
            isPathA = false;
        }
        return isPathA;
    }
    
    public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph)
    {
        ArrayList<Integer> Stack = new ArrayList<Integer>();
        ArrayList<Integer> Q = new ArrayList<Integer>();
        Q.add(source);
        Stack.add(source);
        while (Q.size() > 0)
        {
            Integer v = Q.remove(0);
            if (v == destination)
            {
                break;
            }
            Integer u;
            for (Edge e : graph.listOfEdgesSorted())
            {
                if (v == e.nodes[0])
                {
                    u = e.nodes[1];
                    if (!Stack.contains(u) && e.weight != 0)
                    {
                        Q.add(u);
                        Stack.add(u);
                        break;
                    }
                }
            }
        }
        return Stack;
    }


    public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath)
    {
        String answer = "";
        String mcgill_id = "260501657"; //le mcgill id
        int max_flow = 0;

        // STEP 1: GET AUGMENTING PATH
        WGraph residualGraph = new WGraph(graph);

        // STEP 2: SET ALL WEIGHTS TO 0
        for (Edge e : graph.getEdges())
        {
            e.weight = 0;
        }

        ArrayList<Integer> pathsDepth = pathDFS(source, destination, residualGraph);

        while (isPathA(pathsDepth, residualGraph.getSource(), residualGraph))
        {
            if (pathsDepth.contains(residualGraph.getDestination()))
            {
                int mini = residualGraph.getEdge(pathsDepth.get(0), pathsDepth.get(1)).weight;
                for (int i = 0; i < pathsDepth.size() - 1; i++)
                {
                    if (residualGraph.getEdge(pathsDepth.get(i), pathsDepth.get(i + 1)).weight < mini && residualGraph.getEdge(pathsDepth.get(i), pathsDepth.get(i + 1)).weight != 0)
                    {
                        mini = residualGraph.getEdge(pathsDepth.get(i), pathsDepth.get(i + 1)).weight;
                    }
                }
                max_flow += mini;
                for (int i = 0; i < pathsDepth.size() - 1; i++)
                {
                    graph.getEdge(pathsDepth.get(i), pathsDepth.get(i + 1)).weight += mini;
                    residualGraph.getEdge(pathsDepth.get(i), pathsDepth.get(i + 1)).weight -= mini;
                }
            }

            if (!pathsDepth.contains(residualGraph.getDestination()) && isPathA(pathsDepth, residualGraph.getSource(), residualGraph))
            {

                //change all the nodes of the incomplete path to zero
                for (int i = 0; i < pathsDepth.size() - 1; i++)
                {
                    residualGraph.getEdge(pathsDepth.get(i), pathsDepth.get(i + 1)).weight = 0;
                }

            }

            pathsDepth = pathDFS(source, destination, residualGraph);
        }

        System.out.println("Resulting Graph\n");

        answer += max_flow + "\n" + graph.toString();
        writeAnswer(filePath + mcgill_id + ".txt", answer);
        System.out.println(answer);
    }
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}