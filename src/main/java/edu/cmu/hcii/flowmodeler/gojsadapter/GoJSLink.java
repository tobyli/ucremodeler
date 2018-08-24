package edu.cmu.hcii.flowmodeler.gojsadapter;

public class GoJSLink {
    int from;
    int to;
    String color;
    String text;
    String category;


    public GoJSLink(int from, int to, String text, String color){
        this.from = from;
        this.to = to;
        this.text = text;
        this.color = color;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
