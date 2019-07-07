
//import org.junit.jupiter.api.Test;

import java.util.*;

public class LSHbase {

    private E2HashFamily e2HashFamily;
    private int k;
    private int L;
    private PairTable[] tables;
    private double[][] sample;

    LSHbase(E2HashFamily e2HashFamily, int k, int L){
        this.e2HashFamily = e2HashFamily;
        this.k = k;
        this.L = 0;
        this.tables = new PairTable[L];
        this.resizeTable(L);
    }

     private void resizeTable(int L) {
        PairTable[] newHashTables = new PairTable[L];
        if(this.L > L){
            System.arraycopy(this.tables, 0, newHashTables, 0, L);

        }else{
            System.arraycopy(this.tables, 0, newHashTables, 0, tables.length);
            E2HashFunction[] hashes = new E2HashFunction[k]; //creates K hash functions for every i = 0 to L
            for(int i = this.L; i < L ; i++){
                for (int j = 0 ; j < k ; j++){
                    hashes[j] = e2HashFamily.getHashFunction();
                }
                newHashTables[i] = new PairTable(hashes,new HashMap());
            }
        }
        this.tables = newHashTables;
        this.L = L;
    }

    void indexDataSample(double[][] newSample){
        this.sample = newSample;
        for (PairTable table : tables) {
            for (int j = 0; j < newSample.length; j++) {
                String key = getKey(newSample[j], table.getE2HashFunctions());
                if (!table.getTable().containsKey(key)) {
                    table.getTable().put(key, new HashSet<Integer>());
                }
                ((HashSet<Integer>) table.getTable().get(key)).add(j);
            }
        }
    }


    private String getKey(double[] pointToHash, E2HashFunction[] hashFunctions){
        double [] toReturn = new double[k];
        for(int i =0 ; i<k; i++){
            toReturn[i] = hashFunctions[i].hash(pointToHash);
        }
        return e2HashFamily.combine(toReturn);
    }

    private HashSet<Integer> mergeSet(HashSet<Integer> a, HashSet<Integer> b)
    {
        return new HashSet<>() {
            {
                addAll(a);
                addAll(b);
            } };
    }

    //takes a query and a number of closestNeighbords and check GO TO ALL BUCKETS IN HASH BACKETS
    double[][] query(double[] q, int closestNeighbors){
        HashSet<Integer> candidates = new HashSet<>();
        String key;
        for (PairTable table : tables) {
            key = getKey(q, table.getE2HashFunctions());
            candidates = mergeSet(candidates, (HashSet<Integer>) table.getTable().getOrDefault(key, new HashSet<Integer>()));
        }
        double [][] candidatesWithDistance = new double[candidates.size()][2];
        int i = 0;
        for (int pointNum : candidates) {
                candidatesWithDistance[i][0] = pointNum;
                candidatesWithDistance[i][1] = UsableFunctions.norm2(sample[pointNum], q);
                i++;
        }
        System.out.println();
        System.out.println("***********number of comparasions: "+ candidates.size() + " **************");
        System.out.println();
        Arrays.sort(candidatesWithDistance, Comparator.comparingDouble(a -> a[1]));
        double[][] subArray = new double[Math.min(closestNeighbors,candidatesWithDistance.length)][2];
        for (int j = 0; j < Math.min(closestNeighbors,candidatesWithDistance.length); j++) {
            subArray[j][0] = candidatesWithDistance[j][0];
            subArray[j][1] = candidatesWithDistance[j][1];
        }
        return subArray;

    }

}
