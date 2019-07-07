class E2HashFunction {
    private double[] r;
    private double b;
    private double w;

    E2HashFunction(double[] r, double b, double w){ //checked
        this.r= r;
        this.b = b;
        this.w =w;
    }

    int hash(double[] vectorToHash){ //checked

        return (int)Math.round((UsableFunctions.dotProduct(vectorToHash,this.r)+this.b)/this.w);
    }

}
