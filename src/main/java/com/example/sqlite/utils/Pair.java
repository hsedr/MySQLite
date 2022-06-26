package com.example.sqlite.utils;

public class Pair<T,S>{
    T a;
    S b;

    public Pair(T a, S b){
        this.a = a;
        this.b = b;
    }

    /**
     * Returns first value of this Pair.
     *
     * @return first vlaue
     */
    public T getFirst(){
        return a;
    }

    /**
     * Returns second value of this Pair.
     *
     * @return second vlaue
     */
    public S getSecond(){
        return b;
    }

    /**
     * Pair as a String.
     *
     * @return <a, b>
     */
    @Override
    public String toString(){
        return "<" + a.toString() + ", " + b.toString() + ">";
    }

    /**
     * Pairs equal if there first and second values are equal.
     *
     * @param o Object that's compared to this.
     * @return true if both Objects are equal, else false
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Pair p) {
            return p.getFirst().equals(this.a) && p.getSecond().equals(this.b);
        }
        return false;
    }

    /**
     * The Hash Code of a Pair is the Hash Code of the concatenation of its
     * first and second value.
     *
     * @return Hash Code of this.
     */
    @Override
    public int hashCode(){
        String value = this.a.toString() + this.b.toString();
        return value.hashCode();
    }
}
