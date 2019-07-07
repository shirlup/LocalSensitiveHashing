import java.util.Arrays;
import java.util.Comparator;

public class Tester {

    private double[][] sample;
    private double[][] testingSet;
    private int numOfNeighbors;


    Tester(double[][] sample, double[][] testingSet, int numOfNeighbors){
        this.sample = sample;
        this.testingSet = testingSet;
        this.numOfNeighbors = numOfNeighbors;
    }

    void run(E2HashFamily hashFamily, int k, int L){

    }

/*
    private double[][] linear(double[] q){
        double [][] pointsWithDistances = new double[sample.length][2];
        for(int i=0;i<sample.length;i++){
            pointsWithDistances[i][0]=i;
            pointsWithDistances[i][1]= UsableFunctions.norm2(sample[i], q);

        }
        Arrays.sort(pointsWithDistances, Comparator.comparingDouble(a -> a[1]));
        double[][] subArray = new double[numOfNeighbors][2];
        System.arraycopy(pointsWithDistances, 0,subArray , 0, numOfNeighbors);
        return subArray;
    }

 */



}
