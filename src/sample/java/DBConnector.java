package sample.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /*CONNECT*/
    public void connect() {
        try {
            // db parameter
            String url = "jdbc:sqlite:src/sample/db/spseduDBv2.db";

            // create a connection to the database
            this.connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*DISCONNECT*/
    public void disconnect(){
        try {
            if (connection != null) {
                connection.close();
                System.out.println("SQL connection has been closed");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*LOGIN - VRACIA LIST UZIVATELOV*/
    public ObservableList<User> selectForLogin(){
        connect();
        String SQL="SELECT id, username, heslo, rola FROM ziak UNION SELECT " +
                "id, username, heslo, rola FROM rodic UNION SELECT " +
                "id, username, heslo, rola FROM ucitel";
        ObservableList<User>data=null;
        try {
            data=FXCollections.observableArrayList();
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                data.add(new User(rs.getInt(1), rs.getString(2),rs.getString(3),
                        rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return data;
    }

    /*ADMIN - VRACIA VSETKYCH ZIAKOV*/
    public ObservableList<Ziak> selectAllZiak(){
        connect();
        String SQL="SELECT ziak.ID, ziak.meno, ziak.priezvisko, ziak.username, ziak.heslo, ziak.userID, ziak.rola, " +
                "ziak.pritomnost, rodic.meno || ' ' || rodic.priezvisko AS Rodic, " +
                "trieda.meno AS Trieda, ucitel.meno || ' ' || ucitel.priezvisko AS Ucitel FROM ziak " +
                "INNER JOIN rodic ON ziak.rodicID = rodic.ID " +
                "INNER JOIN trieda ON ziak.triedaID = trieda.ID " +
                "INNER JOIN ucitel ON ziak.ucitelID = ucitel.ID";
        ObservableList<Ziak>data=null;
        try {
            data=FXCollections.observableArrayList();
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                data.add(new Ziak(rs.getInt("ID"), rs.getString("meno"), rs.getString("priezvisko")
                        ,rs.getString("username"), rs.getString("heslo")
                        ,rs.getString("userID"), rs.getString("rola")
                        ,rs.getString("pritomnost"), rs.getString("Rodic")
                        ,rs.getString("Trieda"), rs.getString("Ucitel")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return data;
    }

    /*ADMIN - VRACIA VSETKYCH UCITELOV*/
    public ObservableList<Ucitel> selectAllUcitel(){
        connect();
        String SQL="SELECT ucitel.ID, ucitel.meno, ucitel.priezvisko, ucitel.username, ucitel.heslo, ucitel.userID," +
                " ucitel.rola, trieda.meno AS trieda FROM ucitel JOIN trieda ON ucitel.triedaID = trieda.ID";
        ObservableList<Ucitel>data=null;
        try {
            data=FXCollections.observableArrayList();
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                data.add(new Ucitel(rs.getInt("ID"), rs.getString("meno")
                        ,rs.getString("priezvisko"),rs.getString("username")
                        ,rs.getString("heslo")
                        ,rs.getString("userID"), rs.getString("rola")
                        ,rs.getString("Trieda")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return data;
    }

    /*ADMIN - VRACIA VSETKYCH RODICOV*/
    public ObservableList<Rodic> selectAllRodic(){
        connect();
        String SQL="SELECT rodic.ID, rodic.meno, rodic.priezvisko, rodic.username, rodic.heslo, rodic.userID," +
                " rodic.rola FROM rodic";
        ObservableList<Rodic>data=null;
        try {
            data=FXCollections.observableArrayList();
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                data.add(new Rodic(rs.getInt("ID"), rs.getString("meno")
                        ,rs.getString("priezvisko"),rs.getString("username")
                        ,rs.getString("heslo"),rs.getString("userID")
                        , rs.getString("rola")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return data;
    }

    /*ADMIN - VRACIA VSETKY TRIEDY*/
    public ObservableList<Trieda> selectAllTrieda(){
        connect();
        String SQL="SELECT trieda.ID, trieda.meno FROM trieda";
        ObservableList<Trieda>data=null;
        try {
            data=FXCollections.observableArrayList();
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                data.add(new Trieda(rs.getInt("ID"), rs.getString("meno")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return data;
    }

    /*ADMIN - VYTVARA ZIAKA*/
    public void insertZiak(String meno,String priezvisko,String username,String heslo,String userID, int rodic, int trieda, int ucitel){
        connect();
        String SQL="INSERT INTO ziak(meno,priezvisko,username,heslo,userID, rola, pritomnost, rodicID, triedaID, ucitelID) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstm=connection.prepareStatement(SQL)){
            pstm.setString(1,meno);
            pstm.setString(2,priezvisko);
            pstm.setString(3,username);
            pstm.setString(4,heslo);
            pstm.setString(5,userID);
            pstm.setString(6, "Z");
            pstm.setString(7,"nepritomny");
            pstm.setInt(8, rodic);
            pstm.setInt(9, trieda);
            pstm.setInt(10, ucitel);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

    /*ADMIN - VYTVARA UCITELA*/
    public void insertUcitel(String meno,String priezvisko,String username,String heslo,String userID, int trieda){
        connect();
        String SQL="INSERT INTO ucitel(meno, priezvisko, username, heslo,userID, rola, triedaID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement pstm=connection.prepareStatement(SQL)){
            pstm.setString(1,meno);
            pstm.setString(2,priezvisko);
            pstm.setString(3,username);
            pstm.setString(4,heslo);
            pstm.setString(5,userID);
            pstm.setString(6, "U");
            pstm.setInt(7, trieda);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

    /*ADMIN - VYTVARA RODICA*/
    public void insertRodic(String meno,String priezvisko,String username,String heslo,String userID){
        connect();
        String SQL="INSERT INTO rodic(meno,priezvisko,username,heslo,userID, rola) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement pstm=connection.prepareStatement(SQL)){
            pstm.setString(1,meno);
            pstm.setString(2,priezvisko);
            pstm.setString(3,username);
            pstm.setString(4,heslo);
            pstm.setString(5,userID);
            pstm.setString(6, "R");
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

    /*ADMIN - VYTVARA TRIEDU*/
    public void insertTrieda(String meno){
        connect();
        String SQL="INSERT INTO trieda(meno) VALUES(?)";
        try (PreparedStatement pstm=connection.prepareStatement(SQL)){
            pstm.setString(1,meno);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

    /*ADMIN - UPDATUJE ZIAKA*/
    public void updateZiak(int id, String meno, String priezvisko, String username, String heslo, String userID,
                           String rodicID, String triedaID, String ucitelID){
        connect();
        String sql = "UPDATE ziak SET meno = ?, priezvisko=?, username=?, heslo=?, userID=?, rodicID=?," +
                " triedaID=?, ucitelID=? WHERE id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,meno);
            pstmt.setString(2,priezvisko);
            pstmt.setString(3,username);
            pstmt.setString(4,heslo);
            pstmt.setString(5,userID);
            pstmt.setString(6,rodicID);
            pstmt.setString(7,triedaID);
            pstmt.setString(8,ucitelID);
            pstmt.setInt(9, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /*ADMIN - UPDATUJE UCITELA*/
    public void updateUcitel(int id, String meno, String priezvisko, String username, String heslo, String userID,
                             String triedaID){
        connect();
        String sql = "UPDATE ucitel SET meno = ?, priezvisko=?, username=?, heslo=?, userID=?, triedaID=? WHERE id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,meno);
            pstmt.setString(2,priezvisko);
            pstmt.setString(3,username);
            pstmt.setString(4,heslo);
            pstmt.setString(5,userID);
            pstmt.setString(6,triedaID);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /*ADMIN - UPDATUJE RODICA*/
    public void updateRodic(int id, String meno, String priezvisko, String username, String heslo, String userID){
        connect();
        String sql = "UPDATE rodic SET meno = ?, priezvisko=?, username=?, heslo=?, userID=? WHERE id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,meno);
            pstmt.setString(2,priezvisko);
            pstmt.setString(3,username);
            pstmt.setString(4,heslo);
            pstmt.setString(5,userID);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /*ADMIN - UPDATUJE TRIEDU*/
    public void updateTrieda(int id, String meno){
        connect();
        String sql = "UPDATE trieda SET meno = ? WHERE id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,meno);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
    /*UCITEL - SELECTNE UCITELOVU TRIEDU*/
    public ObservableList<Ziak> selectUcitelTrieda(String username){
        connect();
        String SQL="SELECT ziak.ID, ziak.meno, ziak.priezvisko, rodic.meno || ' ' || rodic.priezvisko AS rodic, ziak.pritomnost " +
                "FROM ziak JOIN rodic ON ziak.rodicID = rodic.ID  JOIN ucitel ON ziak.ucitelID = ucitel.ID" +
                " WHERE ucitel.username LIKE '" +username  +"'";
        ObservableList<Ziak>data=null;
        try {
            data=FXCollections.observableArrayList();
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                data.add(new Ziak(rs.getInt("ID"),rs.getString("meno"), rs.getString("priezvisko"),
                         rs.getString("rodic"), rs.getString("pritomnost")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return data;
    }

    /*UCITEL - SELECTNE UCITELOVU DOCHADZKU*/
    public ObservableList<Dochadzka> selectUcitelDochadzka(int ucitelID){
        connect();
        String SQL="SELECT dochadzka.id, ziak.meno, ziak.priezvisko, dochadzka.datum, dochadzka.pritomnost FROM dochadzka " +
                "JOIN ziak ON dochadzka.ziakID = ziak.ID " +
                "WHERE ziak.ucitelID = " +ucitelID +" AND dochadzka.pritomnost LIKE 'vymeskane'";
        ObservableList<Dochadzka>data=null;
        try {
            data=FXCollections.observableArrayList();
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                data.add(new Dochadzka(rs.getInt("id"), rs.getString("meno"),
                        rs.getString("priezvisko"), rs.getString("datum"), rs.getString("pritomnost")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            disconnect();
        }
        return data;
    }

    /*UCITEL - SELECTNE UCITELOVE OSPRAVEDLNENKY*/
    public ObservableList<Ospravedlnenka> selectUcitelOspravedlnenky(int ucitelID){
        connect();
        String SQL="\n" +
                "SELECT ospravedlnenka.id, ziak.meno || ' ' || ziak.priezvisko AS ziak, ospravedlnenka.text, ospravedlnenka.datum FROM ospravedlnenka " +
                "JOIN ziak ON ospravedlnenka.ziakID = ziak.ID " +
                "WHERE ziak.ucitelID = " +ucitelID;
        ObservableList<Ospravedlnenka>data=null;
        try {
            data=FXCollections.observableArrayList();
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()) {
                data.add(new Ospravedlnenka(rs.getInt("id"), rs.getString("ziak"),
                        rs.getString("text"), rs.getString("datum")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            disconnect();
        }
        return data;
    }

    /*UCITEL - DA VYMESKANY DEN DANEMU ZIAKOVI*/
    public void ucitelTriedaVymeskanie(int ziakID,String datum){
        connect();
        String SQL="INSERT INTO dochadzka(ziakID,datum,pritomnost)VALUES(?,?,?) ";
        try(PreparedStatement pstm=connection.prepareStatement(SQL)){
            pstm.setInt(1, ziakID);
            pstm.setString(2,datum);
            pstm.setString(3,"vymeskane");
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

    /*UCITEL - ZMENI PRITOMNOST V TABULKE DOCHADZKA Z VYMESKANE NA OSPRAVEDLNENE*/
    public void ucitelTriedaOspravedlnenie(int ziakID, String datum){
        connect();
        String SQL ="UPDATE dochadzka SET pritomnost = ? WHERE ziakID = ? AND datum LIKE ?";
        try(PreparedStatement ptsm=connection.prepareStatement(SQL)){
            ptsm.setString(1,"ospravedlnene");
            ptsm.setInt(2,ziakID);
            ptsm.setString(3, datum);
            ptsm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }

    }

    /*ZIAK - SELECTNE ZIAKOVU DOCHADZKU*/
    public ObservableList<Dochadzka> selectZiakDochadzka(int id){
        connect();
        String SQL="SELECT datum,pritomnost FROM dochadzka WHERE ziakID=? ";
        ObservableList<Dochadzka>data=null;
        try{
            data=FXCollections.observableArrayList();
            PreparedStatement stmt= connection.prepareStatement(SQL);
            stmt.setInt(1,id);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()) {
                data.add(new Dochadzka(rs.getString("datum"), rs.getString("pritomnost")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return data;
    }

    /*ZIAK - MENI STAV PRITOMNOSTI*/
    public void ziakPrichodOdchod(int id, String stav){
        connect();
        String sql="UPDATE ziak SET pritomnost = ? WHERE ziak.ID = ?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,stav);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            disconnect();
        }
    }

    /*RODIC - SELECTNE DOCHADZKU RODICOVHO DIETATA*/
    public ObservableList<Dochadzka> selectRodicDochadzka(int id){
        connect();
        String SQL="SELECT dochadzka.datum,dochadzka.pritomnost FROM dochadzka JOIN ziak on dochadzka.ziakID=ziak.ID JOIN rodic on ziak.rodicID=rodic.ID Where ziak.rodicID=?";
        ObservableList<Dochadzka>data=null;
        try {
            data=FXCollections.observableArrayList();
            PreparedStatement stmt=connection.prepareStatement(SQL);
            stmt.setInt(1,id);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                data.add(new Dochadzka(rs.getString("datum"),rs.getString("pritomnost")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return data;
    }

    /*RODIC - POSLE OSPRAVEDLNENKU*/
    public void rodicVytvorOspravedlnenku(int ziakID, String text, String datum){
        connect();
        String SQL="INSERT INTO ospravedlnenka (ziakID, text, datum) VALUES (?,?,?)";
        try (PreparedStatement pstm=connection.prepareStatement(SQL)){
            pstm.setInt(1,ziakID);
            pstm.setString(2,text);
            pstm.setString(3,datum);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

    /*RODIC - KONTROLUJE CI UZ NIE JE OSPRAVEDLNENE*/
    public boolean uzJeOspravedlnene(int ziakID, String datum){
        connect();
        String SQL="SELECT pritomnost FROM dochadzka WHERE ziakID = " +ziakID +" AND datum LIKE '" +datum +"'";
        try {
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                if(rs.getString("pritomnost").equals("ospravedlnene")){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return false;
    }

    /*POMOCNA - VRACIA PRITOMNOST ZIAKA*/
    public boolean getToggleStav(int id){
        connect();
        String sql="SELECT ziak.pritomnost FROM ziak WHERE ziak.id = " +id;
        boolean stav = false;
        try {
            PreparedStatement stmt=connection.prepareStatement(sql);
            ResultSet rs=stmt.executeQuery();
            if(rs.getString("pritomnost").equals("pritomny")){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return stav;
    }

    /*POMOCNA - ZMAZANIE TABULKY*/
    public void delete(int id, String table){
        connect();
        String sql = "DELETE FROM " +table +" WHERE ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            disconnect();
        }
    }

    /*POMOCNA - VRACIA ID UCITELA PODLA MENA*/
    public int getUcitelIDByName(String name){
        String menoPriezvisko[];
        menoPriezvisko = name.split(" ");
        int cislo = 1;
        connect();
        String sql = "SELECT ID FROM ucitel WHERE meno LIKE '" +menoPriezvisko[0] +"' AND priezvisko LIKE '" +menoPriezvisko[1] +"'";
        try{
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            cislo = rs.getInt("ID");
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
        return cislo;
    }

    /*POMOCNA - VRACIA ID RODICA PODLA MENA*/
    public int getRodicIDByName(String name){
        String menoPriezvisko[];
        menoPriezvisko = name.split(" ");
        int cislo = 1;
        connect();
        String sql = "SELECT ID FROM rodic WHERE meno LIKE '" +menoPriezvisko[0] +"' AND priezvisko LIKE '" +menoPriezvisko[1] +"'";
        try{
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            cislo = rs.getInt("ID");
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
        return cislo;
    }

    /*POMOCNA - VRACIA ID ZIAKA PODLA MENA*/
    public int getZiakIdByName(String name){
        String menoPriezvisko[];
        menoPriezvisko = name.split(" ");
        int cislo = 16;
        connect();
        String sql = "SELECT ID FROM ziak WHERE meno LIKE '" +menoPriezvisko[0] +"' AND priezvisko LIKE '" +menoPriezvisko[1] +"'";
        try{
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            cislo = rs.getInt("ID");
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
        return cislo;
    }

    /*POMOCNA - VRACIA ID TRIEDY PODLA MENA*/
    public int getTriedaIDByName(String name){
        int cislo = 1;
        connect();
        String sql = "SELECT ID FROM trieda WHERE meno LIKE '" +name +"'";
        try{
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            cislo = rs.getInt("ID");
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
        return cislo;
    }

    /*POMOCNA - KONTROLUJE CI UZ MA VYMESKANE V DANY DEN*/
    public boolean ucitelKontrola(int ziakID,String datum){
        connect();
        String SQL="SELECT ziakID,datum FROM dochadzka";
        try {
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                if(ziakID==rs.getInt("ziakID")&&datum.equals(rs.getString("datum"))){
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return true;
    }

    /*POMOCNA - VRATI ZIAK ID PODLA RODICA*/
    public int getZiakIDByRodicID(int rodicID){
        connect();
        int id=0;
        String SQL="SELECT ziak.ID, ziak.rodicID FROM ziak";
        try {
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next()){
                if(rs.getInt("rodicID") == rodicID)
                    return rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
        return id;
    }
}
