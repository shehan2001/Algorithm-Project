import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

import java.awt.Dimension;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
//Name-  Shehan Anthony Saverimuttu
//IIT ID-20191185
//UOw ID w1790348
public class Graph {

    static String file = "bridge_1";
    static boolean ifWeighted;
    private static String Variable;
    int vertex;
    public static int vertices;
    boolean ifDirected;
   static int[][] matrixGraph;
    private boolean[][] isSetMatrix;
   private static Integer startNode;
    private static Integer destinationNode;
    private static Integer weight;
     private static Graph obj_graph;


    static {
        try {
            obj_graph = new Graph( true, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Graph(boolean ifDirected, boolean ifWeighted) throws IOException {
        BufferedReader buff_Read = new BufferedReader(new FileReader(file + ".txt"));
        Variable = buff_Read.readLine();
        Variable=Variable.trim();
        vertices = Integer.parseInt(Variable);
        this.vertex = vertices;
        this.ifWeighted = ifWeighted;
        this.ifDirected = ifDirected;
        matrixGraph = new int[vertex][vertex];
        isSetMatrix = new boolean[vertex][vertex];


    }


    public void addEdge(int start,int destination,int weight){

        int valueToAdd= weight;
        if (!ifWeighted) {
            valueToAdd = 0;
        }

        matrixGraph[start][destination] = valueToAdd;
        isSetMatrix[start][destination] = true;

        if (!ifDirected) {
            matrixGraph[destination][start] = valueToAdd;
            isSetMatrix[destination][start] = true;
        }
    }

    public void printGraph(){
        System.out.println("Adjacency Matrix : ");
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j <vertex ; j++) {
                if (isSetMatrix[i][j])
                    System.out.format("%8s", String.valueOf(matrixGraph[i][j]));
                else System.out.format("%8s", "   0");
            }
            System.out.println();
        }
    }

    public void printEdges() {
        for (int i = 0; i < vertex; i++) {
            System.out.print("Node " + i + " is connected to: ");
            for (int j = 0; j < vertex; j++) {
                if (isSetMatrix[i][j]) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }
    }
    public boolean hasEdge(int source, int destination) {
        return isSetMatrix[source][destination];

    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void insertEdges(){
        Scanner s = new Scanner(System.in);
        int I_start;
        int I_destination;



        System.out.println("Enter 'Start' node");
        I_start=s.nextInt();
        System.out.println("Enter 'End' node");
        I_destination=s.nextInt();
        System.out.println("Enter weight of node");
        weight=s.nextInt();
        if(!obj_graph.hasEdge(I_start,I_destination)){
            System.out.println("Test 1");
            obj_graph.addEdge(I_start, I_destination, weight);
        }
        obj_graph.printGraph();
        obj_graph.printEdges();


    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        public static void Build() throws IOException {
            Scanner s = new Scanner(System.in);

            BufferedReader buff_Read = new BufferedReader(new FileReader(file + ".txt"));
            Variable = buff_Read.readLine();
            Variable=Variable.trim();
            vertices = Integer.parseInt(Variable);




            while ((Variable=buff_Read.readLine()) != null) {
                String Temp_Array[] = Variable.split(" ");
                startNode = Integer.valueOf(Temp_Array[0]);
                destinationNode = Integer.valueOf(Temp_Array[1]);
                weight = Integer.valueOf(Temp_Array[2]);
                obj_graph.addEdge(startNode, destinationNode, weight);
            }




            buff_Read.close();





        }

        public static void  display(){
            obj_graph.printGraph();
            obj_graph.printEdges();
        }

        //--------------------------------------------------------------------------------
            public static void search() throws IOException {


                Scanner s = new Scanner(System.in);

                    System.out.println("Type in the first nodes or vertex's number");
                    int node_1 = s.nextInt();
                    System.out.println("Type in the second nodes or vertex's number");
                    int node_2 = s.nextInt();


                if (obj_graph.hasEdge(node_1, node_2)) {
                        System.out.println("yes nodes " + node_1 + " node " + node_2 + " has a connection hence, it's " + ifWeighted);

                    }
                else{
                    System.out.println("yes nodes " + node_1 + " node " + node_2 + " has a no connection hence, it's false"  );
                }

            }
    //--------------------------------------------------------------------------------


        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


        public static void Visualizer() throws IOException {
            DirectedSparseGraph<String, String> g = new DirectedSparseGraph<String, String>();

            BufferedReader buff_Read = new BufferedReader(new FileReader(file + ".txt"));
            String Variable = buff_Read.readLine();
            Variable=Variable.trim();
            vertices = Integer.parseInt(Variable);


            int start = 0;
            int destination = 0;
            int weight = 0;
            int count=1;





            while ((Variable=buff_Read.readLine()) != null){
                String Temp_Array[] = Variable.split(" ");
                start = Integer.valueOf(Temp_Array[0]);
                destination = Integer.valueOf(Temp_Array[1]);
                weight = Integer.valueOf(Temp_Array[2]);

                 count++;

                g.addEdge(" Edge no: " + count +","+ " Weight = "+  String.valueOf(weight) , String.valueOf(start), String.valueOf(destination));




            }
            buff_Read.close();




            VisualizationViewer<String, String> vv =
                    new VisualizationViewer<String, String>(
                            new CircleLayout<String, String>(g), new Dimension(400,400));
            Transformer<String, String> transformer = new Transformer<String, String>() {
                @Override public String transform(String arg0) { return arg0; }
            };
            vv.getRenderContext().setVertexLabelTransformer(transformer);
            transformer = new Transformer<String, String>() {
                @Override public String transform(String arg0) { return arg0; }
            };
            vv.getRenderContext().setEdgeLabelTransformer(transformer);

            final DefaultModalGraphMouse<String,Number> graphMouse = new DefaultModalGraphMouse<String,Number>();
            vv.setGraphMouse(graphMouse);
            graphMouse.setMode(ModalGraphMouse.Mode.PICKING);

            JFrame frame = new JFrame();
            frame.getContentPane().add(vv);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }



    //-------------------------------------------------------------------------------------------------------
        // Using BFS as a searching algorithm
        boolean bfs(int Graph[][], int s, int t, int p[]) throws IOException {

            boolean visited[] = new boolean[vertices];
            for (int i = 0; i < vertices; ++i)
                visited[i] = false;

            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.add(s);
            visited[s] = true;
            p[s] = -1;

            while (queue.size() != 0) {
                int u = queue.poll();

                for (int v = 0; v < vertices; v++) {
                    if (visited[v] == false && Graph[u][v] > 0) {
                        queue.add(v);
                        p[v] = u;
                        visited[v] = true;

                    }
                }
            }

            return (visited[t] == true);
        }

    // Applying fordfulkerson algorithm
    int fordFulkerson(int graph[][], int src, int snk) throws IOException {
        ArrayList<Integer> Reverse = new ArrayList<Integer>();

        int u, v;
        int Graph[][] = new int[vertices][vertices];

        for (u = 0; u < vertices; u++)
            for (v = 0; v < vertices; v++)
                Graph[u][v] = graph[u][v];

        int par[] = new int[vertices];

        int max_flow = 0;
        int Count_1=0;
        int Count_2=0;


        //  # Updating the residual calues of edges
        while (bfs(Graph, src, snk, par)) {
            int path_flow = Integer.MAX_VALUE;

            for (v = snk; v != src; v = par[v]) {
                u = par[v];
                path_flow = Math.min(path_flow, Graph[u][v]);


                Reverse.add(path_flow);



            }







            for (v = snk; v != src; v = par[v]) {
                u = par[v];
                Graph[u][v] -= path_flow;
                Graph[v][u] += path_flow;
                Count_2++;



                System.out.println("Flow " + Count_2 +  ".\n "+ "Node " + v + " from Node  " + u +" is one edge" + "\n");
            }

            // Adding the path flows
            max_flow += path_flow;

        }

        for(int i=Reverse.size()-1; i >=0; i-- ){
            Count_1++;
            int path_flow_2=Reverse.get(i);
            System.out.println("Flow count "+ Count_1 +" with minimum flow = " + path_flow_2 + "\n");


        }

        return max_flow;


    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//---------------------------------------------------------------------------------------------------------------------------


    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);


        System.out.println("*****************************************************************************************");
        System.out.println("* Welcome! ");
        System.out.println("*****************************************************************************************");
        Boolean valid= true;
        while (valid != false) {
            System.out.println("*****************************************************************************************");
            System.out.println("* Next Request? ");
            System.out.println("*****************************************************************************************");


            System.out.println("1. Enter 1 to represent a flow network ");
            System.out.println("2. Enter 2 Max Flow ");
            System.out.println("3. Enter 3 Search");
            System.out.println("4. Enter 4 to add edges and weights ");
            System.out.println("5. Enter 5 Visualize graph");
            System.out.println("6. Enter 6 to leave ");
            Build();
            String Key = "";
            Key = s.next();
            if (Key.equals("1")) {
                display();
            } else if (Key.equals("2")) {

                long start = System.currentTimeMillis();
                System.out.println("Max Flow: " + obj_graph.fordFulkerson(matrixGraph, 0, vertices - 1));
                long now = System.currentTimeMillis();
                double elapsed = (now - start) / 1000.0;
                System.out.println();
                System.out.println("Elapsed time = " + elapsed + " seconds");

            } else if (Key.equals("3")) {
                search();
            } else if (Key.equals("4")) {

                insertEdges();

            } else if (Key.equals("5")) {
                Visualizer();


            }
            else if(Key.equals("6")){
                valid=false;
            }
        }


    }
}
