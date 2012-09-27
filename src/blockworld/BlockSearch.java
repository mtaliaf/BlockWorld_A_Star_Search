package blockworld;

import java.util.*;

public class BlockSearch 
{
    private static HashMap buildDictionary(BlockState currentNode, 
                                           ArrayList<BlockState> children, 
                                           HashMap parent,
                                           int depth,
                                           int maxFrontierSize,
                                           int goalsTested,
                                           int heuristic)
    {
        HashMap temp = new HashMap();
        temp.put("node", currentNode);
        temp.put("children", children);
        temp.put("parent", parent);
        temp.put("depth", depth);
        temp.put("maxFrontierSize", maxFrontierSize);
        temp.put("goalsTested", goalsTested);
        temp.put("heuristic", heuristic);
        
        return temp;
    }
    
    private static boolean isInFrontier(PriorityQueue<HashMap> frontier, BlockState child)
    {
        for(HashMap f: frontier)
            if(((BlockState)f.get("node")).compare(child))
                return true;
        return false;
    }
    
    private static boolean isInSearched(ArrayList<BlockState> searched, BlockState child)
    {
        for(BlockState s: searched)
            if(child.compare(s))
                return true;
        return false;
    }
    
    public static HashMap aStarSearch(BlockState p, boolean flag)
    {
        int maxFrontierSize = 0;
        int goalsTested = 1;
        
        if(p.compare())
            return buildDictionary(p,null,null,0,maxFrontierSize,1,p.getHeuristic(flag));
        
        PriorityQueue<HashMap> frontier = new PriorityQueue<HashMap>(100,new BlockStateComparator());
        ArrayList<BlockState> searched = new ArrayList<BlockState>();
        
        frontier.add(buildDictionary(p,p.generateChildren(),null,0,1,1,p.getHeuristic(flag)));
        searched.add(p);
        
        while(true)
        {
            goalsTested++;
            maxFrontierSize = (frontier.size() > maxFrontierSize) ? frontier.size() : maxFrontierSize;
            
            if(frontier.isEmpty())
                return null;
            HashMap node = frontier.poll();
            
            for(BlockState child: (ArrayList<BlockState>)node.get("children"))
            {
                int d = ((Integer)node.get("depth"))+1;
                if(child.compare())
                    return buildDictionary(child,null,node,d,maxFrontierSize,goalsTested,d+child.getHeuristic(flag));
                if(!isInFrontier(frontier,child)&&!isInSearched(searched,child))
                {
                    frontier.add(buildDictionary(child,child.generateChildren(),node,d,maxFrontierSize,goalsTested,d+child.getHeuristic(flag)));
                    searched.add(child);
                }
            }
            
        }

    }
    
    public static void printBackTrace(HashMap h)
    {
        if(h == null)
        {
            System.out.println(" FAIL!!");
            return;
        }
        
        BlockState p = (BlockState)h.get("node");        
        Stack<BlockState> s = new Stack<BlockState>();
        s.push(p);
        
        
        System.out.print(" Depth: "+(Integer)h.get("depth"));
        System.out.print(" maxFrontierSize: "+(Integer)h.get("maxFrontierSize"));
        System.out.print(" Goals Tested: "+(Integer)h.get("goalsTested") + "\n");
        
        
        //System.out.println((Integer)h.get("depth") + "," + (Integer)h.get("maxFrontierSize") + "," + (Integer)h.get("goalsTested"));
        
        while(h.get("parent")!=null)
        {
            h = (HashMap)h.get("parent");
            p = (BlockState)h.get("node"); 
            s.push(p);
        }
        
        while(!s.isEmpty())
            System.out.println(s.pop());
    }
}


