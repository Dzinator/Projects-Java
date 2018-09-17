/*

	Assignment 3

Name:		Yanis Hattab

Student ID:	260535922

*/



package flowNetwork;

import java.util.*;

public class FlowNetwork {

	//   The data structures follow what I presented in class.  Use three graphs which 
	//   represent the capacities, the flow, and the residual capacities.
	
	Graph capacities;      		// weights are capacities   (G)
	Graph flow;            		// weights are flows        (f)
	Graph residualCapacities;   // weights are determined by capacities (graph) and flow (G_f)
	
	//   Constructor.   The input is a graph that defines the edge capacities.
	
	public FlowNetwork(Graph capacities){
				
		this.capacities    = capacities;
		
		//  The flow and residual capacity graphs have the same vertices as the original graph.
		
		flow               = new Graph( capacities.getVertices() );
		residualCapacities = new Graph( capacities.getVertices() );
		
		//  Initialize the flow and residualCapacity graphs.   The flow is initialized to 0.  
		//  The residual capacity graph has only forward edges, with weights identical to the capacities. 

		for (String u : flow.getVertices()){
			for (String v : capacities.getEdgesFrom(u).keySet() ){
				
				//  Initialize the flow to 0 on each edge
				
				flow.addEdge(u, v, new Double(0.0));
				
				//	Initialize the residual capacity graph G_f to have the same edges and capacities as the original graph G (capacities).
				
				residualCapacities.addEdge(u, v, new Double( capacities.getEdgesFrom(u).get(v) ));
			}
		}
	}

	/*
	 * Here we find the maximum flow in the graph.    There is a while loop, and in each pass
	 * we find an augmenting path from s to t, and then augment the flow using this path.
	 * The beta value is computed in the augment method. 
	 */
	
	public void  maxFlow(String s,  String t){
		
		LinkedList<String> path;
		double beta;
		while (true){
			path = this.findAugmentingPath(s, t);
			if (path == null)
				break;
			else{
				beta = computeBottleneck(path);
				augment(path, beta);				
			}
		}	
	}
	
	/*
	 *   Use breadth first search (bfs) to find an s-t path in the residual graph.    
	 *   If such a path exists, return the path as a linked list of vertices (s,...,t).   
	 *   If no path from s to t in the residual graph exists, then return null.  
	 */
	
	public LinkedList<String>  findAugmentingPath(String s, String t){
		//I create the linked list to return and a temp variable to hold the current vertex
		LinkedList<String> augPath = new LinkedList<String>();
		String temp = t; 
		//I call a breadth first search on the graph
		residualCapacities.bfs(s);
		while(temp != null){
			//Check if there is only the starting vertex then break to return NULL
			if(residualCapacities.getParent(temp) == temp){
				break;
			}
			
			temp = residualCapacities.getParent(temp);
			//we cycle throught the vrtices found in bfs until we reach s
			if((temp != null) && (temp.equals(s))){
				//If we reached starting vertex, we then have a path and we build our linked list with that path
				temp = t;
				while(!temp.equals(s)){
					augPath.addFirst(temp);
					temp = residualCapacities.getParent(temp);
				}
				augPath.addFirst(temp);
				return augPath;
			}
			
		}
		return null;
		
	}
	
	/*
	 *   Given an augmenting path that was computed by findAugmentingPath(), 
	 *   find the bottleneck value (beta) of that path, and return it.
	 */
	
	public double computeBottleneck(LinkedList<String>  path){

		double beta = Double.MAX_VALUE;
		//I'll remember the index of where I am in the path with variable x
		int x = 1;
		//I'll store the capacities values in a hashmap called edgesValues 
		HashMap<String,Double> edgesValues = new HashMap<String,Double>();
		// I get the corresponding edges
		String u = path.get(x-1);
		String v = path.get(x);
		
		while(x < path.size()){
			edgesValues = residualCapacities.getEdgesFrom(u);
			beta = Math.min(beta, edgesValues.get(v));
			//move to the next edges if we are still within the path size
			x++;
			u = path.get(x-1);
			if(x < path.size()){
				v = path.get(x);
			}
			
		}
		
		return beta;
	}
	
	//  Once we know beta for a path, we recompute the flow and update the residual capacity graph.

	public void augment(LinkedList<String>  path,  double beta){
		int x = 1;
		String u = path.get(x-1);
		String v = path.get(x);
		
		while(x < path.size()){
			//I consider only the non null capacities and flows
			if((flow.getEdgesFrom(u).get(v) != null) && (capacities.getEdgesFrom(u).get(v) != null)){
				
				//Case of a forward edge, I augment it and update the flow and the residual graph
				if(flow.getEdgesFrom(u).get(v) < capacities.getEdgesFrom(u).get(v)){
					
					flow.getEdgesFrom(u).put(v, (flow.getEdgesFrom(u).get(v) + beta));
					residualCapacities.getEdgesFrom(u).put(v, (residualCapacities.getEdgesFrom(u).get(v) - beta));
					
					//I remove the edge from the residual graph if it has zero capacity left
					if(residualCapacities.getEdgesFrom(u).get(v) == 0){
						residualCapacities.getEdgesFrom(u).remove(v);
					}
				}
				//case of a backward edge
				
				else{
					flow.getEdgesFrom(u).put(v, (flow.getEdgesFrom(u).get(v) - beta));
					
					if(residualCapacities.getEdgesFrom(u).containsKey(v)){
						residualCapacities.getEdgesFrom(u).put(v, (residualCapacities.getEdgesFrom(u).get(v) + beta));
					}
					else residualCapacities.getEdgesFrom(u).put(v, beta);
				}
				
				//Moving to the next edges within path size
				x++;
				u = path.get(x-1);
				if(x < path.size()){
					v = path.get(x);
				}
			}
			else break;
		}
		//Final build up of the residual graph
		for(String a : residualCapacities.getVertices()){
			for (String b : flow.getEdgesFrom(a).keySet()){
				residualCapacities.getEdgesFrom(b).put(a, flow.getEdgesFrom(a).get(b));
				break;
			}
		}
		
	}

	//  This just dumps out the adjacency lists of the three graphs (original with capacities,  flow,  residual graph).
	
	public String toString(){
		return "capacities\n" + capacities + "\n\n" + "flow\n" + flow + "\n\n" + "residualCapacities\n" + residualCapacities;
	}
	
}
