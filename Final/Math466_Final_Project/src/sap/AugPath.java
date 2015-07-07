package sap;

import java.util.Arrays;
import java.util.LinkedList;

public class AugPath{
	int flow = 0; //no flow yet
	public AugPath(int cap[][], int fnet[][], int s, int t) {
       int n = cap.length;
        while (true) {
            //Find an augmenting path using BFS
            int[] prev = new int[n];
            Arrays.fill(prev, -1);
            LinkedList<Integer> queue = new LinkedList<Integer>();
            prev[s] = -2;
            queue.add(s);
            while (!queue.isEmpty() && prev[t] == -1) {
                int u = queue.poll();
                for (int v = 0; v < n; v++) {
                    if (prev[v] == -1) { //not seen yet
                        if (fnet[v][u] > 0 || fnet[u][v] < cap[u][v]) {
                            prev[v] = u;
                            queue.add(v);
                        }
                    }
                }
            }
            if (prev[t] == -1) break;

            int bot = Integer.MAX_VALUE;
            for (int v = t, u = prev[v]; u >= 0; v = u, u = prev[v]) {
                if (fnet[v][u] > 0)  
                    bot = Math.min(bot, fnet[v][u]);
                else 
                    bot = Math.min(bot, cap[u][v] - fnet[u][v]);
            }
            System.out.println("bot: " + bot);

            //update the flow network
            for (int v = t, u = prev[v]; u >= 0; v = u, u = prev[v]) {
                if (fnet[v][u] > 0) //backward edge -> subtract
                    fnet[v][u] -= bot;
                else //forward edge -> add
                    fnet[u][v] += bot;
            }
            flow += bot;
            System.out.println("flow: " + flow);
            
            printMatrix(fnet);
            
        }
        
       
    }
	public void printMatrix(int[][] M) {
	  	int i,j;
	  	for (i = 0; i < M.length; i++) {
	    		for (j = 0; j < M.length; j++)
	      			System.out.print(M[i][j] + " ");
	    		System.out.println();
	  	}
	  	
	}
	public int getFlow(){
		return flow;
	}
}