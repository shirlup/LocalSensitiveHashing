class UsableFunctions {
    static double dotProduct(double[] u, double[] v) //Checked
    {
        double product = 0;

        // Loop for calculate dot product
        for (int i = 0; i < u.length; i++)
            product = product + u[i] * v[i];
        return product;
    }

    static double norm2(double[] u, double[] v){  //checked
        double sum=0;
        for (int i=0; i < u.length ; i++){
            sum+= Math.pow(u[i]-v[i],2);
        }
        return Math.sqrt(sum);
    }
}
