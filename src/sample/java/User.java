package sample.java;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.codec.digest.DigestUtils;

public class User {

    private SimpleIntegerProperty id;
    private SimpleStringProperty meno;
    private SimpleStringProperty priezvisko;
    private SimpleStringProperty username;
    private SimpleStringProperty heslo;
    private SimpleStringProperty userID;
    private SimpleStringProperty rola;

    public User(int id, String meno,String priezvisko, String username, String heslo, String userID, String rola) {
        this.id = new SimpleIntegerProperty(id);
        this.meno =new SimpleStringProperty(meno);
        this.priezvisko =new SimpleStringProperty(priezvisko);
        this.username =new SimpleStringProperty(username);
        this.heslo =new SimpleStringProperty(heslo);
        this.userID =new SimpleStringProperty(userID);
        this.rola =new SimpleStringProperty(rola);
    }

    public User(int id, String username, String heslo, String rola) {
        this.id = new SimpleIntegerProperty(id);
        this.username =new SimpleStringProperty(username);
        this.heslo =new SimpleStringProperty(heslo);
        this.rola =new SimpleStringProperty(rola);
    }

    public User(String meno, String priezvisko){
        this.meno = new SimpleStringProperty(meno);
        this.priezvisko = new SimpleStringProperty(priezvisko);
    }
    public User(int id,String meno,String priezvisko){
        this.id=new SimpleIntegerProperty(id);
        this.meno=new SimpleStringProperty(meno);
        this.priezvisko=new SimpleStringProperty(priezvisko);
    }

    public int getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getMeno() {
        return meno.get();
    }

    public void setMeno(String meno) {
        this.meno.set(meno);
    }

    public String getPriezvisko() {
        return priezvisko.get();
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko.set(priezvisko);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getHeslo() {
        return heslo.get();
    }

    public void setHeslo(String heslo) {
        this.heslo.set(heslo);
    }

    public String getRola() {
        return rola.get();
    }

    public void setRola(String rola) {
        this.rola.set(rola);
    }

    public String getUserID() {
        return userID.get();
    }

    public void setUserID(String userID) {
        this.userID.set(userID);
    }

}

