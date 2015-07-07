package maxFlow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.sun.jmx.snmp.Timestamp;

import csa.CapacityScalingAlg;
import csa.EdmondsKarp;
import fpp.PreflowPush;
import graph.core.Graph;
import sap.*;
public class MaxFlow
{

    
	public static void main(String[] args) throws Exception
    {
        ArrayList<Graph> graphs = new ArrayList<Graph>();
        int numberOfNodes = 0;
        int numberOfEdges = 0;
        int source = 0;
        int sink = 0;
        boolean exit = false; 

        	//get new number of nodes
        	do{
        		numberOfNodes = (int) (Math.random() * 100);
        	}while(numberOfNodes < 5 || numberOfNodes > 15);
        	//get new number of edges
        	do{
        		numberOfEdges = (int)(Math.random() * 100);
        	}while(numberOfEdges < 25 || numberOfEdges > 35);
        	do{
        		source = (int)(Math.random() * 100);
        	}while(source > numberOfNodes-2);
        	do{
        		sink = (int)(Math.random() * 100);
        	}while(sink > numberOfNodes-2);
        	
        	
        	//new graph
	        int[][] graph = null;
	        System.out.println("Graph: ");
	        Graph g = new Graph(numberOfNodes, numberOfEdges, 6);
	        
	        graph = g.toArray();        
	        System.out.println();
	        System.out.println("Number of Nodes:\t" + numberOfNodes);
	        System.out.println("Number of Edges:\t" +numberOfEdges);
	
	        int[][] holder = new int[numberOfNodes+1][numberOfNodes+1];
	        int[][] holder2 = new int[numberOfNodes][numberOfNodes];
	        int[][] holder3 = new int[numberOfNodes][numberOfNodes];
	        
	        //for time
	        long START,end,microseconds;
	        
	     
	        /****************************************************************************************************/
	        
	        
	        System.out.println("*************************************************************************************************");
	        START = System.nanoTime();
	        PreflowPush z = new PreflowPush(graph,holder,source,sink);
	        end = System.nanoTime();
	        microseconds = (end - START) / 1000;
	        System.out.println("After PreflowPush: \n");
	        z.printMatrix(holder);
	        System.out.println("start: " + START + " end: " +end+ " microseconds: " +microseconds);
	        System.out.println("Flow: " + z.getFlow());
	        System.out.println("expected run time: " + numberOfEdges*numberOfEdges*numberOfEdges );
	        System.out.println();
	        System.out.println("*************************************************************************************************");
	        
	        
	        /****************************************************************************************************/
	        System.out.println("\nAugmenting Path\n");
	        START = System.nanoTime();
	        AugPath f = new AugPath(graph,holder2,source,sink);
	        System.out.println("\nAfter AugPath:\n\n");
	        end = System.nanoTime();
	        microseconds = (end - START) / 1000;
	        z.printMatrix(holder2);
	        System.out.println("start: " + START + " end: " +end+ " microseconds: " +microseconds);
	        System.out.println("Flow: " + f.getFlow());
	        System.out.println("expected run time: " + numberOfEdges*numberOfEdges*numberOfNodes );
	        System.out.println();
	        System.out.println("*************************************************************************************************");
	        
	        
	        /****************************************************************************************************/
	     //   System.out.println("Edmond Karp");
	     //   EdmondsKarp EK = new EdmondsKarp(graph,7,source,sink);
	     //   EK.printMatrix();
	        
        
//        CapacityScalingAlg t = new CapacityScalingAlg(graph,holder3,7,10,source, sink);
//        System.out.println("Afer Capacity Scaling");
//        t.printMatrix(holder3);
    }
}