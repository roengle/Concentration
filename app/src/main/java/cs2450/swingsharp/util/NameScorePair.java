package cs2450.swingsharp.util;

/**
 * A class to store a username-score pair that implements the comparable interface. By implementing
 * Comparable, we can designate how to sort items of this type. For this example, we want to sort by
 * scores.
 */
public class NameScorePair implements Comparable<NameScorePair>{
    //Instance fields
    private String name;
    private int score;

    /**
     * Constructs a NameScorePair object given a name and a score
     *
     * @param name the name
     * @param score the score
     */
    public NameScorePair(String name, int score){
        this.name = name;
        this.score = score;
    }

    /**
     * Returns the name of the name-score pair.
     *
     * @return the name
     */
    public String getName(){ return this.name; }

    /**
     * Returns the score of the name-score pair
     * @return
     */
    public int getScore(){return this.score; }

    /**
     * Override for the compareTo method, as this class implements the Comparable interface.
     *
     * @param o the other NameScorePair this pair is being compared to
     * @return 1 if this score is less than the score being compared to, -1 if greater, 0 if equal
     */
    @Override
    public int compareTo(NameScorePair o) {
        if(this.score < o.getScore())
            return 1;
        else if(this.score > o.getScore())
            return -1;
        return 0;
    }

    /**
     * A toString for the NameScorePair class. Gives the string as "Name-Score"
     *
     * @return the name followed by the score, with a '-' in between.
     */
    @Override
    public String toString(){
        return String.format("%s-%s", this.name, this.score);
    }
}
