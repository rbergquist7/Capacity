
package graph.core;

public class Edge {
    private Node a, b;

    /** Creates a new instance of Edge */
    public Edge(Node a, Node b) throws EdgeException {


        if (a == b) {
            throw new EdgeException("Cannot connect to self.");
        }
        if (a == null || b == null) {
        	throw new EdgeException("Cannot create edge");
        }
        if (a.sharesEdgeWidth(b)) {
            throw new EdgeException("Already connected.");
        }

        this.a = a;
        this.b = b;


        a.addEdge(this);
        b.addEdge(this);
    }
    public double getLength() {
        if (a == null || b == null)
            return 0;

        double x = a.getX() - b.getX();
        double y = a.getY() - b.getY();
        double z = a.getZ() - b.getZ();

        return Math.sqrt(x*x + y*y + z*z);
    }

    public Node getOpposingNode(Node n) {
        if (n == a)
            return b;
        else if (n == b)
            return a;
        else
            return null;
    }

    public void destroy() {
        a.removeEdge(this);
        b.removeEdge(this);
    }

    public void destroy(Node n) {
        getOpposingNode(n).removeEdge(this);
    }

    @Override
    public String toString() {
        return "From node: " + a.getIDString() + " to node: " + b.getIDString();
    }

    public Node getA() {
        return a;
    }

    public void setA(Node a) {
        this.a = a;
    }

    public Node getB() {
        return b;
    }

    public void setB(Node b) {
        this.b = b;
    }


}