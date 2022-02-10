package sample.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;


public class UcitelController{

    /*POMOCNA PREMENNA KTORA UKLADA UCITELA - HLAVNE JEHO ID*/
    private User ucitel;

    @FXML
    private TableView<Ziak> triedaTableView;

    @FXML
    private TableView<Dochadzka> hodinyTableView;

    @FXML
    private TableView<Ospravedlnenka> ospravedlnenkyTableView;

    @FXML
    private TableColumn<Ziak, String> menoTC, priezviskoTC, rodicTC, pritomnostTC;

    @FXML
    private TableColumn<Dochadzka,String> datumVC, menoVC,priezviskoVC;

    @FXML
    private TableColumn<Ospravedlnenka,String> ziakOC, textOC,datumOC;

    @FXML
    private DatePicker sysDatum;

    /*ODHLASENIE*/
    @FXML
    public void logout(){
        try {
            Stage stage = (Stage) triedaTableView.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/prihlasenie.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setTitle("Prihlasenie");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*VYMESKA DANY DEN DANEMU ZIAKOVI*/
    public void vymeskanieDna(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chýbanie žiaka");
        alert.setHeaderText(null);
        Ziak selected=triedaTableView.getSelectionModel().getSelectedItem();
        if(selected==null){
            alert.setContentText("Nezvolili ste ziaka!");
            alert.showAndWait();
        }
        else {
            alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Chcete tomuto ziakovi nastavit chybanie?");
            Optional<ButtonType>answer=alert.showAndWait();
            if (answer.get()==ButtonType.OK){
                DBConnector conn=new DBConnector();
                if (conn.ucitelKontrola(selected.getId(), String.valueOf(LocalDate.now()))){
                conn.ucitelTriedaVymeskanie(selected.getId(), String.valueOf(LocalDate.now()));
                alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Chybanie");
                alert.setContentText("Chybanie prebehlo uspesne!");
                alert.showAndWait();
                hodinyTableView.setItems(conn.selectUcitelDochadzka(ucitel.getId()));}
                else{
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Chyba");
                    alert.setContentText("Tento ziak uz ma chybanie!");
                    alert.showAndWait();
                }
            }
        }
    }

    /*OSPRAVEDLNI DEN DANEMU ZIAKOVI*/
    public void ospravedlnDen() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ospravedlnenie!");
        alert.setHeaderText(null);
        Ospravedlnenka selected = ospravedlnenkyTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert.setContentText("Nezvolili ste ospravedlnenku");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Chystas sa ospravedlnit ziaka: " + selected.getZiak() + " z dna: " + selected.getDatum());
            // alert is exited, no button has been pressed.
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                DBConnector conn = new DBConnector();
                conn.ucitelTriedaOspravedlnenie(conn.getZiakIdByName(selected.getZiak()), selected.getDatum());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Ziak ospravedlneny, ospravedlnenka sa odstrani");
                conn.delete(selected.getId(), "ospravedlnenka");
                ospravedlnenkyTableView.setItems(conn.selectUcitelOspravedlnenky(ucitel.getId()));
                hodinyTableView.setItems(conn.selectUcitelDochadzka(ucitel.getId()));
            } else if (answer.get() == ButtonType.CANCEL) {
                alert.close();
            }
        }
    }

    /*POMOCNA - SETNE UDAJE UCITELA PRI PRECHODE NA JEHO SCENU*/
    public void setUcitel(User ucitel) {
        sysDatum.setValue(LocalDate.now());
        this.ucitel=ucitel;

        DBConnector conn = new DBConnector();

        /*TRIEDA*/
        menoTC.setCellValueFactory(new PropertyValueFactory<>("meno"));
        priezviskoTC.setCellValueFactory(new PropertyValueFactory<>("priezvisko"));
        rodicTC.setCellValueFactory(new PropertyValueFactory<>("rodic"));
        pritomnostTC.setCellValueFactory(new PropertyValueFactory<>("pritomnost"));
        triedaTableView.setItems(conn.selectUcitelTrieda(ucitel.getUsername()));

        /*VYMESKANE HODINY TRIEDY*/
        menoVC.setCellValueFactory(new PropertyValueFactory<>("ziakMeno"));
        priezviskoVC.setCellValueFactory(new PropertyValueFactory<>("ziakPriezvisko"));
        datumVC.setCellValueFactory(new PropertyValueFactory<>("datum"));
        hodinyTableView.setItems(conn.selectUcitelDochadzka(ucitel.getId()));

        /*OSPRAVEDLNENKY TRIEDY*/
        ziakOC.setCellValueFactory(new PropertyValueFactory<>("ziak"));
        textOC.setCellValueFactory(new PropertyValueFactory<>("text"));
        datumOC.setCellValueFactory(new PropertyValueFactory<>("datum"));
        ospravedlnenkyTableView.setItems(conn.selectUcitelOspravedlnenky(ucitel.getId()));
    }
}
