
import java.util.Arrays;
import java.util.Random;

class E2HashFamily {
    private double cr;
    private double d;

    E2HashFamily(double cr, double d){
        this.cr = cr;
        this.d = d;
    }

    E2HashFunction getHashFunction(){
        return new E2HashFunction(getRandomGaussianVector(),getOffset(),cr);
    }

    double [] getRandomGaussianVector (){
        double [] vectorToReturn = new double [(int)(d)];
        Random rand = new Random();
        for (int i = 0; i<d ; i++){
            vectorToReturn[i] = rand.nextGaussian();
        }
        return vectorToReturn;
    }

    private double getOffset(){
        double start = 0;
        double end = this.cr;
        double random = new Random().nextDouble();
        return start + random * (end - start);
    }

    public String combine(double[] hashes){
        return Arrays.toString(hashes);
    }


}
