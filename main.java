
public class main {
    public static void main(String[] args) {
    	long sTime;
    	long eTime;
    	long elapsedTime;

    	MazeBlocks m = new MazeBlocks(21,21);
    	m.print();
    	
        AStarAdaptive aAdaptive = new AStarAdaptive(m);
        aAdaptive.aStarSearch();
    	
        AStarBackward astarbackward = new AStarBackward(m);        
        sTime = System.currentTimeMillis();
        astarbackward.aStarSearch();
        eTime = System.currentTimeMillis();
        elapsedTime = eTime - sTime;        
        System.out.println("Number of expanded node:" + astarbackward.getExpanded());
        System.out.println("Time elapsed on Repeated Backward A*: " + elapsedTime + " ms");
        m.print();
        
        AStarLargerG astarlarger = new AStarLargerG(m);
        sTime = System.currentTimeMillis();
        astarlarger.aStarSearch();
        eTime = System.currentTimeMillis();
        elapsedTime = eTime - sTime;
        System.out.println("Time elapsed on LargerG A*: " + elapsedTime + " ms");
        System.out.println("Number of expanded node: " + astarlarger.getExpanded());
        m.print();
        
        AStarSmallerG astarsmaller = new AStarSmallerG(m);
        sTime = System.currentTimeMillis();
        astarsmaller.aStarSearch();
        eTime = System.currentTimeMillis();
        elapsedTime = eTime - sTime;
        System.out.println("Time elapsed on SmallerG A*: " + elapsedTime + " ms");
        System.out.println("Number of expanded node: " + astarsmaller.getExpanded());
        m.print();

        
       
    }
}
