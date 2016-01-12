//Ariana Aimani 260501657


import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        int maxval = (1 << size) - 1;
        return (int)(Math.random()*maxval);
    }
    
    public static int[] naive(int size, int x, int y) {
        if (size == 1) {
            return new int[] {x*y,1}; //all we're doing is making a new integer array that holds the values of x*y and 1
        }

        else {
            int m = (int) Math.ceil(size/2.0);

            int xl = x >> m;  //bitshifts to the left
            int xr = x - (xl << m); //bitshifts to the right
            int yl = y >> m;
            int yr = y - (yl << m);

            int [] a1 = naive(m,xl,yl);
            int [] a2 = naive(m,xr,yr);
            int [] a3 = naive(m,xr,yl);
            int [] a4 = naive(m,xl,yr);

            int naivefunction = (int) (Math.pow(2,2*m)*a1[0] + Math.pow(2,m)*(a4[0] + a3[0])  + a2[0]); //calculating the actual number in naive.
            int runtime = (a1[1] + a2[1] + a3[1] + a4[1] + 3*m);  //getting the runtime

            return new int[] {naivefunction, runtime};
        }
        
    }

    public static int[] karatsuba(int size, int x, int y) {
        
        if (size == 1) {
            return new int [] {x*y,1};
        }

        else {
            int m1 = (int) Math.ceil(size/2.0);

            int kxl = x >> m1;
            int kxr = x - (kxl << m1);
            int kyl = y >> m1;
            int kyr = y - (kyl << m1);
            int k1 = kxl-kxr;
            int k2 = kyl-kyr;

            int [] e1 = karatsuba(m1,kxl,kyl);
            int [] f1 = karatsuba(m1,kxr,kyr);
            int [] g1 = karatsuba(m1,k1,k2);



            int kfunction =  (int)(Math.pow(2,2*m1)*e1[0] + Math.pow(2,m1)*(e1[0] + f1[0] - g1[0])  + f1[0]);
            int kruntime =   (e1[1] + f1[1] + g1[1] + 6*m1);

            return new int[] {kfunction, kruntime};
        }

        
    }
    
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
