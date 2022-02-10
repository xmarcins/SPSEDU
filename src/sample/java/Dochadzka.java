package sample.java;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Date;

public class Dochadzka {
    private SimpleIntegerProperty id;
    private SimpleStringProperty ziakMeno;
    private SimpleStringProperty ziakPriezvisko;
    private SimpleStringProperty datum;
    private SimpleStringProperty pritomnost;

    public Dochadzka(int id, String ziakMeno, String ziakPriezvisko, String datum, String pritomnost) {
        this.id = new SimpleIntegerProperty(id);
        this.ziakMeno =new SimpleStringProperty(ziakMeno);
        this.ziakPriezvisko =new SimpleStringProperty(ziakPriezvisko);
        this.datum =new SimpleStringProperty(datum);
        this.pritomnost =new SimpleStringProperty(pritomnost);
    }

    public Dochadzka(int id, String datum) {
        this.id=new SimpleIntegerProperty(id);
        this.datum=new SimpleStringProperty(datum);
    }

    public Dochadzka(String datum, String pritomnost) {
        this.datum=new SimpleStringProperty(datum);
        this.pritomnost=new SimpleStringProperty(pritomnost);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getZiakMeno() {
        return ziakMeno.get();
    }

    public void setZiakMeno(String ziakMeno) {
        this.ziakMeno.set(ziakMeno);
    }

    public String getZiakPriezvisko() {
        return ziakPriezvisko.get();
    }

    public void setZiakPriezvisko(String ziakPriezvisko) {
        this.ziakPriezvisko.set(ziakPriezvisko);
    }

    public String getDatum() {
        return datum.get();
    }

    public void setDatum(String datum) {
        this.datum.set(datum);
    }

    public String getPritomnost() {
        return pritomnost.get();
    }

    public void setPritomnost(String pritomnost) {
        this.pritomnost.set(pritomnost);
    }
}