package sample.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    /*pomocna premenna pre update*/
    private int id;

    /*ZADEFINOVANIE PREMIEN Z FXML*/
    @FXML
    private TableView<Ziak> ziaciTableView;

    @FXML
    private TableView<Ucitel> uciteliaTableView;

    @FXML
    private TableView<Rodic> rodiciaTableView;

    @FXML
    private TableView<Trieda> triedyTableView;

    @FXML
    private TableColumn<Ziak, String> menoZC, priezviskoZC, usernameZC, hesloZC, userIDZC, rodicZC, triedaZC, ucitelZC
            ,idUC, menoUC, priezviskoUC, usernameUC, hesloUC, userIDUC, triedaUC
            ,idRC, menoRC, priezviskoRC, usernameRC, hesloRC, userIDRC
            ,idTC, menoTC;

    @FXML
    private TextField menoZField, priezviskoZField, usernameZField, userIDZField, rodicZField, triedaZField, ucitelZField
            ,menoUField, priezviskoUField, usernameUField, userIDUField, triedaUField
            ,menoRField, priezviskoRField, usernameRField, userIDRField
            ,menoTField;

    @FXML
    private PasswordField hesloZField, hesloUField, hesloRField;

    @FXML
    private Button addZButton, updateZButton, addUButton, updateUButton, addRButton, updateRButton, addTButton, updateTButton;

    /*FUNKCIA PRE ODHLASENIE*/
    @FXML
    private void Logout(){
        try {
            Stage stage = (Stage) ziaciTableView.getScene().getWindow();
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

    /*PRIDANIE ZIAKA DO DATABAZY*/
    @FXML
    public void AddZiak() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pridanie žiaka!");
        alert.setHeaderText(null);
        if (fieldCheckZiak(alert)) {
                DBConnector conn = new DBConnector();
                conn.insertZiak(menoZField.getText(), priezviskoZField.getText(), usernameZField.getText(),
                        hesloZField.getText(), userIDZField.getText(), Integer.parseInt(rodicZField.getText()),
                        Integer.parseInt(triedaZField.getText()), Integer.parseInt(ucitelZField.getText()));
                ziaciTableView.setItems(conn.selectAllZiak());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Pridanie užívateľa!");
                alert.setContentText("Pridanie prebehlo úspešne!");
                alert.showAndWait();
                fieldClearZiak();
        }
    }

    /*PRIDANIE UCITELA DO DATABAZY*/
    @FXML
    public void AddUcitel() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pridanie učiteľa!");
        alert.setHeaderText(null);
        if (fieldCheckUcitel(alert)) {
            DBConnector conn = new DBConnector();
            conn.insertUcitel(menoUField.getText(), priezviskoUField.getText(), usernameUField.getText(),
                    hesloUField.getText(), userIDUField.getText(), Integer.parseInt(triedaUField.getText()));
            uciteliaTableView.setItems(conn.selectAllUcitel());
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Pridanie učiteľa!");
            alert.setContentText("Pridanie prebehlo úspešne!");
            alert.showAndWait();
            fieldClearUcitel();
        }
    }

    /*PRIDANIE UCITELA DO DATABAZY*/
    @FXML
    public void AddRodic() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Pridanie rodica!");
        alert.setHeaderText(null);
        if (fieldCheckRodic(alert)) {
            DBConnector conn = new DBConnector();
            conn.insertRodic(menoRField.getText(), priezviskoRField.getText(), usernameRField.getText(),
                    hesloRField.getText(), userIDRField.getText());
            rodiciaTableView.setItems(conn.selectAllRodic());
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Pridanie rodica");
            alert.setContentText("Pridanie prebehlo úspešne!");
            alert.showAndWait();
            fieldClearRodic();
        }
    }

    /*PRIDANIE TRIEDY DO DATABAZY*/
    @FXML
    public void AddTrieda() {
        if (menoTField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pridanie triedy!");
            alert.setHeaderText(null);
            alert.setContentText("Nezadal si meno triedy");
            alert.showAndWait();
        }else{
            DBConnector conn = new DBConnector();
            conn.insertTrieda(menoTField.getText());
            triedyTableView.setItems(conn.selectAllTrieda());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Pridanie triedy!");
            alert.setContentText("Pridanie prebehlo úspešne!");
            alert.showAndWait();
            menoTField.setText("");
        }
    }

    /*MAZANIE ZIAKA*/
    @FXML
    private void DeleteZiak(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Odstránenie užívateľa!");
        alert.setHeaderText(null);
        Ziak selected=ziaciTableView.getSelectionModel().getSelectedItem();
        if (selected== null){
            alert.setContentText("Nezvolili ste uzivatela na zmazanie");
            alert.showAndWait();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Chystas sa odstranit uzivatel s id:" + selected.getUserID());
            // alert is exited, no button has been pressed.
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                DBConnector conn = new DBConnector();
                conn.delete(selected.getId(), "ziak");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspesne odstraneny uzivatel");
                ziaciTableView.setItems(conn.selectAllZiak());
            } else if (answer.get() == ButtonType.CANCEL) {
                alert.close();
            }
        }
    }

    /*MAZANIE UCITELA*/
    @FXML
    private void DeleteUcitel(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Odstránenie ucitela!");
        alert.setHeaderText(null);
        Ucitel selected=uciteliaTableView.getSelectionModel().getSelectedItem();
        if (selected== null){
            alert.setContentText("Nezvolili ste ucitela na zmazanie");
            alert.showAndWait();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Chystas sa odstranit ucitela s id:" + selected.getId());
            // alert is exited, no button has been pressed.
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                DBConnector conn = new DBConnector();
                conn.delete(selected.getId(), "ucitel");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspesne odstraneny ucitel");
                uciteliaTableView.setItems(conn.selectAllUcitel());
            } else if (answer.get() == ButtonType.CANCEL) {
                alert.close();
            }
        }
    }

    /*MAZANIE RODICA*/
    @FXML
    private void DeleteRodic(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Odstránenie rodica!");
        alert.setHeaderText(null);
        Rodic selected=rodiciaTableView.getSelectionModel().getSelectedItem();
        if (selected== null){
            alert.setContentText("Nezvolili ste rodica na zmazanie");
            alert.showAndWait();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Chystas sa odstranit rodic s id:" + selected.getId());
            // alert is exited, no button has been pressed.
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                DBConnector conn = new DBConnector();
                conn.delete(selected.getId(), "rodic");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspesne odstraneny rodic");
                rodiciaTableView.setItems(conn.selectAllRodic());
            } else if (answer.get() == ButtonType.CANCEL) {
                alert.close();
            }
        }
    }

    /*MAZANIE TRIEDY*/
    @FXML
    private void DeleteTrieda(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Odstránenie triedy!");
        alert.setHeaderText(null);
        Trieda selected=triedyTableView.getSelectionModel().getSelectedItem();
        if (selected== null){
            alert.setContentText("Nezvolili ste triedu na zmazanie");
            alert.showAndWait();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Chystas sa odstranit triedu s id:" + selected.getId());
            // alert is exited, no button has been pressed.
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                DBConnector conn = new DBConnector();
                conn.delete(selected.getId(), "trieda");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspesne odstranena trieda");
                triedyTableView.setItems(conn.selectAllTrieda());
            } else if (answer.get() == ButtonType.CANCEL) {
                alert.close();
            }
        }
    }

    /*UPDATE ZIAKA*/
    @FXML
    private void UpdateZiak(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Update uzivatela");
        alert.setContentText("Naozaj chcete updatovat ziaka?");
        if (fieldCheckZiak(alert)){
            alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Update ziaka");
            alert.setContentText("Naozaj chcete updatovat ziaka?");
            Optional<ButtonType> answer=alert.showAndWait();
            if (answer.get() == ButtonType.OK){
                DBConnector conn=new DBConnector();
                conn.updateZiak(id, menoZField.getText(), priezviskoZField.getText(), usernameZField.getText(),
                        hesloZField.getText(), userIDZField.getText(), rodicZField.getText(), triedaZField.getText(),
                        ucitelZField.getText());
                alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspesne updatovany uzivatel");
                alert.showAndWait();
                fieldClearZiak();
                addZButton.setDisable(false);
                updateZButton.setDisable(true);
                ziaciTableView.setItems(conn.selectAllZiak());
            }
            else if (answer.get()==ButtonType.CANCEL){
                alert.close();
                fieldClearZiak();
                addZButton.setDisable(false);
                updateZButton.setDisable(true);
            }
        }
    }

    /*UPDATE UCITELA*/
    @FXML
    private void UpdateUcitel(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Update ucitela");
        alert.setContentText("Naozaj chcete updatovat ucitela?");
        if (fieldCheckUcitel(alert)){
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Update ucitela");
            alert.setContentText("Naozaj chcete updatovat ucitela?");
            Optional<ButtonType> answer=alert.showAndWait();
            if (answer.get() == ButtonType.OK){
                DBConnector conn=new DBConnector();
                conn.updateUcitel(id, menoUField.getText(), priezviskoUField.getText(), usernameUField.getText(),
                        hesloUField.getText(), userIDUField.getText(), triedaUField.getText());
                alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspesne updatovany ucitel");
                alert.showAndWait();
                fieldClearUcitel();
                addUButton.setDisable(false);
                updateUButton.setDisable(true);
                uciteliaTableView.setItems(conn.selectAllUcitel());
                ziaciTableView.setItems(conn.selectAllZiak());
            }
            else if (answer.get()==ButtonType.CANCEL){
                alert.close();
                fieldClearUcitel();
                addUButton.setDisable(false);
                updateUButton.setDisable(true);
            }
        }
    }

    /*UPDATE RODICA*/
    @FXML
    private void UpdateRodic(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Update rodica");
        if (fieldCheckRodic(alert)){
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Update rodica");
            alert.setContentText("Naozaj chcete updatovat rodica?");
            Optional<ButtonType> answer=alert.showAndWait();
            if (answer.get() == ButtonType.OK){
                DBConnector conn=new DBConnector();
                conn.updateRodic(id, menoRField.getText(), priezviskoRField.getText(), usernameRField.getText(),
                        hesloRField.getText(), userIDRField.getText());
                alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspesne updatovany rodic");
                alert.showAndWait();
                fieldClearRodic();
                addRButton.setDisable(false);
                updateRButton.setDisable(true);
                rodiciaTableView.setItems(conn.selectAllRodic());
                ziaciTableView.setItems(conn.selectAllZiak());
            }
            else if (answer.get()==ButtonType.CANCEL){
                alert.close();
                fieldClearRodic();
                addRButton.setDisable(false);
                updateRButton.setDisable(true);
            }
        }
    }

    /*UPDATE TRIEDY*/
    @FXML
    private void UpdateTrieda(){
        if (menoTField.getText().isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Update triedy");
            alert.setContentText("Nezadali ste meno !");
            alert.showAndWait();
        }else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Update triedy");
            alert.setContentText("Naozaj chcete udpatovat triedu?");
            Optional<ButtonType> answer=alert.showAndWait();
            if (answer.get() == ButtonType.OK){
                DBConnector conn=new DBConnector();
                conn.updateTrieda(id, menoTField.getText());
                alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspesne updatovana trieda");
                alert.showAndWait();
                menoTField.setText("");
                addTButton.setDisable(false);
                updateTButton.setDisable(true);
                triedyTableView.setItems(conn.selectAllTrieda());
                ziaciTableView.setItems(conn.selectAllZiak());
                uciteliaTableView.setItems(conn.selectAllUcitel());
            }
            else if (answer.get()==ButtonType.CANCEL){
                alert.close();
                menoTField.setText("");
                addTButton.setDisable(false);
                updateTButton.setDisable(true);
            }
        }
    }

    /*POMOCNA - VYPLNI POLIA PRED UPDATOM ZIAKA*/
    @FXML
    private void BeforeUpdateZiak(ActionEvent event){
        Ziak selected=ziaciTableView.getSelectionModel().getSelectedItem();
        if (selected==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nezvolili ste uzivatela na updatovanie");
            alert.showAndWait();
        }
        else {
            DBConnector conn = new DBConnector();
            id = selected.getId();
            addZButton.setDisable(true);
            menoZField.setText(selected.getMeno());
            priezviskoZField.setText(selected.getPriezvisko());
            usernameZField.setText(selected.getUsername());
            hesloZField.setText(selected.getHeslo());
            userIDZField.setText(selected.getUserID());
            rodicZField.setText(String.valueOf(conn.getRodicIDByName(selected.getRodic())));
            triedaZField.setText(String.valueOf(conn.getTriedaIDByName(selected.getTrieda())));
            ucitelZField.setText(String.valueOf(conn.getUcitelIDByName(selected.getUcitel())));
            updateZButton.setDisable(false);
        }
    }

    /*POMOCNA - VYPLNI POLIA PRED UPDATOM UCITELA*/
    @FXML
    private void BeforeUpdateUcitel(){
        Ucitel selected=uciteliaTableView.getSelectionModel().getSelectedItem();
        if (selected==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nezvolili ste ucitela na updatovanie");
            alert.showAndWait();
        } else {
            DBConnector conn = new DBConnector();
            id = selected.getId();
            addUButton.setDisable(true);
            menoUField.setText(selected.getMeno());
            priezviskoUField.setText(selected.getPriezvisko());
            usernameUField.setText(selected.getUsername());
            hesloUField.setText(selected.getHeslo());
            userIDUField.setText(selected.getUserID());
            triedaUField.setText(String.valueOf(conn.getTriedaIDByName(selected.getTrieda())));
            updateUButton.setDisable(false);
        }
    }

    /*POMOCNA - VYPLNI POLIA PRED UPDATOM RODICA*/
    @FXML
    private void BeforeUpdateRodic(){
        Rodic selected=rodiciaTableView.getSelectionModel().getSelectedItem();
        if (selected==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nezvolili ste rodica na updatovanie");
            alert.showAndWait();
        }
        else {
            id = selected.getId();
            addRButton.setDisable(true);
            menoRField.setText(selected.getMeno());
            priezviskoRField.setText(selected.getPriezvisko());
            usernameRField.setText(selected.getUsername());
            hesloRField.setText(selected.getHeslo());
            userIDRField.setText(selected.getUserID());
            updateRButton.setDisable(false);
        }
    }

    /*POMOCNA - VYPLNI POLIA PRED UPDATOM TRIEDY*/
    @FXML
    private void BeforeUpdateTrieda(){
        Trieda selected=triedyTableView.getSelectionModel().getSelectedItem();
        if (selected==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nezvolili ste triedu na updatovanie");
            alert.showAndWait();
        }
        else {
            id = selected.getId();
            addTButton.setDisable(true);
            menoTField.setText(selected.getMeno());
            updateTButton.setDisable(false);
        }
    }

    /*KONTROLA CI SU ZADANE VSETKY POLIA PRI VYTVARANI A UPDATE ZIAKA*/
    private boolean fieldCheckZiak(Alert alert) {
        if (menoZField.getText().isEmpty()) {
            alert.setContentText("Nezadal si meno!");
            alert.showAndWait();
            return false;
        } else if (priezviskoZField.getText().isEmpty()) {
            alert.setContentText("Nezadal si priezvisko!");
            alert.showAndWait();
            return false;
        } else if (usernameZField.getText().isEmpty()) {
            alert.setContentText("Nezadal si username!");
            alert.showAndWait();
            return false;
        } else if (hesloZField.getText().isEmpty()) {
            alert.setContentText("Nezadal si heslo!");
            alert.showAndWait();
            return false;
        } else if (userIDZField.getText().isEmpty()) {
            alert.setContentText("Nezadal si user ID!");
            alert.showAndWait();
            return false;
        } else if (rodicZField.getText().isEmpty()) {
            alert.setContentText("Nezadal si rodica!");
            alert.showAndWait();
            return false;
        } else if (triedaZField.getText().isEmpty()) {
            alert.setContentText("Nezadal si triedu!");
            alert.showAndWait();
            return false;
        } else if (ucitelZField.getText().isEmpty()) {
            alert.setContentText("Nezadal si ucitela!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /*KONTROLA CI SU ZADANE VSETKY POLIA PRI VYTVARANI A UPDATE UCITELA*/
    private boolean fieldCheckUcitel(Alert alert) {
        if (menoUField.getText().isEmpty()) {
            alert.setContentText("Nezadal si meno!");
            alert.showAndWait();
            return false;
        } else if (priezviskoUField.getText().isEmpty()) {
            alert.setContentText("Nezadal si priezvisko!");
            alert.showAndWait();
            return false;
        } else if (usernameUField.getText().isEmpty()) {
            alert.setContentText("Nezadal si username!");
            alert.showAndWait();
            return false;
        } else if (hesloUField.getText().isEmpty()) {
            alert.setContentText("Nezadal si heslo!");
            alert.showAndWait();
            return false;
        } else if (userIDUField.getText().isEmpty()) {
            alert.setContentText("Nezadal si user ID!");
            alert.showAndWait();
            return false;
        }else if (triedaUField.getText().isEmpty()) {
            alert.setContentText("Nezadal si triedu!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /*KONTROLA CI SU ZADANE VSETKY POLIA PRI VYTVARANI A UPDATE RODICA*/
    private boolean fieldCheckRodic(Alert alert) {
        if (menoRField.getText().isEmpty()) {
            alert.setContentText("Nezadal si meno!");
            alert.showAndWait();
            return false;
        } else if (priezviskoRField.getText().isEmpty()) {
            alert.setContentText("Nezadal si priezvisko!");
            alert.showAndWait();
            return false;
        } else if (usernameRField.getText().isEmpty()) {
            alert.setContentText("Nezadal si username!");
            alert.showAndWait();
            return false;
        } else if (hesloRField.getText().isEmpty()) {
            alert.setContentText("Nezadal si heslo!");
            alert.showAndWait();
            return false;
        } else if (userIDRField.getText().isEmpty()) {
            alert.setContentText("Nezadal si user ID!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /*CISTENIE VSTUPOV PRE ZIAKA*/
    private void fieldClearZiak() {
        menoZField.setText("");
        priezviskoZField.setText("");
        usernameZField.setText("");
        hesloZField.setText("");
        userIDZField.setText("");
        rodicZField.setText("");
        triedaZField.setText("");
        ucitelZField.setText("");
    }

    /*CISTENIE VSTUPOV PRE UCITELA*/
    private void fieldClearUcitel() {
        menoUField.setText("");
        priezviskoUField.setText("");
        usernameUField.setText("");
        hesloUField.setText("");
        userIDUField.setText("");
        triedaUField.setText("");
    }

    /*CISTENIE VSTUPOV PRE RODICA*/
    private void fieldClearRodic() {
        menoRField.setText("");
        priezviskoRField.setText("");
        usernameRField.setText("");
        hesloRField.setText("");
        userIDRField.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //nastavenie blokovania vsetkych update buttonov
        updateZButton.setDisable(true);
        updateUButton.setDisable(true);
        updateRButton.setDisable(true);
        updateTButton.setDisable(true);

        DBConnector conn = new DBConnector();

        //ziaci
        menoZC.setCellValueFactory(new PropertyValueFactory<>("meno"));
        priezviskoZC.setCellValueFactory(new PropertyValueFactory<>("priezvisko"));
        usernameZC.setCellValueFactory(new PropertyValueFactory<>("username"));
        hesloZC.setCellValueFactory(new PropertyValueFactory<>("heslo"));
        userIDZC.setCellValueFactory(new PropertyValueFactory<>("userID"));
        rodicZC.setCellValueFactory(new PropertyValueFactory<>("rodic"));
        triedaZC.setCellValueFactory(new PropertyValueFactory<>("trieda"));
        ucitelZC.setCellValueFactory(new PropertyValueFactory<>("ucitel"));
        ziaciTableView.setItems(conn.selectAllZiak());

        //ucitelia
        idUC.setCellValueFactory(new PropertyValueFactory<>("id"));
        menoUC.setCellValueFactory(new PropertyValueFactory<>("meno"));
        priezviskoUC.setCellValueFactory(new PropertyValueFactory<>("priezvisko"));
        usernameUC.setCellValueFactory(new PropertyValueFactory<>("username"));
        hesloUC.setCellValueFactory(new PropertyValueFactory<>("heslo"));
        userIDUC.setCellValueFactory(new PropertyValueFactory<>("userID"));
        triedaUC.setCellValueFactory(new PropertyValueFactory<>("trieda"));
        uciteliaTableView.setItems(conn.selectAllUcitel());

        //rodicia
        idRC.setCellValueFactory(new PropertyValueFactory<>("id"));
        menoRC.setCellValueFactory(new PropertyValueFactory<>("meno"));
        priezviskoRC.setCellValueFactory(new PropertyValueFactory<>("priezvisko"));
        usernameRC.setCellValueFactory(new PropertyValueFactory<>("username"));
        hesloRC.setCellValueFactory(new PropertyValueFactory<>("heslo"));
        userIDRC.setCellValueFactory(new PropertyValueFactory<>("userID"));
        rodiciaTableView.setItems(conn.selectAllRodic());

        //triedy
        idTC.setCellValueFactory(new PropertyValueFactory<>("id"));
        menoTC.setCellValueFactory(new PropertyValueFactory<>("meno"));
        triedyTableView.setItems(conn.selectAllTrieda());
    }
}
