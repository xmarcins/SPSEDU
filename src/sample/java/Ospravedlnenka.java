package sample.java;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;



public class Ospravedlnenka {
    private SimpleIntegerProperty id;
    private SimpleStringProperty ziak;
    private SimpleStringProperty text;
    private SimpleStringProperty datum;

    public Ospravedlnenka(int id, String ziak, String text, String datum) {
        this.id =new SimpleIntegerProperty(id);
        this.ziak =new SimpleStringProperty(ziak);
        this.text =new SimpleStringProperty(text);
        this.datum =new SimpleStringProperty(datum);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getZiak() {
        return ziak.get();
    }

    public void setZiak(String ziak) {
        this.ziak.set(ziak);
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public String getDatum() {
        return datum.get();
    }

    public void setDatum(String datum) {
        this.datum.set(datum);
    }
}
