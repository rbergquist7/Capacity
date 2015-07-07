package csa;

import java.util.Arrays;
import java.util.LinkedList;

public class CapacityScalingAlg {

	//D = Uij
	int maxC = 0;
	int bot=0;
	
	int min_flow = 0;
	int source, sink, max_flow, delta, nV, nE, max_capacity, nP=0;
	 int[] prev;
	 LinkedList<Integer> queue = new LinkedList<Integer>();
	// int cap[][], int fnet[][], int n, int s, int t) {
	public CapacityScalingAlg(int graph[][], int[][] flow, int nV, int nE, int source, int destination){
		prev = new int[nV];
		this.nE = nE;
		this.nV = nV;
		
		int b,i;
		getMaxFlow(graph);
//		for(int j = 0; j < graph.length; j++){
//			for(int k = 0; k < flow.length; k++){
//				flow[j][k] = graph[j][k];
//			}
//		}
		double power = Math.log((double)max_flow/Math.log(2));
		delta = (int)Math.pow(2, power);
				
		while(delta >=1){
			//bfs
			
			System.out.println("delta: " + delta + " max flow" + max_flow);		
           while(bfs(graph,flow,nV,source,destination)){
   	  
        	   //max_flow = max_flow + bot;	/* add bottleneck to max_flow value */ 
        	  // add_to_pathlist(bot);	
        	   AugPath(graph,flow,nV,source,destination);
           }
            
            
			delta/=2;
		}
		
		//calculate_min_cut(graph);
	}


	private void getMaxFlow(int[][] graph) {
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph.length;j++){
				max_flow = Math.max(max_flow, graph[i][j]);
			}
		}
		
	}


	public void AugPath(int cap[][], int fnet[][], int n, int s, int t) {
	       
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

            int bot = max_flow;
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
            max_flow += bot;
            System.out.println("flow: " + max_flow);
            
            printMatrix(fnet);
            
        }
        
       
	}


	private boolean bfs(int[][] graph, int[][] flow, int n, int source, int destination) {
         Arrays.fill(prev, -1);
         prev[source] = -2;
         queue.add(source);
         while(true){
        	 
        	 while (!queue.isEmpty() && prev[destination] == -1) {
        		 int u = queue.poll();
        		 for (int v = 0; v < n; v++) {
        			 if (prev[v] == -1) { //not seen yet
        				 if (graph[v][u] > 0) {
        					 prev[v] = u;
        					 queue.add(v);
        				 }
        				 System.out.print(graph[v][u] + " ");
        			 }
        		 }
        		 System.out.println();
        	 }
        	 if (prev[destination] == -1) {
        		 
        		 break;
        	 }
        	 return false;
         }
        // System.out.println("true");
         return true;
	}
	public void printMatrix(int[][] M) {
	  	int i,j;
	  	for (i = 0; i < this.nV; i++) {
	    		for (j = 0; j < this.nV; j++)
	      			System.out.print(M[i][j] + " ");
	    		System.out.println();
	  	}
	  	
	}
	

}

