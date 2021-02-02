package dsna19.particle036;

import java.util.Random;

public class RandGen {
    private static Random   mRandom;
    private static long     mSeed;

    static {
        mSeed = System.currentTimeMillis();
        mRandom = new Random(mSeed);
    }

    private RandGen(){}
    public static double uniform() {
        return mRandom.nextDouble();
    }
    public static int uniform(int n) {

        return mRandom.nextInt(n);
    }

    public static double uniform (double a, double b) {

        return  a + (uniform() * (b - a));
    }
}
