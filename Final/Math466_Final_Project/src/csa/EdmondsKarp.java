package csa;

import java.util.*;
import java.io.*;

public class EdmondsKarp
{
        private int[][] flow, capacity, res_capacity;
        private int[] parent, prev, queue;
        private int[] min_capacity;
        private int size, source, sink, first, last;
        private int max_flow;
        private int delta;
        private int t;
        public EdmondsKarp(int[][] graph, int nV, int source, int sink)
        {
            this.capacity = graph;
            this.size = nV;
            this.source = source;
            this.sink = sink;
            maxFlow();
        }
        public int[][] getFlow(){
        	return flow;
        }
        private void maxFlow()  // Edmonds-Karp algorithm with O(V³E) complexity
        {
                flow = new int[size][size];
                res_capacity = new int[size][size];
                parent = new int[size];
                min_capacity = new int[size];
                prev = new int[size];
                queue = new int[size];
 
                for (int i = 0; i < size; i++)
                        for (int j = 0; j < size; j++){
                        	res_capacity[i][j] = capacity[i][j];
                        	max_flow = Math.max(capacity[i][j], max_flow);
                        }
                
                double power = Math.log((double)max_flow/Math.log(2));
        		delta = (int)Math.pow(2, power);
        				
        		while(delta >=1){
        		
	                if (BFS(source))
	                {
	                        max_flow -= min_capacity[sink];
	                        
	                        int v = sink, u;
	                        while (v != source)
	                        {
	                                u = parent[v];
	                                flow[u][v] += min_capacity[sink];
	                                flow[v][u] -= min_capacity[sink];
	                                res_capacity[u][v] -= min_capacity[sink];
	                                res_capacity[v][u] += min_capacity[sink];
	                                v = u;
	                        }
	                        printMatrix(res_capacity);
	                        System.out.println("delta: " + delta + " bottlenect: " + min_capacity[sink]);
	                }
	                delta /= 2;
        		}
        		System.out.println("Max flow: " + max_flow);
        }
 
        private boolean BFS(int source)  // Breadth First Search in O(V<sup>2</sup>)
        {
                for (int i = 0; i < size; i++)
                {
                        prev[i] = 0;
                        min_capacity[i] = Integer.MAX_VALUE;
                }
 
                first = last = 0;
                queue[last++] = source;
                prev[source] = -1;
 
                while (first != last)  // While "queue" not empty..
                {
                        int v = queue[first++];
                        for (int u = 0; u < size; u++)
                                if (prev[u] == 0 && res_capacity[v][u] > 0)
                                {
                                        min_capacity[u] = Math.min(min_capacity[v], res_capacity[v][u]);
                                        parent[u] = v;
                                        prev[u] = -1;
                                        if (u == sink) return true;
                                        queue[last++] = u;
                                }
                }
                return false;
        }
        public void printMatrix() {
        	int[][] M = capacity;
    	  	int i,j;
    	  	for (i = 0; i < M.length; i++) {
    	    		for (j = 0; j < M.length; j++)
    	      			System.out.print(M[i][j] + " ");
    	    		System.out.println();
    	  	}
    	  	
    	}
        public void printMatrix(int[][] x) {
        	int[][] M = x;
    	  	int i,j;
    	  	for (i = 0; i < M.length; i++) {
    	    		for (j = 0; j < M.length; j++)
    	      			System.out.print(M[i][j] + " ");
    	    		System.out.println();
    	  	}
    	  	
    	}
 
}