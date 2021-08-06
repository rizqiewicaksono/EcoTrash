
package controller;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.DataSampah;
import model.User;
import model.UserWrapper;

/**
 *
 * @author by Sigit Priadi
 */
public class LoginController implements Initializable {
    @FXML
    private CheckBox visible;
    
    @FXML
    private Label slose;
    
    @FXML
    private TextField user;

    @FXML
    private PasswordField pass;

    @FXML
    private Button login;
    
    private ObservableList<User> dataUser = FXCollections.observableArrayList();
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        boolean play = false;
       
        TambahUserController controller = new TambahUserController();
        controller.loadUserDataFromFile();
        
        List<User> filter = controller.getData();
        
         String isi1 = user.getText();
            String isi2 = pass.getText();
            String kodeIsi1;
            String kodeIsi2;
            int temp1;
            int temp2;
        //"Admin", "UserBantul", "UserGnKidul", "UserJogja"
        
        String bantul = "UserBantul";
        String kidul = "UserKidul";
        String kulon = "UserKulon";
        String sleman = "UserSleman";
        String jogja = "UserJogja";
        
        try {

            // dekripsi untuk mengembalikan hasil enkripsi
            // karakter ke-i dikodekan ASCII-nya menjadi ASCII - (i + i + (jumlah karakter isi / 3)
            kodeIsi1="";
            for (int i=0;i<isi1.length();i++){
                temp1 = (int)isi1.charAt(i) + ( i + i + (isi1.length()/3));
                kodeIsi1 = kodeIsi1 + (char)temp1;
            }
            
            kodeIsi2="";
            for (int i=0;i<isi2.length();i++){
                temp2 = (int)isi2.charAt(i) + ( i + i + (isi2.length()/3));
                kodeIsi2 = kodeIsi2 + (char)temp2;
            }
            
            System.out.println(kodeIsi1);
            System.out.println(kodeIsi2);
            
            for(User p : filter){                
                if(kodeIsi1.equals(p.getUsername()) && kodeIsi2.equals(p.getPassword())){                       
                    play = true;
                    break;              
                }
            }
            if(play == true){
                popUpMenuDashboard(event);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informasi");
                alert.setHeaderText(null);
                alert.setContentText("Login Berhasil");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Perigatan!");
                alert.setHeaderText(null);
                alert.setContentText("Login Gagal");
                alert.showAndWait(); 
            }           
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @FXML
    void visibleAction (ActionEvent event){
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
    private void SloceAction() {
        Platform.exit();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }
    
    public void popUpMenuDashboard(ActionEvent event){
        try {
            Parent dashboard = FXMLLoader.load(getClass().getResource("/view/DashboardMenu.fxml"));
            Scene dashboardscene = new Scene(dashboard);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(dashboardscene);
            window.setTitle("DashBoard ECO TRASH");
            window.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
            window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
        } catch (IOException ex) {
            Logger.getLogger(DashboardMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public File getUserFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        String filePath = prefs.get("filepath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    public void setUserFilePath(File f) {
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
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
}