/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.DataSampahWrapper;
import model.User;
import model.UserWrapper;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class TambahUserController implements Initializable {
    @FXML
    private Button tambah;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private CheckBox visible;
    @FXML
    private ChoiceBox cmbAkses;
    @FXML
    private TextField nama;
    @FXML
    private Label slose;

    /**
     * Initializes the controller class.
     */
    
    ObservableList<User> dataUser = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadUserDataFromFile();
        cmbAkses.getItems().removeAll(cmbAkses.getItems());
        cmbAkses.getItems().addAll("Admin", "UserBantul", "UserKidul", "UserKulon", "UserSleman", "UserJogja");
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            
            String isi = user.getText();
            String isi2 = pass.getText();
            String kodeIsi1;
            String kodeIsi2;
            int temp1;
            int temp2;

            // enkripsi sederhana 
            // karakter ke-i dikodekan ASCII-nya menjadi ASCII + (i + i + (jumlah karakter isi / 3)
            kodeIsi1="";
            kodeIsi2="";
            for (int i=0;i<isi.length();i++){
                temp1 = (int)isi.charAt(i) + i + i + (isi.length()/3);
                kodeIsi1 = kodeIsi1 + (char)temp1;
            }
//            label.setText(kodeIsi);
            
            for (int i=0;i<isi2.length();i++){
                temp2 = (int)isi2.charAt(i) + i + i + (isi2.length()/3);
                kodeIsi2 = kodeIsi2 + (char)temp2;
            }
            
            User data = new User();
            data.setNama(nama.getText());
            data.setUsername(kodeIsi1);
            data.setPassword(kodeIsi2);
            data.setAkses((String)cmbAkses.getValue());
            dataUser.add(data);
            
            saveUserDataToFile();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Akun tersimpan!");
            alert.showAndWait();
            
//            popUpDisplay2();
            
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setHeaderText(null);
            alert.setContentText("Cek Pengisian Data!");
            alert.showAndWait();
        }
    }

    @FXML
    private void visibleAction(ActionEvent event) {
        if (visible.isSelected()){
            pass.setPromptText(pass.getText());
            pass.setText("");
        }
        else {
            pass.setText(pass.getPromptText());
            pass.setPromptText("");
        }
    }

    @FXML
    private void SloceAction(MouseEvent event) {
        Platform.exit();
    }
    
    public File getUserFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(TambahUserController.class);
        String filePath = prefs.get("filepath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    public void setUserFilePath(File f) {
        Preferences prefs = Preferences.userNodeForPackage(TambahUserController.class);
        if (f != null) {
            prefs.put("filePath", f.getPath());
        } else {
            prefs.remove("filePath");
        }
    }
    
    public void loadUserDataFromFile() {
        try {
            File file = new File("C:/XML/DataUser.xml");
            JAXBContext context = JAXBContext.newInstance(UserWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            
            UserWrapper wrapper = (UserWrapper) um.unmarshal(file);
            
            if (wrapper.getUsers()!= null) {
                dataUser.clear();
                dataUser.addAll(wrapper.getUsers());
            }
            
            setUserFilePath(file);
            
        } catch (Exception e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.showAndWait();
        }
    }
    
    public void saveUserDataToFile() {
        try {
            File file = new File("C:/XML/DataUser.xml");
            JAXBContext context = JAXBContext.newInstance(UserWrapper.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            UserWrapper wrapper = new UserWrapper();

            wrapper.setUsers(dataUser);
            m.marshal(wrapper, file);

            setUserFilePath(file);
        } catch (Exception e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.showAndWait();
        }
    }
    
    public void popUpDisplay2(){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TambahUser.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                
                stage.initStyle(StageStyle.UTILITY);
                stage.setScene(new Scene(root1));
                stage.close();
            }
            catch (IOException ex){
                Logger.getLogger(DashboardMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public ObservableList getData() {
        return dataUser;        
    }
}
