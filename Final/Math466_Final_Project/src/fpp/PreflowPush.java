package fpp;

public class PreflowPush {
	int NODES;
	int INFINITE = 9999;
    int flows;
	  public PreflowPush(int graph[][], int[][] flow, int source, int destination){
		  NODES = graph.length;
	  	  flows = pushRelabel(graph, flow, source, destination);
		}
	  public int getFlow(){
		  return flows;
	  }
	  
	  public void printFlow(int graph[][], int[][] flow, int source, int destination){
		  System.out.println("flow");
		  printMatrix(flow);
	  }
	  public int pushRelabel(int[][] capacities, int[][] flow, int source, int sink) {
		  	int []excess = new int[NODES];
		  	int []height = new int[NODES];
		  	int []list = new int[NODES-2];
		  	int []seen = new int[NODES];
		  	int i, p;
		  	for (i = 0, p = 0; i < NODES; i++){
	    		if((i != source) && (i != sink)) {
	      			list[p] = i;
	      			p++;
	    		}
		  	}
		  	height[source] = NODES;
		  	excess[source] = INFINITE;
		  	for (i = 0; i < NODES; i++)
		    		push(capacities, flow, excess, source, i);
		 
		  	p = 0;
		  	while (p < NODES - 2) {
		    		int u = list[p];
		    		int old_height = height[u];
		    		discharge(capacities, flow, excess, height, seen, u);
		    		if (height[u] > old_height) {
		      			moveToFront(p,list);
		      			p=0;
		    		}
		    		else
		      			p += 1;
		  	}
		  	int maxflow = 0;
		  	for (i = 0; i < NODES; i++)
		    		maxflow += flow[source][i];
		  	
		  	return maxflow;
	}

	
	  private void discharge(int[][] capacities, int[][] flow, int[] excess,
			int[] height, int[] seen, int u) {
		  while (excess[u] > 0) {
	    		if (seen[u] < NODES) {
	      			int v = seen[u];
	      			if ((capacities[u][v] - flow[u][v] > 0) && (height[u] > height[v])){
	    				push(capacities, flow, excess, u, v);
	      			}
	      			else
	    				seen[u] += 1;
	    		} else {
	      			relabel(capacities, flow, height, u);
	      			seen[u] = 0;
	    		}
	  	}		
	}

	private void relabel(int[][] capacities, int[][] flow, int[] height, int u) {
		int v;
	  	int min_height = INFINITE;
	  	for (v = 0; v < NODES; v++) {
	    		if (capacities[u][v] - flow[u][v] > 0) {
	      			min_height = Math.min(min_height, height[v]);
	      			height[u] = min_height + 1;
	    		}
	  	}		
	}

	private void push(int[][] capacities, int[][] flow, int[] excess,
			int u, int v) {
		int send = Math.min(excess[u], capacities[u][v] - flow[u][v]);
	  	flow[u][v] += send;
	  	flow[v][u] -= send;
	  	excess[u] -= send;
	  	excess[v] += send;		
	}

	private void moveToFront(int p, int[] list) {
		int temp = list[p];
	  	int n;
	  	for (n = p; n > 0; n--){
	    		list[n] = list[n-1];
	  	}
	  	list[0] = temp;		
	}

	
	public void printMatrix(int[][] M) {
	  	int i,j;
	  	for (i = 0; i < NODES; i++) {
	    		for (j = 0; j < NODES; j++)
	      			System.out.print(M[i][j] + " ");
	    		System.out.println();
	  	}
	  	
	}
}
