#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

struct n{
	int label;
	int capacity;
	struct n *next;
	int edge_type;
};

typedef struct n node;

struct path_list {
	char pathname[4];
	int capacity;
	struct path_list *next;
};

struct path_list *pathlst = NULL;
int source, sink, max_flow, delta, nV, nE, max_capacity, nP=0;

node * adj_list;
node * max_flow_graph;
node *path; 
void print_paths();
void print_adj_list();
