

package graph.core;

/*
 * Node.java
 *
 * Created on 19 October 2005, 15:30
 */
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Linus Norton <linusnorton@gmail.com>
 */
public class Node {
    private double x, y, z;
    private int id;
    private ArrayList<Edge> edges;

    public Node(int id) {
        this.x = Math.ceil(Math.random() * 100);
        this.y = Math.ceil(Math.random() * 100);
        this.z = Math.ceil(Math.random() * 100);
        this.id = id;

        edges = new ArrayList<Edge>();        
    }

    public Node() {
        this.x = Math.ceil(Math.random() * 100);
        this.y = Math.ceil(Math.random() * 100);
        this.z = Math.ceil(Math.random() * 100);
        this.id = 0;

        edges = new ArrayList<Edge>();
    }

    /**
     * along as the same edge is not being added twice add the edge
     */
    public void addEdge(Edge e) {
        if (!edges.contains(e) && e != null) {
            edges.add(e);
        }
    }

    /** remove an edge from the node */
    public void removeEdge(Edge e) {
        edges.remove(e);
    }

    /** returns true if e.getOpposingNode(this) == b
     *  where e is each one of the edges the node has
     */
    public boolean sharesEdgeWidth(Node b) {
        Iterator<Edge> it = edges.iterator();
        while (it.hasNext()) {
            Edge e = it.next();

            //node opposite this one is b then connected
            if (e.getOpposingNode(this) == b) {
                return true;
            }
        }

        return false;
    }

    /** move to the side in an anti clockwise direction by theta (radians) */
    public void yaw(double theta) {
        double X = z * Math.sin(theta) + x * Math.cos(theta);
        double Z = z * Math.cos(theta) - x * Math.sin(theta);
        x = X;
        z = Z;
    }

    /** move down by theta (radians) */
    public void pitch(double theta) {
        double Y = y * Math.cos(theta) - z * Math.sin(theta);
        double Z = y * Math.sin(theta) + z * Math.cos(theta);
        y = Y;
        z = Z;
    }

    public String getIDString() {
        return Integer.toString(id);
    }

    /** destroy all edges connected to this node */
    public void destroy(ArrayList<Edge> masterEdgeList) {
       Iterator<Edge> it = edges.iterator();
       while (it.hasNext()) {
           //destroy but only tell the other node because
           //we don't want concurrent modification
           Edge e = it.next();
           masterEdgeList.remove(e);
           e.destroy(this);
       }
    }

    public int getDegree() {
        return edges.size();
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

}