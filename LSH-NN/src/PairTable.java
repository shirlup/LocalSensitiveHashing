import java.util.HashMap;
import java.util.HashSet;


public class PairTable {
    private E2HashFunction [] e2HashFunctions;
    private HashMap <String, HashSet<Integer>> table;

    PairTable(E2HashFunction[] h, HashMap d){
        this.e2HashFunctions = h;
        this.table = d;
    }

    E2HashFunction[] getE2HashFunctions() {
        return e2HashFunctions;
    }

    HashMap getTable() {
        return table;
    }


}
