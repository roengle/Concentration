package cs2450.swingsharp;

public class Card {
    String name;        //Name of the Card
    boolean faceUp;     //Determines if face is up or not
    boolean matching;   //Determines if card has been matched (maybe unused)

    public Card(){}

    public Card(String name, boolean faceUp){
        this.name = name;
        this.faceUp = faceUp;
    }

    public Card(String name){
        this.name = name;
        faceUp = false;
    }

    public String getName(){
        return name;
    }

    public boolean getFaceUp(){
        return faceUp;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setFaceUp(boolean faceUp){
        this.faceUp = faceUp;
    }

}
