import java.util.*;

public class Node {
    boolean isBlocked; 
    boolean isVisited;
    List<Node> neighbors = new ArrayList<Node>();
    Node parent;
    int x, y;
    double f, g, h;

    public boolean eastwall;
    public boolean westwall;
    public boolean northwall;
    public boolean southwall;

    public Node(int x, int y) {
        this.isBlocked = false;
        this.isVisited = false;

        this.parent = null;
        f = Double.MAX_VALUE;
        g = Double.MAX_VALUE;
        h = Double.MAX_VALUE;

        this.x = x;
        this.y = y;
    }

    public Node(int x, int y, Node parent) {
        this.isBlocked = false;
        this.isVisited = false;

        this.parent = parent;
        f = Double.MAX_VALUE;
        g = Double.MAX_VALUE;
        h = Double.MAX_VALUE;

        this.x = x;
        this.y = y;
    }
}
