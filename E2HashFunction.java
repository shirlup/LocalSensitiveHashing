class E2HashFunction {
  private double[] r;
  private double b;
  private double w;
  
  E2HashFunction(double[] r, double b, double w) {
    this.r = r;
    this.b = b;
    this.w = w;
  }
  
  int hash(double[] vectorToHash)
  {
    return (int)Math.round((UsableFunctions.dotProduct(vectorToHash, r) + b) / w);
  }
}
