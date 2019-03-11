import java.util.LinkedHashSet;
import java.util.Set;

public class AStarAdaptive {
	private boolean reached;
	private boolean[][] explored;
	private Node[][] board;
	private boolean[][] closedSet;
	private Maze maze;
	private MazeBlocks MazeBlocks;
	private Node start;
	private Node end;
	private int len;
	private int wid;
	private int expand; 
	
	AStarAdaptive(Maze m){
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
        
        closedSet = new boolean[len+2][wid+2];
        for(int i=0;i<len;i++){
        	for(int j=0;j<wid;j++){
                explored[i][j] = false;
                closedSet[i][j] = false;
        	}
        }
        
	}
	
	AStarAdaptive(MazeBlocks m){
		this.MazeBlocks = m;
		this.len = m.length;
		this.wid = m.width;
		this.start = m.Start;
		this.end = m.End;
        this.expand = 0;
        this.reached = false;
        explored = new boolean[len][wid];
        
        for(int i=0;i<len;i++){
        	for(int j=0;j<wid;j++){
        		explored[i][j] = false;
        	}
        }
        
        this.board= new Node[len][wid];
        
        for(int i=0;i<len;i++){
        	for(int j=0;j< wid;j++){
        		this.board[i][j] = m.board[i][j];
        		this.board[i][j].isVisited = false;
        	}
        }
        
        closedSet = new boolean[len][wid];
        
        for(int i=0;i<len;i++){
        	for(int j=0;j<wid;j++){
                explored[i][j] = false;
                closedSet[i][j] = false;
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
        Node pathP = lastVisitedNode;
        while (pathP != start)
        {
            pathP.isVisited = true;
            pathP = pathP.parent;
        }
    }

    public void aStarSearch()
    {
        long  sTime;
        long eTime;
        long elapsedTime;

        sTime = System.currentTimeMillis();
        aStarSearchNormal();
        eTime = System.currentTimeMillis();
        elapsedTime = eTime - sTime;
        
        System.out.println("Time elapsed on Repeat Forward A*: " + elapsedTime + " ms");
        System.out.println("Number of expanded node: " + getExpanded());
        MazeBlocks.print();

        for(int i=0;i<len;i++){
        	for(int j=0;j<wid;j++){
                explored[i][j] = false;
                MazeBlocks.board[i][j].isVisited = false;
                if(closedSet[i][j] == true)
                {
                    MazeBlocks.board[i][j].h = end.g - MazeBlocks.board[i][j].g;
                    closedSet[i][j] = false;
                }
            }
        }

        reached = false;
        this.expand = 0;
        sTime = System.currentTimeMillis();
        aStarSearchAdaptive();
        eTime = System.currentTimeMillis();
        elapsedTime = eTime - sTime;
        
        System.out.println("Time elapsed on Adaptive A*: " + elapsedTime + " ms");
        System.out.println("Number of expanded node: " + getExpanded());
        MazeBlocks.print();
    }

    public void aStarSearchAdaptive()
    {
        boolean hasPath = false;

        Set<Node> openSet = new LinkedHashSet();
     
        start.g = 0;
        start.h = 0;
        start.f = 0;
        openSet.add(start);

        Node lowFNode = null;

        while(!openSet.isEmpty() && !reached)
        {
            lowFNode = getLowestNodeByH(openSet);    

            openSet.remove(lowFNode);
            if (closedSet == null)
            {
                System.out.println("is null");
                return;
            }
            
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
                    currNeighbor.g = lowFNode.g + 1.0;
                    tracePath(currNeighbor);
                    hasPath = true;
                    reached = true;
                    
                }
            
                else if (closedSet[cx][cy] == false)
                {
                	this.expand++;
                
                  
                    if(currNeighbor.f >= (Double.MAX_VALUE-100)) 
                    {
                        newH = calcHManhattan(cx, cy);
                    }
                    else
                    {
                        newH = currNeighbor.h;
                    }
                    
                    newG = lowFNode.g + 1.0;
                    newF = newG + newH;
                    
            
                    currNeighbor.g = newG;
                    currNeighbor.h = newH;
                    currNeighbor.f = newF;
                    currNeighbor.parent = lowFNode;
                    
      
                    openSet.add(currNeighbor);

                }
            }
         
        
        }
      
        if (hasPath&& reached)
            System.out.println("Found a viable path from Start to End");        
        else
            System.out.println("No viable path found"); 
    }

    public void aStarSearchNormal()
    {
        boolean hasPath = false;

        Set<Node> openSet = new LinkedHashSet();

        start.g = 0;
        start.h = 0;
        start.f = 0;
        openSet.add(start);

        Node lowestFNode = null;
        while(!openSet.isEmpty() && !reached)
        {
            lowestFNode = getLowestNode(openSet);
        
            openSet.remove(lowestFNode);
            closedSet[lowestFNode.x][lowestFNode.y] = true;
           
            double newG, newF, newH;

            for (int i = 0; i < lowestFNode.neighbors.size(); ++i)
            {
                Node currentNeighbor = lowestFNode.neighbors.get(i);
                int cx = currentNeighbor.x;
                int cy = currentNeighbor.y;

                
 
                if (isDest(currentNeighbor))
                {
      
                    currentNeighbor.parent = lowestFNode; 
                    currentNeighbor.g = lowestFNode.g + 1.0;
                    tracePath(currentNeighbor);
                    hasPath = true;
                    reached = true;
                    
                }
           
                else if (closedSet[cx][cy] == false)
                {
                	this.expand++;
           
                    newG = lowestFNode.g + 1.0; 
                    newH = calcHManhattan(cx, cy);
                    newF = newG + newH;
         
                    currentNeighbor.g = newG;
                    currentNeighbor.h = newH;
                    currentNeighbor.f = newF;
                    currentNeighbor.parent = lowestFNode;

                    openSet.add(currentNeighbor);

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

        Node p = openArray[0];
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
    

    private Node getLowestNodeByH(Set<Node> openSet)
    {
        double minH = Double.MAX_VALUE;
        Node[] openArray = openSet.toArray(new Node[0]);

        Node p = openArray[0];
        for (int i = 0; i < openArray.length; ++i)
        {
            if (openArray[i].h < minH)    
            {
                minH = openArray[i].h;
                p = openArray[i];
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
