import java.util.*;

 public class MazeBlocks {
	public Node Start;
	public Node End;
	public Node[][] board;
    public int length;
    public int width;

    public MazeBlocks(int length, int width){
        this.length = length;
        this.width = width;
        
        this.board= new Node[length][width];
        
        int [][] board2 = {{1,1,1,1,1,1,1,1,1},
        				   {1,0,0,0,0,0,0,0,1},
        		  		   {1,0,1,0,0,0,0,0,1},
        		  		   {1,0,1,1,1,1,1,0,1},
        		  		   {1,0,0,0,0,0,0,0,1},
        		  		   {1,0,0,0,0,0,0,0,1},
        				   {1,0,0,0,0,0,0,0,1},
        				   {1,1,1,1,1,1,1,1,1}};
        int [][] board3 = {{0,0,0,0,0},
				   		  {0,0,1,0,1},
				   		  {1,1,0,0,0},
				   		  {0,0,0,0,0},
				   		  {0,1,0,0,1}};
        				
        			
        double p = 0.3;
        
        for(int i=0;i< length;i++){
            for(int j=0;j < width;j++){
                this.board[i][j] = new Node(i, j);
                if (Math.random() < p)
                {
                    this.board[i][j].isBlocked = true;
                }
                
            }
        }

        this.Start = this.board[0][0];
        this.Start.h = 0;
        this.Start.f = 0;
        this.Start.g = 0;

        this.End = this.board[length-1][width-1];
        this.End.isBlocked = false;
        this.Start.isBlocked = false;

        for(int i=0;i< length;i++){
            for(int j=0;j< width;j++){
                // West
                if( i>0 && !this.board[i-1][j].isBlocked){
                    this.board[i][j].neighbors.add(this.board[i-1][j]);
                }
                // East
                if( i< this.length-1 && !this.board[i+1][j].isBlocked){
                    this.board[i][j].neighbors.add(this.board[i+1][j]);
                }
                // South
                if( j< this.width-1 && !this.board[i][j+1].isBlocked){
                    this.board[i][j].neighbors.add(this.board[i][j+1]);
                }
                // North
                if (j > 0 && !this.board[i][j-1].isBlocked){
                    this.board[i][j].neighbors.add(this.board[i][j-1]);
                }
            }
        }
    }

    public void print(){
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.width; j++) {
                if(this.board[i][j] == this.Start){  
                    System.out.print(" S ");
                }
                else if (this.board[i][j] == this.End) {
                    System.out.print(" E ");
                }
                else if(this.board[i][j].isBlocked == false){
                	
                    if (this.board[i][j].isVisited) 
                        System.out.print(" P ");
                    else 
                        System.out.print("   ");
                }
                else{
                    System.out.print(" X ");
                }
            }
            System.out.println();
        }
    }
    
}