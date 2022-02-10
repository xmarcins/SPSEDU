package sample.java;

import javafx.beans.property.SimpleStringProperty;

public class Ucitel extends User{

    private SimpleStringProperty trieda;

    public Ucitel(int id, String meno, String priezvisko, String username, String heslo, String userID, String rola, String trieda) {
        super(id, meno, priezvisko, username, heslo, userID, rola);
        this.trieda = new SimpleStringProperty(trieda);
    }

    public String getTrieda() {
        return trieda.get();
    }

    public void setTrieda(String trieda) {
        this.trieda.set(trieda);
    }
}