//	int augment_path(int flow)
//	{	
//		node *path_temp, *graph_node, *new_node, *temp;
//		int edge_start, edge_end, found=0;
//
//		path_temp = path+(sink-1);
//		edge_end = sink;
//		edge_start = path_temp->label;
//
//		while(1)
//		{
//			
//			/* if flow > 0, add a backward edge equivalent to flow value */
//			found = 0;
//
//			if(flow > 0)
//			{
//				graph_node = adj_list+edge_end-1;
//				
//				new_node = (node *) malloc(sizeof(node));
//				new_node->label = edge_start;
//				new_node->capacity = flow;
//				new_node->edge_type = 0;	/* 0 signifies backward edge */
//
//				graph_node = graph_node->next;
//
//				while(graph_node != NULL)	{
//					if(graph_node->label == new_node->label)	{
//						graph_node->capacity = graph_node->capacity+new_node->capacity;
//						found = 1;
//					}
//					graph_node = graph_node->next;
//				}
//				if(!found)	{
//					graph_node = adj_list+edge_end-1;
//					
//					if(graph_node->next !=NULL)	{
//						while(graph_node->next->label<= new_node->label)	/*this traversal ensures that adjacency list is always sorted */
//						{
//							graph_node = graph_node->next;
//							if(graph_node -> next == NULL)
//								break;
//						}
//					}
//
//					temp = graph_node->next;
//					graph_node->next = new_node;
//					new_node->next = temp;
//				}
//			}
//			
//			/* reduce the capacity of edge on path by (edge_capacity-flow) value */
//
//			graph_node = adj_list+edge_start-1;
//
//			while(graph_node->next != NULL)
//			{
//				graph_node = graph_node->next;
//				if(graph_node->label == edge_end)
//				{
//					graph_node->capacity = graph_node->capacity - flow;
//					break;
//				}
//			}
//					
//			if(path_temp->label == source)	
//				break;
//			path_temp = path+((path_temp->label)-1);
//			edge_end = edge_start;
//			edge_start = path_temp->label;
//		}	
//	}
//
//	int bottleneck()
//	{
//		node *temp;
//		int min_cap;
//		
//		temp = path+(sink-1);
//		min_cap = temp->capacity;
//		while(1)
//		{
//			if(temp->label == source)	
//				break;
//			temp = path+((temp->label)-1);
//			if(temp->capacity<min_cap)
//				min_cap = temp->capacity;
//		}	
//		return min_cap;
//		
//	}
//
//	void calculate_min_cut(FILE *f)
//	{
//		int i, main_sink, nA=0, nB=0;
//		int A[nV], B[nV];
//
//		main_sink = sink;
//
//		for(i=1;i<=nV;i++)
//		{
//			if (i == source || i == main_sink)
//			{
//				if(i==source)	/* consider source to be a part of set A */
//					A[nA++]=i;
//				if(i==main_sink)	/*consider sink to be a part of set B */
//					B[nB++]=i;
//				continue;
//			}
//			/* for all nodes except source and sink, compute BFS from source to each node in graph and see if its
//			   reacheable from source. If there is path path from source to node, it belongs to source set A else
//			   it belongs to sink set B */ 
//			else	
//			{
//				sink = i;
//				if(bfs())	{
//					A[nA++] = i;						
//				}else	{
//					B[nB++] = i;
//				}
//			}		
//		}
//
//		/* Push formatted min-cut output to output file */
//		fprintf(f,"\nMin-cut capacity=%d\n",max_flow);
//		fprintf(f,"The min-cut:\n");
//		fprintf(f,"The set S:\n");
//		for(i=0;i<nA;i++)
//			fprintf(f,"%d, ",A[i]);
//		fprintf(f,"\nThe set T:\n");
//		for(i=0;i<nB;i++)
//			fprintf(f,"%d, ",B[i]);
//		fprintf(f,"\n");
//
//	}
//
//	/*
//		This is a main Max Flow Capacity scaling algorithm implementation
//	*/
//	void capacity_scaling()
//	{
//		int b,i;
//		struct path_list *temp;
//		max_flow = 0;
//		FILE *op = fopen("output.txt","w");
//		
//		/* delta = nearest power of 2 from Maximum capacity, which is less than Maximum capacity in graph */
//
//		double power = log((double)max_capacity)/log(2);
//		//printf("power: %d log 8: %lf\n",power,log(max_capacity));
//		delta = (int)pow(2,power);
//		
//		//pathlst = (struct path_list *) malloc(sizeof(struct path_list)*nE*2);
//		
//		while(delta>=1)
//		{	
//			while(bfs())	{
//				b= bottleneck();	/* find a width of a path found by BFS on graph */
//				max_flow = max_flow + b;	/* add bottleneck to max_flow value */ 
//				add_to_pathlist(b);	
//				augment_path(b); 	/* augment newly found path to adjust forward and backward edges */
//			}
//			printf("delta: %d bottleneck: %d\n",delta,b);
//			fprintf(op,"With DELTA=%d, Flow Value=%d\n",delta,max_flow); 
//			delta = delta/2;
//		}
//
//		printf("Max Flow\n");
//		fprintf(op,"Max-flow value=%d\n",max_flow);
//
//		/* Write Max Flow graph to output file */
//		fprintf(op,"The max-flow:\n");
//		temp = pathlst;
//		temp = temp->next;
//		//for(i=0;i<nP;i++)
//		while(temp!=NULL)
//		{
//			printf("%s",temp->pathname);
//			printf(" %d\n",temp->capacity);
//			fprintf(op,"%s %d\n",temp->pathname,temp->capacity);
//			temp = temp->next;
//		}
//
//		calculate_min_cut(op);
//
//		fclose(op);
//		printf("max flow: %d\n",max_flow);
//	}


//