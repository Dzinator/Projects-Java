/*

	Assignment 2

Name:		Yanis Hattab

Student ID:	260535922

*/

package a2posted;

import java.util.HashMap;
import java.util.HashSet;

public class Dijkstra {

	private IndexedHeap  pq;	
	private static int edgeCount = 0;               //  Use this to give names to the edges.										
	private HashMap<String,Edge>  edges = new HashMap<String,Edge>();

	private HashMap<String,String>   parent;
	private HashMap<String,Double>   dist;  //  This is variable "d" in lecture notes
	private String 					 startingVertex;	
	
	HashSet<String>  setS      ;
	HashSet<String>  setVminusS;

	public Dijkstra(){
		pq    		= new IndexedHeap()  ;		
		setS        = new HashSet<String>();
		setVminusS  = new HashSet<String>();		
		parent  = new HashMap<String,String>();
		dist 	= new HashMap<String,Double>();
	}
	
	/*
	 * Run Dijkstra's algorithm from a vertex whose name is given by the string s.
	 */
	
	public void dijkstraVertices(Graph graph, String s){
		
		//  temporary variables
		
		String u;	
		double  distToU,
				costUV;		
		
		HashMap<String,Double>    uAdjList;		
		initialize(graph,s);
		
		parent.put( s, null );
		pq.add(s, 0.0);   // shortest path from s to s is 0.
		this.startingVertex = s;

		//  --------- BEGIN: ADD YOUR CODE HERE  -----------------------
		
		//I first put all my vertices in the priority queue (pq) except my starting one
		for( String x : graph.getVertices()){
			if(!pq.contains(x)) pq.add(x, dist.get(x));
		}
		
		while(!pq.isEmpty()){
			//while the pq still has vertices, I get the minimum vertex as u
			u = pq.nameOfMin();
			distToU = pq.getMinPriority();
			
			setS.add(u);
			setVminusS.remove(u);
			//I remove u from pq
			pq.removeMin();
			
			uAdjList = graph.getAdjList().get(u);
			
			for( String v : uAdjList.keySet()){
				if(setVminusS.contains(v)){
					costUV =  uAdjList.get(v);
					distToU = dist.get(u);
					//I create a variable that is the path length from s to v
					Double pthLen = costUV + distToU;
					if (pthLen < dist.get(v)){
						//If the path length is better I update it in pq and dist and set the parent
						pq.changePriority(v, pthLen);
						dist.put(v, pthLen);
						parent.put(v, u);
					}
					
					
				}
			}
			
		}
		
		//  --------- END:  ADD YOUR CODE HERE  -----------------------
	}
	
	
	public void dijkstraEdges(Graph graph, String startingVertex){

		//  Makes sets of the names of vertices,  rather than vertices themselves.
		//  (Could have done it either way.)
		
		//  temporary variables
		
		Edge e;
		String u,v;
		double tmpDistToV;
		
		initialize(graph, startingVertex);

		//  --------- BEGIN: ADD YOUR CODE HERE  -----------------------
		
		//I first put all the edges of the starting vertex in the priority queue
		pqAddEdgesFrom(graph, startingVertex);
		
		while(setS.size() < graph.getVertices().size()){
			//This while loop will iterate until all vertices are in the the set S
			//I retrieve the lowest weight edge from pq as e and name its end points u and v
			e = edges.get(pq.nameOfMin());
			u = e.getStartVertex();
			v = e.getEndVertex();
			//I save the weight of the edge in tmpDistToV and remove the edge from pq
			tmpDistToV = pq.getPriority(e.getName());
			pq.removeMin();
			
			if(!(setS.contains(v))){
				//If the edge is a crossing edge (u in S, v in V/S) then I add it to djikstra
				parent.put(v, u);
				dist.put(v, tmpDistToV);
				//I add the edges starting at v to pq for next loop iteration
				pqAddEdgesFrom(graph, v);
				//I update the sets S and V/S
				setS.add(v);
				setVminusS.remove(v);
			}
			
		}
		
		//  --------- END:  ADD YOUR CODE HERE  -----------------------

	}
	
	/*
	 *   This initialization code is common to both of the methods that you need to implement so
	 *   I just factored it out.
	 */

	private void initialize(Graph graph, String startingVertex){
		//  initialization of sets V and VminusS,  dist, parent variables
		//

		for (String v : graph.getVertices()){
			setVminusS.add( v );
			dist.put(v, Double.POSITIVE_INFINITY);
			parent.put(v, null);
		}
		this.startingVertex = startingVertex;

		//   Transfer the starting vertex from VminusS to S and  

		setVminusS.remove(startingVertex);
		setS.add(startingVertex);
		dist.put(startingVertex, 0.0);
		parent.put(startingVertex, null);
	}

    /*  
	 *  helper method for dijkstraEdges:   Whenever we move a vertex u from V\S to S,  
	 *  add all edges (u,v) in E to the priority queue of edges.
	 *  
	 *  For each edge (u,v), if this edge gave a shorter total distance to v than any
	 *  previous paths that terminate at v,  then this edge will be removed from the priority
	 *  queue before these other vertices. 
	 *  
	 */
	
	public void pqAddEdgesFrom(Graph g, String u){
		double distToU = dist.get(u); 
		for (String v : g.getAdjList().get(u).keySet()  ){  //  all edges of form (u, v) 
			
			edgeCount++;
			Edge e = new Edge( edgeCount, u, v );
			edges.put( e.getName(), e );
			pq.add( e.getName() ,  distToU + g.getAdjList().get(u).get(v) );
		}
	}

	// -------------------------------------------------------------------------------------------
	
	public String toString(){
		String s = "";
		s += "\nRan Dijkstra from vertex " + startingVertex + "\n";
		for (String v : parent.keySet()){
			s += v + "'s parent is " +   parent.get(v) ;
			s += "   and pathlength is "  + dist.get(v) + "\n" ;
		}
		return s;
	}

	//  This class is used only to keep track of edges in the priority queue. 
	
	private class Edge{
		
		private String edgeName;
		private String u, v;
		
		Edge(int i, String u, String v){
			this.edgeName = "e_" + Integer.toString(i);
			this.u = u;
			this.v = v;
		}
		
		public String getName(){
			return edgeName;
		}
		
		String getStartVertex(){
			return u;
		}

		String getEndVertex(){
			return v;
		}
		
		public String toString(){
			return 	edgeName + " : " + u + " " + v;
		}
	}
	

}
