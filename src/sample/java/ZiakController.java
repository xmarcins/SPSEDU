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

public class ZiakController {

    /*POMOCNA PREMENNA - UKLADA ZIAKA - HLAVNE ID*/
    private User ziak;

    @FXML
    private TableView<Dochadzka> dochadzkaTableView;

    @FXML
    private TableColumn<Dochadzka,String> datumZ,pritomnostZ;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ToggleButton toggle;

    /*ODHLASENIE*/
    @FXML
    public void logout(){
        try {
            Stage stage = (Stage) dochadzkaTableView.getScene().getWindow();
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

    /*POTVRDI ZMENU STAVU Z PRITOMNY NA NEPRITOMNY A NAOPAK*/
    @FXML
    public void potvrdStav(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Prítomnosť");
        alert.setContentText("Naozaj chceš potvrdiť svoj stav?");
        Optional<ButtonType> answer = alert.showAndWait();
        if(answer.get() == ButtonType.OK){
            DBConnector conn = new DBConnector();
            conn.ziakPrichodOdchod(ziak.getId(), (toggle.isSelected() == true ? "pritomny" : "nepritomny"));
        }else if (answer.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    /*POMOCNA - SETNE ZIAKA PRI PRECHODE NA JEHO SCENU*/
    public void setZiak(User ziak){
        this.ziak=ziak;
        datePicker.setValue(LocalDate.now());
        DBConnector conn =new DBConnector();
        datumZ.setCellValueFactory(new PropertyValueFactory<>("datum"));
        pritomnostZ.setCellValueFactory(new PropertyValueFactory<>("pritomnost"));
        dochadzkaTableView.setItems(conn.selectZiakDochadzka(ziak.getId()));
        toggle.setSelected(conn.getToggleStav(ziak.getId()));
    }

}
