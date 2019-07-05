import java.util.HashMap;

public class PairTable
{
  private E2HashFunction[] e2HashFunctions;
  private HashMap<String, java.util.HashSet<Integer>> table;
  
  PairTable(E2HashFunction[] h, HashMap d)
  {
    e2HashFunctions = h;
    table = d;
  }
  
  E2HashFunction[] getE2HashFunctions() {
    return e2HashFunctions;
  }
  
  HashMap getTable() {
    return table;
  }
}
