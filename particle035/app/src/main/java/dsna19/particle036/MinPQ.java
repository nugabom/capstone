package dsna19.particle036;

import java.util.NoSuchElementException;

public class MinPQ<Key> {
    private Key[]   mPQ;
    private int     mN;

    public MinPQ(int capacity){
        mPQ = (Key[]) new Object[capacity + 1];
        mN = 0;
    }

    public MinPQ(){this(1);}
    public boolean isEmpty() { return mN == 0; }
    public int size() { return mN; }
    private void resize(int capacity) {
        Key[] temp = (Key[]) new Object[capacity + 1];
        for (int i = 1; i <= mN; i++)
            temp[i] = mPQ[i];
        mPQ = temp;
    }

    public void insert(Key x) {
        if(mN == mPQ.length - 1) resize(2*mPQ.length);
        mPQ[++mN] = x;
        swim(mN);
    }

    public Key delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        exch(1, mN);
        Key min = mPQ[mN--];
        sink(1);
        mPQ[mN + 1] = null;
        if((mN > 0) && (mN == (mPQ.length - 1)/4)) resize(mPQ.length/2);
        return min;
    }

    private void swim (int k){
        while (k > 1 && greater(k/2, k)) {
            exch (k/2, k);
            k = k/2;
        }
    }

    private void sink(int k){
        while (2 * k <= mN) {
            int j = 2 * k;
            if (j < mN && greater(j, j + 1)) j++;
            if (!greater(k, j)) break;
            exch (k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j){
        return ((Comparable<Key>) mPQ[i]).compareTo(mPQ[j]) > 0;
    }

    private void exch(int i, int j){
        Key temp  = mPQ[i];
        mPQ[i] = mPQ[j];
        mPQ[j] = temp;
    }
}
