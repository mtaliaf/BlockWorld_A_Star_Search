package blockworld;

import java.util.*;

public class BlockStateComparator implements Comparator<HashMap>
{
    @Override
    public int compare(HashMap x, HashMap y)
    {
        // Assume neither string is null. Real code should
        // probably be more robust
        if ((Integer)x.get("heuristic") < (Integer)y.get("heuristic"))
        {
            return -1;
        }
        if((Integer)x.get("heuristic") > (Integer)y.get("heuristic"))
        {
            return 1;
        }
        return 0;
    }

}
