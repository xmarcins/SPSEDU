package sample.java;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PrihlasenieController{

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Label alert;

    /*PRIHLASENIE*/
    @FXML
    public void Login(ActionEvent event) {
        if (username.getText().isEmpty()) {
            alert.setText("Nezadal si meno alebo heslo!");
        } else if (password.getText().isEmpty()){
            alert.setText("Nezada si heslo!");
        } else {
            DBConnector conn = new DBConnector();
            String meno=username.getText();
            String heslo=password.getText();
            boolean exists=false;
            if(isAdmin(meno, heslo)){
                goToAdmin();
                return;
            }
            for (User user:conn.selectForLogin()){
                if (meno.equals(user.getUsername())&& heslo.equals(user.getHeslo())){
                    exists=true;
                    switch (user.getRola()){
                        case "U" :
                            goToUcitel(user);break;
                        case "Z" :
                            goToZiak(user);break;
                        case "R" :
                            goToRodic(user);break;
                        default:
                            System.out.println("Role neexistuje");
                    }
                }
            }
            if (exists == false) {
                alert.setText("Zadal si neplatne udaje");
            }
        }
    }

    /*PRESMEROVANEI NA ADMINA*/
    private void goToAdmin(){
        try {
            Stage stage = (Stage) alert.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/admin.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setTitle("Admin");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*PRESMEROVANIE NA UCITELA*/
    private void goToUcitel(User user) {
        try {
            Stage stage = (Stage) username.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ucitel.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            UcitelController ucitelController = loader.getController();
            ucitelController.setUcitel(user);

            stage.setTitle("Ucitel");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*PRESMEROVANIE NA UCITELA*/
    private void goToRodic(User user) {
        try {
            Stage stage = (Stage) username.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/rodic.fxml"));
            Parent root = loader.load();
            RodicController rodicController=loader.getController();
            rodicController.setRodic(user);
            Scene scene = new Scene(root);

            stage.setTitle("Rodic");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*PRESMEROVANIE NA ZIAKA*/
    private void goToZiak(User user) {
        try {
            Stage stage = (Stage) username.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ziak.fxml"));
            Parent root = loader.load();
            ZiakController ziakController = loader.getController();
            ziakController.setZiak(user);
            Scene scene = new Scene(root);

            stage.setTitle("Ziak");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*POMOCNA - KONTROLUJE CI JE ADMIN*/
    public boolean isAdmin(String meno, String heslo){
        return meno.equals("Admin") && heslo.equals("password");
    }
}