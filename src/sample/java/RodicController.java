package sample.java;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class RodicController {

    /*POMOCNA PREMENNA KTORA UKLADA RODICA - HLAVNE ID*/
    private User rodic;

    @FXML
    private TableView<Dochadzka> dochadzkaRTableView;

    @FXML
    private TableColumn<Dochadzka,String> datumR,pritomnostR;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<User> table;

    @FXML
    private JFXTextArea ospravedlnenka;

    @FXML
    private JFXButton odoslat;

    @FXML
    private Dochadzka den;

    /*ODHLASENIE*/
    @FXML
    public void logout(){
        try {
            Stage stage = (Stage) dochadzkaRTableView.getScene().getWindow();
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

    /*OSPRAVEDLNI DANY DEN*/
    @FXML
    public void ospravedln(){
        if(ospravedlnenka.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nezadali ste text ospravedlnenky!");
            alert.showAndWait();
        } else {
            DBConnector conn = new DBConnector();
            conn.rodicVytvorOspravedlnenku(conn.getZiakIDByRodicID(rodic.getId()), ospravedlnenka.getText(), den.getDatum());
            ospravedlnenka.setText("");
            odoslat.setDisable(true);
        }
    }

    /*POMOCNA - VYKONA SA PRED OSPRAVEDLNENIM, ULOZI SELECTNUTY DEN DANEJ OSPRAVEDLNENKY*/
    @FXML
    public void predOspravedlnenim(){
        Dochadzka selected = dochadzkaRTableView.getSelectionModel().getSelectedItem();
        DBConnector conn = new DBConnector();
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if(selected == null){
            alert.setContentText("Nezvolili ste den na ucitelTriedaOspravedlnenie!");
            alert.showAndWait();
        }else if(conn.uzJeOspravedlnene(conn.getZiakIDByRodicID(rodic.getId()), selected.getDatum())){
            alert.setContentText("Dany den uz je ospravedlneny");
            alert.showAndWait();
        } else {
            odoslat.setDisable(false);
            den = selected;
        }
    }

    /*POMOCNA - VYKONA SA PRI PREPNUTI SCENY NA RODICA A SETNE POTREBNE UDAJE*/
    public void setRodic(User rodic){
        odoslat.setDisable(true);
        this.rodic=rodic;
        datePicker.setValue(LocalDate.now());
        DBConnector conn =new DBConnector();
        datumR.setCellValueFactory(new PropertyValueFactory<>("datum"));
        pritomnostR.setCellValueFactory(new PropertyValueFactory<>("pritomnost"));
        dochadzkaRTableView.setItems(conn.selectRodicDochadzka(rodic.getId()));
    }
}
