package blockworld;

import java.util.*;

public class BlockWorld 
{
    public static void main(String[] args) 
    {
        BlockState s  = new BlockState(8,3);
        s.generateRandomBoard(100);
        System.out.println(s+"\n");
        
        System.out.println("SOLVING...");
        BlockSearch.printBackTrace(BlockSearch.aStarSearch(s,false));  
    }
}
