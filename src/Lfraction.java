import java.util.*;

/** This class represents fractions of form n/d where n and d are long integer
 * numbers. Basic operations and arithmetics for fractions are provided.
 */


/**
 * Hashcode:
 * https://javarevisited.blogspot.com/2011/10/override-hashcode-in-java-example.html
 */
public class Lfraction implements Comparable<Lfraction> {

   /** Main method. Different tests. */
   public static void main (String[] param) {

   }


   private long numerator;
   private long denominator;

   /** Constructor.
    * @param a numerator
    * @param b denominator > 0
    */
   public Lfraction(long a, long b) {
      if (b == 0) {
         throw new RuntimeException("Denominator cannot be zero.");
      }

      if (b < 0) {
         a = -a;
         b = -b;
      }

      long commonDivisor = commonDivisor(Math.abs(a), b);
      this.numerator = a / commonDivisor;
      this.denominator = b / commonDivisor;
   }

   /** Public method to access the numerator field.
    * @return numerator
    */
   public long getNumerator() {
      return numerator;
   }

   /** Public method to access the denominator field.
    * @return denominator
    */
   public long getDenominator() {
      return denominator;
   }

   /** Conversion to string.
    * @return string representation of the fraction
    */
   @Override
   public String toString() {
      if (denominator == 1){
         return String.valueOf(numerator);
      }
      return numerator + "/" + denominator;
   }

   /** Equality test.
    * @param m second fraction
    * @return true if fractions this and m are equal
    */
   @Override
   public boolean equals (Object m) {
      if (this == m)
         return true;

      if (m == null || getClass() != m.getClass())
         return false;

      Lfraction otherFraction = (Lfraction) m;

      return this.numerator == otherFraction.numerator
              && this.denominator == otherFraction.denominator;
   }

   /** Hashcode has to be the same for equal fractions and in general, different
    * for different fractions.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      int result = 5;
      result = 31 * result + (int)(numerator ^ (numerator >>> 32)); // multiply by another prime and add hash of numerator
      result = 31 * result + (int)(denominator ^ (denominator >>> 32)); // multiply by another prime and add hash of denominator
      return result;
   }

   /** Sum of fractions.
    * @param m second addend
    * @return this+m
    */
   public Lfraction plus (Lfraction m) {
      long smallestCommonDivisor = denominator * m.getDenominator() / commonDivisor(denominator, m.getDenominator());
      long newNumerator = (numerator * (smallestCommonDivisor / denominator)) + (m.getNumerator() * (smallestCommonDivisor / m.getDenominator()));
      return new Lfraction(newNumerator, smallestCommonDivisor);
   }

   /** Multiplication of fractions.
    * @param m second factor
    * @return this*m
    */
   public Lfraction times (Lfraction m) {
      long newNominator = numerator * m.getNumerator();
      long newDenominator = denominator * m.getDenominator();
      return new Lfraction(newNominator, newDenominator);
   }

   /** Inverse of the fraction. n/d becomes d/n.
    * @return inverse of this fraction: 1/this
    */
   public Lfraction inverse() {
      if (numerator == 0){
         throw new RuntimeException("Can't inverse a 0 denominator!");
      }

      return new Lfraction(denominator, numerator);
   }

   /** Opposite of the fraction. n/d becomes -n/d.
    * @return opposite of this fraction: -this
    */
   public Lfraction opposite() {
      return new Lfraction(-numerator, denominator);
   }

   /** Difference of fractions.
    * @param m subtrahend
    * @return this-m
    */
   public Lfraction minus (Lfraction m) {
      long smallestCommonDivisor = denominator * m.getDenominator() / commonDivisor(denominator, m.getDenominator());
      long newNumerator = (numerator * (smallestCommonDivisor / denominator)) - (m.getNumerator() * (smallestCommonDivisor / m.getDenominator()));
      return new Lfraction(newNumerator, smallestCommonDivisor);
   }

   /** Quotient of fractions.
    * @param m divisor
    * @return this/m
    */
   public Lfraction divideBy (Lfraction m) {
      if (m.numerator == 0){
         throw new ArithmeticException("Division by zero in fraction.");
      }
      Lfraction inversed = m.inverse();
      long newNumerator = numerator * inversed.getNumerator();
      long newDenominator = denominator * inversed.getDenominator();
      return new Lfraction(newNumerator, newDenominator);
   }

   /** Comparision of fractions.
    * @param m second fraction
    * @return -1 if this < m; 0 if this==m; 1 if this > m
    */
   @Override
   public int compareTo(Lfraction m) {
      long crossProduct1 = numerator * m.denominator;
      long crossProduct2 = m.numerator * denominator;

      if (crossProduct1 < crossProduct2) {
         return -1;
      } else if (crossProduct1 == crossProduct2) {
         return 0;
      } else {
         return 1;
      }
   }

   /** Clone of the fraction.
    * @return new fraction equal to this
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      return new Lfraction(numerator, denominator);
   }

   /** Integer part of the (improper) fraction.
    * @return integer part of this fraction
    */
   public long integerPart() {
      return numerator/denominator;
   }

   /** Extract fraction part of the (improper) fraction
    * (a proper fraction without the integer part).
    * @return fraction part of this fraction
    */
   public Lfraction fractionPart() {
      long overload = this.numerator % this.denominator;
      return new Lfraction(overload, denominator);
   }

   /** Approximate value of the fraction.
    * @return real value of this fraction
    */
   public double toDouble() {
      return (double) numerator / denominator;
   }

   /** Double value f presented as a fraction with denominator d > 0.
    * @param f real number
    * @param d positive denominator for the result
    * @return f as an approximate fraction of form n/d
    */
   public static Lfraction toLfraction (double f, long d) {
      if (d <= 0) {
         throw new IllegalArgumentException("Denominator must be d > 0");
      }
      long n = Math.round(f * d);
      return new Lfraction(n, d);
   }

   /** Conversion from string to the fraction. Accepts strings of form
    * that is defined by the toString method.
    * @param s string form (as produced by toString) of the fraction
    * @return fraction represented by s
    */
   public static Lfraction valueOf (String s) {
      if(s == null || s.isEmpty()){
         throw new RuntimeException("Invalid input: " + s);
      }

      if (!s.contains("/")){
         return new Lfraction(Long.parseLong(s), 1);
      }

      String[] values = s.split("/");
      System.out.println(values.length);

      if (values.length != 2 || s.endsWith("/")) {
         throw new RuntimeException("Invalid input: " + s);
      }

      try {
         long num = Long.parseLong(values[0]);
         long denom = Long.parseLong(values[1]);
         return new Lfraction(num, denom);
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException("Invalid number format in fraction.", e);
      }
   }

   private static long commonDivisor(long a, long b) {
      while (b != 0) {
         long temp = a % b;
         a = b;
         b = temp;
      }
      return a;
   }
}