import java.math.BigInteger;

/**
 * PDrop.
 */
public class PDrop {
  private BigFraction zero = new BigFraction(0, 1);
  private BigFraction one = new BigFraction(1, 1);

  public static void main(String args[]) {
    new PDrop();
  }

  public PDrop() {
    for(int c = 1; c < 20; c++) {
      System.out.println("checking: c = " + c);
      for(int s = 0; s <= c/2; s++) {
        for(int k = 0; k < s; k++) {
          BigFraction pdrop = PDrop_K(c, s, k);
          BigFraction pdrop_simple = PDrop_K_simplified(c, s, k);
          if(!pdrop.equals(pdrop_simple)) {
            System.out.println("error: c = " + c + ", s = " + s + ", k = " + k);
            System.out.println("  " + pdrop + " != " + pdrop_simple);
          }
        }
      }
    }
  }

  private BigFraction big(long l) {
    return new BigFraction(l, 1);
  }

  /**
   * Fraction.
   */
  private BigFraction frac(long a, long b) {
    return new BigFraction(a, b);
  }

  /**
   * Binomial.
   */
  private BigFraction bin(long a, long b) {
    // optimization
    if(b < a - b) b = a - b;
    return new BigFraction(fac_(b+1,a), fac_(2, a-b));
  }

  /**
   * Special factorial from * ... * to.
   */
  private BigInteger fac_(long from, long to) {
    BigInteger result = new BigInteger("1");
    for(long i = from; i <= to; i++) result = result.multiply(new BigInteger(Long.toString(i)));
    return result;
  }

  private BigFraction PDrop_K(int s, int c, int k) {
    BigFraction result = new BigFraction(0, 1);
    for(int shat = 0; shat <= k; shat++) {
      result = result.add(
              frac(s-k,s-shat)
                      .multiply(bin(s,shat))
                      .multiply(bin(c-s,k-shat))
                      .divide(bin(c,k))
      );
    }
    return result;
  }

  private BigFraction PDrop_K_simplified(int s, int c, int k) {
    BigFraction h = big(s).multiply(c - s).multiply(bin(c,k+1));
    return one.divide(bin(c,k)).multiply(
            bin(c,k)
                    .add(h)
                    .subtract(h.multiply(bin(s-1,k+1)))
    );
  }
}
