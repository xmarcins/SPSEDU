package sample.java;

import javafx.beans.property.SimpleStringProperty;

public class Trieda {

    private int id;
    private SimpleStringProperty meno;

    public Trieda(int id, String meno) {
        this.id = id;
        this.meno = new SimpleStringProperty(meno);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeno() {
        return meno.get();
    }

    public void setMeno(String meno) {
        this.meno.set(meno);
    }
}
