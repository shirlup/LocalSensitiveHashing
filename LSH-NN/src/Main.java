import java.util.*;


public class Main {
    public static void main(String [] args){

        test1(30,10,3,1000,1);
        test2(30,10,3,1000,1); //K false positive
        test3(30,10,10,1000,1); //L false negative
        test4(30,10,1,100,1); //num

    }

    static double [][] generateSample(double[][] closeNeighbors ,int d,int maxEntryValue,int sampleSize){
        double [][] sample = new double[sampleSize][d];
        Random r = new Random();
        int num;

        for(num = 0 ; num < closeNeighbors.length ; num++)
            sample[num] = closeNeighbors[num];

        for(int i = num ; i < sampleSize ; i++){
            for(int j = 0 ; j < d ; j++){
                sample[i][j]= r.nextInt(maxEntryValue+1);
            }
        }
        return sample;
    }

    static double [] generateRandomQuery(int d,int maxValue){
        double[] toReturn =  new double[d];
        Random r = new Random();
        for(int i = 0 ; i < d; i++){
            toReturn[i] = r.nextInt(maxValue);
        }
        return toReturn;

    }

    static double[][] generateCloseNeighbors(int d,double[] q,int numOfNeighbors){

        double[][] neighbors = new double[numOfNeighbors][d];
        for(int num = 0 ; num < numOfNeighbors ; num++) {
            double[] v = new double[d];
            for (int i = 0; i < d; i++) {
                int x = (Math.random() > 0.5) ? 1 : -1;
                v[i] = q[i] + x * 1 / Math.sqrt(d);
            }
            neighbors[num] = v;
        }

        return neighbors;
    }

    //Basic test see all functions do work
    static void test1(int d,int maxEntryValue,int numOfNeighbors,int sampleSize,double radius){

        double [] q = generateRandomQuery(d,maxEntryValue);
        double[][] closeNeighbors = generateCloseNeighbors(d, q,numOfNeighbors);
        double [][] sample = generateSample(closeNeighbors,d,maxEntryValue,sampleSize);
        System.out.println("************************************* SAMPLE 1 *********************************************");
        System.out.println(Arrays.deepToString(sample));
        E2HashFamily hashFamily = new E2HashFamily(1000*radius,d);
        LSHbase lhs = new LSHbase(hashFamily,20,10);
        lhs.indexDataSample(sample);
        double[] [] check = lhs.query(q,numOfNeighbors);
        System.out.println("***************************************** Q ************************************************");
        System.out.println(Arrays.toString(q));
        System.out.println("************************************* CHECK ARRAY ******************************************");
        System.out.println(Arrays.deepToString(check));
    }

    //Checking our experiment with with increasing values of k on the same sample and L=1 is constant
    static void test2(int d,int maxEntryValue,int numOfNeighbors,int sampleSize,double radius){

        double [] q = generateRandomQuery(d,maxEntryValue);
        double[][] closeNeighbors = generateCloseNeighbors(d, q,numOfNeighbors);
        double [][] sample = generateSample(closeNeighbors,d,maxEntryValue,sampleSize);
        System.out.println("************************************* SAMPLE 1 *********************************************");
        System.out.println(Arrays.deepToString(sample));

        double[][] check;
        E2HashFamily hashFamily = new E2HashFamily(100*radius,d);
        double L = 1;
            for (double k = 1; k < sampleSize/4; k++) {
                System.out.println("------------------------------TEST2: K: * " + k + "* L: *" + L + "* ----------------------------------------");
                System.out.println();
                LSHbase lhs = new LSHbase(hashFamily, (int) k, (int) L);
                lhs.indexDataSample(sample);
                check = lhs.query(q, numOfNeighbors);
                System.out.println("************************************* CHECK ARRAY ******************************************");
                System.out.println();
                System.out.println(Arrays.deepToString(check));
                System.out.println();
                System.out.println("--------------------------END OF ROUND WITH K AND L-----------------------------------------");
                System.out.println();
            }
    }

    //Checking our experiment with with increasing values of L on the same sample and k=50 is constant
    static void test3(int d,int maxEntryValue,int numOfNeighbors,int sampleSize,double radius){

        double [] q = generateRandomQuery(d,maxEntryValue);
        double[][] closeNeighbors = generateCloseNeighbors(d, q,numOfNeighbors);
        double [][] sample = generateSample(closeNeighbors,d,maxEntryValue,sampleSize);
        System.out.println("************************************* SAMPLE 2 *********************************************");
        System.out.println(Arrays.deepToString(sample));

        double[][] check;
        E2HashFamily hashFamily = new E2HashFamily(100*radius,d);
        double k = 50;
        for (double L = 1; L < 50; L++) {
            System.out.println("------------------------------TEST3: K: * " + k + "* L: *" + L + "* ----------------------------------------");
            System.out.println();
            LSHbase lhs = new LSHbase(hashFamily, (int) k, (int) L);
            lhs.indexDataSample(sample);
            check = lhs.query(q, numOfNeighbors);
            System.out.println("************************************* CHECK ARRAY ******************************************");
            System.out.println();
            System.out.println(Arrays.deepToString(check));
            System.out.println();
            System.out.println(".....................  " + check.length + " ......................");
            System.out.println("--------------------------END OF ROUND WITH K AND L-----------------------------------------");
            System.out.println();
        }


    }

    //fix k and fix L value with increasing amount of data in the sample group - demonstrates that as we increase data size, the number of comparisons done by the algorithm also increase.
    static void test4(int d,int maxEntryValue,int numOfNeighbors,int sampleSize,double radius){

        double [][] sample;
        double[][] check;
        E2HashFamily hashFamily;
        double [] q = generateRandomQuery(d,maxEntryValue);
        double[][] closeNeighbors = generateCloseNeighbors(d, q,numOfNeighbors);

        double L = 20;
        double k = 5;
        hashFamily = new E2HashFamily(100*radius,d);
        LSHbase lhs;

        for (int i = sampleSize ; i < 40000 ; i+=1000) {
            sample = generateSample(closeNeighbors,d,maxEntryValue,i);
            lhs = new LSHbase(hashFamily, (int) k, (int) L);
            System.out.println("************************************* SAMPLE "+ Math.round(i/1000) + 1 + " *********************************************");
            lhs.indexDataSample(sample);
            check = lhs.query(q, numOfNeighbors);
            System.out.println(Arrays.deepToString(check));
            System.out.println();
        }

    }
}
