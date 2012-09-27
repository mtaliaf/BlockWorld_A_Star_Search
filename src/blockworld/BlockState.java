package blockworld;

import java.util.*;

public class BlockState
{   
    private ArrayList<Integer>[] game;
    private int NUMCOLUMNS;
    private int NUMBLOCKS;
    
    public BlockState(int numBlocks, int numColumns)
    {  
        NUMCOLUMNS = numColumns;
        NUMBLOCKS = numBlocks;
        game = new ArrayList[NUMCOLUMNS];
        
        for(int i = 0; i < NUMCOLUMNS; i++)
            game[i] = new ArrayList<Integer>();
        
        for(int i = 0; i < NUMBLOCKS; i++)
            game[0].add(i);        
    }
    
    public BlockState(BlockState b)
    {
        NUMCOLUMNS = b.NUMCOLUMNS;
        NUMBLOCKS = b.NUMBLOCKS;
        game = new ArrayList[NUMCOLUMNS];
        
        for(int i = 0; i < NUMCOLUMNS; i++)
        {
            game[i] = new ArrayList<Integer>();
            
            for(int j = 0; j<b.game[i].size(); j++)
                game[i].add(b.game[i].get(j));
        }
    }
    
    private void move(int source, int destination)
    {
        push(pop(source),destination);
    }
    
    public void randomMove()
    {
        Random r = new Random();
        int source = r.nextInt(NUMCOLUMNS);
        int destination = r.nextInt(NUMCOLUMNS);
        
        while(game[source].isEmpty()||source==destination)
        {
            source = r.nextInt(NUMCOLUMNS);
            destination = r.nextInt(NUMCOLUMNS);
        }
            move(source,destination);
    }
    
    public void generateRandomBoard(int i)
    {
        for(int j = 0; j<i; j++)
            randomMove();
    }
    
    public ArrayList<Integer[]> getValidMoves()
    {
        ArrayList<Integer[]> temp = new ArrayList<Integer[]>();
        
        for(int i = 0; i<NUMCOLUMNS; i++)
            for(int j = 0; j<NUMCOLUMNS; j++)
                if(!game[i].isEmpty()&&i!=j)
                {
                    Integer[] t = {i,j};
                    temp.add(t);
                }
        return temp;
    }
    
    public ArrayList<BlockState> generateChildren()
    {
        ArrayList<Integer[]> validMoves = getValidMoves();
        ArrayList<BlockState> children = new ArrayList<BlockState>();
        
        for(int i = 0; i < validMoves.size(); i++)
        {
            int s = validMoves.get(i)[0];
            int d = validMoves.get(i)[1];
            children.add(new BlockState(this));
            
            children.get(i).move(s, d);
        }
        
        return children;
            
    }
    
    /* 
     * Ho = number of blocks out of place;
     * H* = if in goal stack and not in correct order h+=2; if in other stack h+1
     */
    public int getHeuristic(boolean flag)
    {
        //Ho
        if(flag)
        {
            int h = NUMBLOCKS;        
            for(int i = 0; i < game[0].size()-1; i++)
            {
                if(game[0].get(i)==i)
                    h--;
                else
                    break;
            }

            return h;
        }
        //H*
        else
        {
            int h = 0;
            for(int i = 1; i<NUMCOLUMNS; i++)
                h+=game[i].size();
            for(int i = 0; i<game[0].size(); i++)
            {
                if(game[0].get(i)!=i)
                {
                    h+=2*(game[0].size()-i);
                    break;
                }
            }
            return h;
        }
    }
    
    public boolean compare()
    {
        if(game[0].size()!=NUMBLOCKS)
            return false;
        
        for(int i = 0; i < NUMBLOCKS; i++)
            if(game[0].get(i)!=i)
                return false;
        
        for(int i = 1;i<NUMCOLUMNS;i++)
            if(!game[i].isEmpty())
                return false;
        
        return true;
    }
    
    public boolean compare(BlockState p)
    {
        for(int i = 0; i<NUMCOLUMNS; i++)
        {
            if(game[i].size()!=p.game[i].size())
                return false;
            for(int j = 0; j<game[i].size();j++)
            {
                if(game[i].get(j)!=p.game[i].get(j))
                    return false;           
            }
        }
        return true;
    }
    
    private void push(int i,int column)
    {
        game[column].add(i);
    }
    
    private int pop(int column)
    {
        return game[column].remove(game[column].size()-1);
    }
    
    @Override
    public String toString()
    {
        String temp = "";
        for(int i = 0; i< NUMCOLUMNS; i++)
        {
            temp += i + " | " + game[i] + "\n";
        }
        
        return temp;
    }
}