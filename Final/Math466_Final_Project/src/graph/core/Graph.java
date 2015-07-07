/*
 * Graph.java
 *
 * Created on 01 November 2005, 17:13
 *
 */
package graph.core;
import java.util.*;

public class Graph {
    public ArrayList<Node> nodes;
    public ArrayList<Edge> edges;
    public int id = 0;
    int error = 0;
    public Graph(int nodesCount, int edgesCount, int maxDegree)
        throws Exception {
       
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();

        for (int i = 0; i < nodesCount; i++) {
            nodes.add(new Node(i + 1));
        }

        //for all edges add some random nodes together
        //i = index for edges j = index for nodes
        for (int i = 0, j = 0; i < edgesCount; i++) {

            Node a = nodes.get(j++), b = getNodeFor(a);

            //if were at the end of the nodes go back to the start
            if (j == nodes.size()) {
                j = 0;

                //if the last node is full then we got a problem
                if (b == null || a.getDegree() >= maxDegree || b.getDegree() >= maxDegree) {
                    throw new Exception("Cannot create any more edges, all nodes full!");
                }
            }

            //current node is connected to all other nodes try next one
            if (b == null || a.getDegree() >= maxDegree || b.getDegree() >= maxDegree) {
                i--; //have to dec otherwise get unintialised edges
                continue;
            }

            edges.add( new Edge(a,b));
        }
    }

    public Graph() {

    }
    public Node getNodeFor(Node a) {
        int j = (int) (Math.random() * (nodes.size() -1));

        for (int i = 0; i < nodes.size(); i++ ) {
            if (!a.sharesEdgeWidth(nodes.get(j)) && a != nodes.get(j) )
                return nodes.get(j);

            j++;
            if (j == nodes.size())
                j = 0;

        }
        return null;
    }
    public void addNode(Node n ) {
        if (n == null) {
            return;
        }

        nodes.add(n);
    }
    public void addEdge(Edge e ) {
        if (e == null) {
            return;
        }

        edges.add(e);
    }
    public int[][] toArray(){
    	int x[][] = new int[this.nodes.size()][this.nodes.size()];
    	
    	for(int i = 0; i < this.nodes.size();i++){
    		for(int j = 0; j < this.nodes.size(); j++){
    			x[i][j] = 0;
    		}
    	}
    	
    	for(int i = 0; i < this.edges.size(); i++){
    		int xx = this.edges.listIterator(i).next().getA().getID();
    		int yy = this.edges.listIterator(i).next().getB().getID();
    		x[xx-1][yy-1] = (int) Math.ceil(this.edges.listIterator(i).next().getLength());    		
    	}
    	for(int i = 0; i < this.nodes.size();i++){
    		for(int j = 0; j < this.nodes.size(); j++){
    			System.out.print(x[i][j] + " ");
    		}
    		System.out.println();
    	}
		return x;
    	
    }
   
}