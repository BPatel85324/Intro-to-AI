import java.util.LinkedHashSet;
import java.util.Set;

public class AStarLargerG{
	private static final int C = 10000;
	private MazeBlocks MazeBlocks;
	private Node [][] board;
	private Maze maze;
	private Node start;
	private Node end;
	private int len;
	private int wid;
	private int expand;
	private boolean reached;
	
	AStarLargerG(Maze m){
		this.maze = m;
		this.len = m.getLength();
		this.wid = m.getWidth();
		this.start = m.Start;
		this.end = m.End;
        this.expand = 0;
        this.reached = false;
        this.board= new Node[len+2][wid+2];
        for(int i=0;i<len+2;i++){
        	for(int j=0;j< wid+2;j++){
        		this.board[i][j] = m.board[i][j];
        		this.board[i][j].isVisited = false;
        	}
        }
	}
	
	AStarLargerG(MazeBlocks m){
		this.MazeBlocks = m;
		this.len = m.length;
		this.wid = m.width;
		this.start = m.Start;
		this.end = m.End;
        this.expand = 0;
        this.reached = false;
        this.board= new Node[len][wid];
        for(int i=0;i<this.len;i++){
        	for(int j=0;j< this.wid;j++){
        		this.board[i][j] = m.board[i][j];
        		this.board[i][j].isVisited = false;
        	}
        }

	}
 
    public boolean isDest(Node node)
    {
        if(node == end)
            return true;
        else   
            return false;
    }

    public double calcHManhattan(int row, int col)
    {
        return Math.abs(row - end.x) + Math.abs(col - end.y);
    }

    public double calcHValue(int row, int col)
    {
        return ((double) Math.sqrt((row-(len-1)*(row-(len-1)) + (col-(wid-1)*(col-(wid-1))))));
    }

    private void tracePath(Node lastVisitedNode)
    {
        Node nodeP = lastVisitedNode;
        while (nodeP != start)
        {
            nodeP.isVisited = true;
            nodeP = nodeP.parent;
        }
    }

    public void aStarSearch()
    {
        boolean hasPath = false;

        Set<Node> openSet = new LinkedHashSet();
    
        boolean[][] closedSet = new boolean[len+2][wid+2];

        start.g = 0;
        start.h = 0;
        start.f = 0;
        openSet.add(start);

        Node lowFNode = null;
        while(!openSet.isEmpty() && !reached)
        {
            lowFNode = getLowestNode(openSet);
            openSet.remove(lowFNode);
            closedSet[lowFNode.x][lowFNode.y] = true;
            
            double newG, newF, newH;
            for (int i = 0; i < lowFNode.neighbors.size(); ++i)
            {
                Node currNeighbor = lowFNode.neighbors.get(i);
                int cx = currNeighbor.x;
                int cy = currNeighbor.y;

                if (isDest(currNeighbor))
                {
                    currNeighbor.parent = lowFNode;
                    tracePath(currNeighbor);
                    hasPath = true;
                    reached = true;
                    
                }
             
                else if (closedSet[cx][cy] == false)
                {
                    
                    newG = lowFNode.g + 1.0; 
                    newH = calcHManhattan(cx, cy);
                    newF = newG + newH;

                    currNeighbor.g = newG;
                    currNeighbor.h = newH;
                    currNeighbor.f = newF;
                    currNeighbor.parent = lowFNode;

                    openSet.add(currNeighbor);
                    this.expand ++;
                }
            }
       
        }
    
        if (hasPath&& reached)
            System.out.println("Found a viable path from Start to End");        
        else
            System.out.println("No viable path found"); 
    }

    private Node getLowestNode(Set<Node> openSet)
    {

        double minF = Double.MAX_VALUE;
        Node[] openArray = openSet.toArray(new Node[0]);
        double Mingvalue = 0;
        Node p = null;
        for (int i = 0; i < openArray.length; ++i)
        {
            if (openArray[i].f < minF)    
            {
                minF = openArray[i].f;
                p = openArray[i];
            }
            else if (openArray[i].f == minF){
            	if(p.g < openArray[i].g){
            		p = openArray[i];
            	}
            }
        }
        return p;
    }
  
    static int displayN(Node node)
    {
        return node.neighbors.size();
    }

    public int getExpanded(){
    	return this.expand;
    }
}
