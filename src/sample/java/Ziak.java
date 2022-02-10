package sample.java;

import javafx.beans.property.SimpleStringProperty;

public class Ziak extends User{

    private SimpleStringProperty pritomnost;
    private SimpleStringProperty rodic;
    private SimpleStringProperty trieda;
    private SimpleStringProperty ucitel;

    public Ziak(int id, String meno, String priezvisko, String username, String heslo, String userID, String rola, String pritomnost, String rodic, String trieda, String ucitel) {
        super(id, meno, priezvisko, username, heslo, userID, rola);
        this.pritomnost = new SimpleStringProperty(pritomnost);
        this.rodic = new SimpleStringProperty(rodic);
        this.trieda = new SimpleStringProperty(trieda);
        this.ucitel = new SimpleStringProperty(ucitel);
    }

    public Ziak(int id,String meno, String priezvisko, String rodic, String pritomnost){
        super(id,meno, priezvisko);
        this.rodic = new SimpleStringProperty(rodic);
        this.pritomnost = new SimpleStringProperty(pritomnost);
    }

    public String getRodic() {
        return rodic.get();
    }

    public void setRodic(String rodic) {
        this.rodic.set(rodic);
    }

    public String getTrieda() {
        return trieda.get();
    }

    public void setTrieda(String trieda) {
        this.trieda.set(trieda);
    }

    public String getUcitel() {
        return ucitel.get();
    }

    public void setUcitel(String ucitel) {
        this.ucitel.set(ucitel);
    }

    public String getPritomnost() {
        return pritomnost.get();
    }

    public void setPritomnost(String pritomnost) {
        this.pritomnost.set(pritomnost);
    }


}
