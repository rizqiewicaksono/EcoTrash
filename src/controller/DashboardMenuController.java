/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import model.DataSampah;
import model.DataSampahWrapper;

import java.time.LocalDate;
import java.util.Iterator;
import ecotrash.MainApp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * FXML Controller class
 *
 * @author Sigit Priadi & Andri Setyadi
 */
public class DashboardMenuController implements Initializable {
    
    private MainApp mainApp;
    @FXML
    private PieChart pcSampah;
    @FXML
    private AnchorPane tutup;
    @FXML
    private DatePicker dpGrafik;
    @FXML
    private BarChart<String, Integer> bcDataSampah;
    @FXML
    private Button btnBeranda;
    @FXML
    private Button btndatasampah;
    @FXML
    private Button btngrafik;
    @FXML
    private Pane paneBeranda;
    @FXML
    private Pane paneDataSampah;
    @FXML
    private ChoiceBox cbnmkota;
    @FXML
    private TextField txtjmlpddk;
    @FXML
    private ChoiceBox cbktgrsampah;
    @FXML
    private TextField txtjmlsampahmasuk;
    @FXML
    private TextField txtjmlterkelola;
    @FXML
    private TextField txtjmlnotterkelola;
    @FXML
    private DatePicker dptglmasuk;
    @FXML
    private Button buttonadd;
    @FXML
    private TableView<DataSampah> tvsampah;
    @FXML
    private TableColumn<DataSampah, Integer> tcno;
    @FXML
    private TableColumn<DataSampah, String> tcnamakota;
    @FXML
    private TableColumn<DataSampah, Integer> tcpnddk;
    @FXML
    private TableColumn<DataSampah, String> tckategorisampah;
    @FXML
    private TableColumn<DataSampah, Integer> tcjmlsampahmasuk;
    @FXML
    private TableColumn<DataSampah, DatePicker> tctglmasuk;
    @FXML
    private TableColumn<DataSampah, Integer> tcjmlterkelola;
    @FXML
    private TableColumn<DataSampah, Integer> tcjmlnotterkelola;
    @FXML
    private Button buttonupdate;
    @FXML
    private Button buttondelete;
    @FXML
    private Pane paneGrafik;
    @FXML
    private Button btnlogout;
    @FXML
    private ComboBox cmbKabupaten;
    @FXML
    private Button coba;
    @FXML
    private Label gpModusTidakTerkelola;
    @FXML
    private Label gpMedianTidakTerkelola;
    @FXML
    private Label gpMeanTidakTerkelola;
    @FXML
    private Label gpMedianTerkelola;
    @FXML
    private Label gpModusTerkelola;
    @FXML
    private Label gpMeanTerkelola;
    @FXML
    private Label tidakInterval1;
    @FXML
    private Label tidakInterval2;
    @FXML
    private Label tidakInterval3;
    @FXML
    private Label interval11;
    @FXML
    private Label interval13;
    @FXML
    private Label interval12;

    /**
     * Initializes the controller class.
     */
    
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    private ObservableList<DataSampah> dataSampah = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadSampahDataFromFile();
        tvsampah.setItems(dataSampah);
        
        tcno.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(1+tvsampah.getItems().indexOf(column.getValue())));
        
        tcnamakota.setCellValueFactory(new PropertyValueFactory<DataSampah, String>("nama_kota"));
        tcpnddk.setCellValueFactory(new PropertyValueFactory<DataSampah, Integer>("jumlah_penduduk"));
        tckategorisampah.setCellValueFactory(new PropertyValueFactory<DataSampah, String>("katagori_sampah"));
        tcjmlsampahmasuk.setCellValueFactory(new PropertyValueFactory<DataSampah, Integer>("jumlah_sampah_masuk"));
        tctglmasuk.setCellValueFactory(new PropertyValueFactory<DataSampah, DatePicker>("tanggal_masuk"));
        tcjmlterkelola.setCellValueFactory(new PropertyValueFactory<DataSampah, Integer>("jumlah_sampah_terkelola"));
        tcjmlnotterkelola.setCellValueFactory(new PropertyValueFactory<DataSampah, Integer>("jumlah_sampah_tidak_terkelola"));
        
        cbnmkota.getItems().removeAll(cbnmkota.getItems());
        cbnmkota.getItems().addAll("Kabupaten Bantul", "Kabupaten Gunung Kidul", "Kabupaten Kulon Progo", "Kabupaten Sleman", "Kota Yogyakarta");
        
        cbktgrsampah.getItems().removeAll(cbnmkota.getItems());
        cbktgrsampah.getItems().addAll("Organik", "Anorganik");
        
        cmbKabupaten.getItems().removeAll(cbnmkota.getItems());
        cmbKabupaten.getItems().addAll("Kabupaten Bantul", "Kabupaten Gunung Kidul", "Kabupaten Kulon Progo", "Kabupaten Sleman", "Kota Yogyakarta");
    
        kosong();
    }    

    @FXML
    private void handleButtonActionberanda(ActionEvent event) {
        paneBeranda.setVisible(true);
        paneDataSampah.setVisible(false);
        paneGrafik.setVisible(false);
    }

    @FXML
    private void handleButtonActiondatasampah(ActionEvent event) {
        paneBeranda.setVisible(false);
        paneDataSampah.setVisible(true);
        paneGrafik.setVisible(false);
        kosong();
    }
    
    @FXML
    private void handleClickGrafikDatePicker(ActionEvent event){
        ObservableList<PieChart.Data> chart1 = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> chart2 = FXCollections.observableArrayList();
        ObservableList<DataSampah> filter = FXCollections.observableArrayList();
        
        for(DataSampah p : dataSampah){
            if(p.getTanggal_masuk().equals(dpGrafik.getValue())){
                filter.add(p);
            }
        }
        
        int sum1 = 0;
        int sum2 = 0;        
        for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){
            DataSampah x = it.next();
            sum1 += x.getJumlah_sampah_terkelola();
            sum2 += x.getJumlah_sampah_tidak_terkelola();
        }
        
        for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){
            DataSampah x = it.next();
            chart1.add(new PieChart.Data(x.getNama_kota(), sum1));
            chart2.add(new PieChart.Data(x.getNama_kota(), sum2));
        }

        pcSampah.setData(chart1);
        pcSampah.setData(chart2);
    }
    
    @FXML
    private void handleGrafikItem(ActionEvent Event){        
        XYChart.Series<String, Integer> series1 = new XYChart.Series();
        XYChart.Series<String, Integer> series2 = new XYChart.Series();
        series1.setName("Sampah Terkelola");
        series2.setName("Sampah Tidak Terkelola");
        ObservableList<DataSampah> filter = FXCollections.observableArrayList();
        
        try {
            bcDataSampah.getData().clear();
           
            if(cmbKabupaten.getValue().equals("Kabupaten Bantul")){
                String a = (String) cmbKabupaten.getValue();
                DecimalFormat koma = new DecimalFormat("#.##");
                for(DataSampah p : dataSampah){
                    if(p.getNama_kota().equals(a)){
                        filter.add(p);
                    }
                }

                //proses mean + filter
                double jumlahTerkelola=0;
                double jumlahTidakTerkelola = 0;
                int totalData=0;
                for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){
                    DataSampah x = it.next();

                    LocalDate tanggal = x.getTanggal_masuk();
                    String b = String.valueOf(tanggal);

                    series1.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_terkelola()));
                    series2.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_tidak_terkelola()));
                
                    jumlahTerkelola = jumlahTerkelola + x.getJumlah_sampah_terkelola();
                    jumlahTidakTerkelola = jumlahTidakTerkelola + x.getJumlah_sampah_tidak_terkelola();
                    totalData++;
                }
                double rata1 = jumlahTidakTerkelola / totalData;
                double rata2 = jumlahTerkelola / totalData; 
                
                //cetak mean terkelola
                gpMeanTerkelola.setText(String.valueOf(koma.format(rata2)));
                //cetak mean tidak terkelola
                gpMeanTidakTerkelola.setText(String.valueOf(koma.format(rata1)));
                
                //proses modus terkelola
                    int md1=0;
                    int md2=0;
                    int md3=0;
                    int jumlahData =0;
                for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                //pilihan interval data
                    if(x.getJumlah_sampah_terkelola() >=1 && x.getJumlah_sampah_terkelola() <= 500){
                        md1++;
                    }
                    else if(x.getJumlah_sampah_terkelola()>= 501 && x.getJumlah_sampah_terkelola()<=1000){
                        md2++;
                    }
                    else if(x.getJumlah_sampah_terkelola()>=1001 && x.getJumlah_sampah_terkelola()<1500){
                        md3++;
                    }
                    jumlahData++;
                }
                    //mencetak jumlah frekuensi
                    interval11.setText(String.valueOf(md1));
                    interval12.setText(String.valueOf(md2));
                    interval13.setText(String.valueOf(md3));
                    
                    //pilihan jumlah frekuensi
                if(md1>md2){
                    if(md1>md3){
                        double bo = 1 - 0.5;
                        double d1 = md1;
                        double d2 = md1 -md2;
                        double l = (501-0.5) - (1-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                    }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = md3-md2;
                        double d2 = md3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                    }
                }
                else{
                    if(md2>md3){
                        double bo = 501 - 0.5;
                        double d1 = md2-md1;
                        double d2 = md2-md3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                    }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = md3-md2;
                        double d2 = md3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median terkelola
                     int urut = jumlahData /2;    
                if(md1>=urut){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = md1;
                     double C = 500;
                     double median = tb + (((urut-F) /f) * C);
                     gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2 >= urut){
                    double tb = 501 - 0.5;
                    double F = md1;
                    double f = md2;
                    double C = 500;
                    double median = tb + (((urut-F) /f) * C);
                    gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2+md3 >= urut ){
                   double tb = 1001 - 0.5;
                   double F = md1 + md2;
                   double f = md3;
                   double C = 500;
                   double median = tb + (((urut-F) /f) * C);
                   gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
            
                // modus tidak terkelola
                    int mdtidak1=0;
                    int mdtidak2=0;
                    int mdtidak3=0;
                    int jumlahData2 =0;
                    for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                    //pilihan interval
                        if(x.getJumlah_sampah_tidak_terkelola() >=1 && x.getJumlah_sampah_tidak_terkelola() <= 500){
                             mdtidak1++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola()>= 501 && x.getJumlah_sampah_tidak_terkelola()<=1000){
                             mdtidak2++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola() >=1001 && x.getJumlah_sampah_tidak_terkelola()<1500){
                             mdtidak3++;
                         }
                        jumlahData2++;
                    }
                        tidakInterval1.setText(String.valueOf(mdtidak1));
                        tidakInterval2.setText(String.valueOf(mdtidak2));
                        tidakInterval3.setText(String.valueOf(mdtidak3));
                    
                    if(mdtidak1>mdtidak2){
                        if(mdtidak1>mdtidak3){
                            double bo = 1 - 0.5;
                            double d1 = mdtidak1;
                            double d2 = mdtidak1 -mdtidak2;
                            double l = (501-0.5) - (1-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                        else{
                            double bo = 1001 - 0.5;
                            double d1 = mdtidak3-mdtidak2;
                            double d2 = mdtidak3 ;
                            double l = (1500+0.5) - (1001-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    }
                    else{
                        if(mdtidak2>mdtidak3){
                        double bo = 501 - 0.5;
                        double d1 = mdtidak2-mdtidak1;
                        double d2 = mdtidak2-mdtidak3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = mdtidak3-mdtidak2;
                        double d2 = mdtidak3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median tidak terkelola
                     int urut2 = jumlahData2 /2;    
                if(mdtidak1>=urut2){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = mdtidak1;
                     double C = 500;
                     double median = tb + (((urut2-F) /f) * C);
                     gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2 >= urut2){
                    double tb = 501 - 0.5;
                    double F = mdtidak1;
                    double f = mdtidak2;
                    double C = 500;
                    double median = tb + (((urut2-F) /f) * C);
                    gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2+mdtidak3 >= urut2 ){
                   double tb = 1001 - 0.5;
                   double F = mdtidak1 + mdtidak2;
                   double f = mdtidak3;
                   double C = 500;
                   double median = tb + (((urut2-F) /f) * C);
                   gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                bcDataSampah.getData().add(series1);
                bcDataSampah.getData().add(series2);
            }
                    
            else if(cmbKabupaten.getValue().equals("Kabupaten Gunung Kidul")){
                String a = (String) cmbKabupaten.getValue();
                DecimalFormat koma = new DecimalFormat("#.##");
                for(DataSampah p : dataSampah){
                    if(p.getNama_kota().equals(a)){
                        filter.add(p);

                    }
                }
                double jumlahTerkelola = 0;
                double jumlahTidakTerkelola = 0;
                double totalData =0;
                for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){
                    DataSampah x = it.next();

                    LocalDate tanggal = x.getTanggal_masuk();
                    String b = String.valueOf(tanggal);

                    series1.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_terkelola()));
                    series2.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_tidak_terkelola()));
                    
                    jumlahTerkelola = jumlahTerkelola + x.getJumlah_sampah_terkelola();
                    jumlahTidakTerkelola = jumlahTidakTerkelola + x.getJumlah_sampah_tidak_terkelola();
                    totalData++;
                }
                
                double meanTerkelola = jumlahTerkelola / totalData;
                double meanTidakTerkelola = jumlahTidakTerkelola / totalData;
                gpMeanTerkelola.setText(String.valueOf(koma.format(meanTerkelola)));
                gpMeanTidakTerkelola.setText(String.valueOf(koma.format(meanTidakTerkelola)));             
                
                 //proses modus terkelola
                    int md1=0;
                    int md2=0;
                    int md3=0;
                    int jumlahData =0;
                    for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                    //pilihan interval
                        if(x.getJumlah_sampah_terkelola() >=1 && x.getJumlah_sampah_terkelola() <= 500){
                             md1++;
                        }
                         else if(x.getJumlah_sampah_terkelola()>= 501 && x.getJumlah_sampah_terkelola()<=1000){
                             md2++;
                        }
                         else if(x.getJumlah_sampah_terkelola()>=1001 && x.getJumlah_sampah_terkelola()<1500){
                             md3++;
                         }
                        jumlahData++;
                    }
                        interval11.setText(String.valueOf(md1));
                        interval12.setText(String.valueOf(md2));
                        interval13.setText(String.valueOf(md3));
                    
                    //pilihan jumlah frekuensi
                    if(md1>md2){
                        if(md1>md3){
                            double bo = 1 - 0.5;
                            double d1 = md1;
                            double d2 = md1 -md2;
                            double l = (501-0.5) - (1-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                        else{
                            double bo = 1001 - 0.5;
                            double d1 = md3-md2;
                            double d2 = md3 ;
                            double l = (1500+0.5) - (1001-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    }
                    else{
                        if(md2>md3){
                        double bo = 501 - 0.5;
                        double d1 = md2-md1;
                        double d2 = md2-md3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = md3-md2;
                        double d2 = md3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median terkelola
                     int urut = jumlahData /2;    
                if(md1>=urut){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = md1;
                     double C = 500;
                     double median = tb + (((urut-F) /f) * C);
                     gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2 >= urut){
                    double tb = 501 - 0.5;
                    double F = md1;
                    double f = md2;
                    double C = 500;
                    double median = tb + (((urut-F) /f) * C);
                    gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2+md3 >= urut ){
                   double tb = 1001 - 0.5;
                   double F = md1 + md2;
                   double f = md3;
                   double C = 500;
                   double median = tb + (((urut-F) /f) * C);
                   gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
            
                // modus tidak terkelola
                    int mdtidak1=0;
                    int mdtidak2=0;
                    int mdtidak3=0;
                    int jumlahData2 =0;
                    for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                    //pilihan interval
                        if(x.getJumlah_sampah_tidak_terkelola() >=1 && x.getJumlah_sampah_tidak_terkelola() <= 500){
                             mdtidak1++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola()>= 501 && x.getJumlah_sampah_tidak_terkelola()<=1000){
                             mdtidak2++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola() >=1001 && x.getJumlah_sampah_tidak_terkelola()<1500){
                             mdtidak3++;
                         }
                        jumlahData2++;
                    }
                     tidakInterval1.setText(String.valueOf(mdtidak1));
                     tidakInterval2.setText(String.valueOf(mdtidak2));
                     tidakInterval3.setText(String.valueOf(mdtidak3));
                    
                    if(mdtidak1>mdtidak2){
                        if(mdtidak1>mdtidak3){
                            double bo = 1 - 0.5;
                            double d1 = mdtidak1;
                            double d2 = mdtidak1 -mdtidak2;
                            double l = (501-0.5) - (1-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                        else{
                            double bo = 1001 - 0.5;
                            double d1 = mdtidak3-mdtidak2;
                            double d2 = mdtidak3 ;
                            double l = (1500+0.5) - (1001-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    }
                    else{
                        if(mdtidak2>mdtidak3){
                        double bo = 501 - 0.5;
                        double d1 = mdtidak2-mdtidak1;
                        double d2 = mdtidak2-mdtidak3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = mdtidak3-mdtidak2;
                        double d2 = mdtidak3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median tidak terkelola
                     int urut2 = jumlahData2 /2;    
                if(mdtidak1>=urut2){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = mdtidak1;
                     double C = 500;
                     double median = tb + (((urut2-F) /f) * C);
                     gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2 >= urut2){
                    double tb = 501 - 0.5;
                    double F = mdtidak1;
                    double f = mdtidak2;
                    double C = 500;
                    double median = tb + (((urut2-F) /f) * C);
                    gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2+mdtidak3 >= urut2 ){
                   double tb = 1001 - 0.5;
                   double F = mdtidak1 + mdtidak2;
                   double f = mdtidak3;
                   double C = 500;
                   double median = tb + (((urut2-F) /f) * C);
                   gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }

                bcDataSampah.getData().add(series1);
                bcDataSampah.getData().add(series2);

            }
            else if(cmbKabupaten.getValue().equals("Kabupaten Kulon Progo")){
                String a = (String) cmbKabupaten.getValue();
                DecimalFormat koma = new DecimalFormat("#.##");
                for(DataSampah p : dataSampah){
                    if(p.getNama_kota().equals(a)){
                        filter.add(p);                    }
                }
                
                double jumlahTerkelola = 0;
                double jumlahTidakTerkelola = 0;
                double totalData = 0;
                for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){
                    DataSampah x = it.next();

                    LocalDate tanggal = x.getTanggal_masuk();
                    String b = String.valueOf(tanggal);

                    series1.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_terkelola()));
                    series2.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_tidak_terkelola()));
               
                    jumlahTerkelola = jumlahTerkelola + x.getJumlah_sampah_terkelola();
                    jumlahTidakTerkelola = jumlahTidakTerkelola + x.getJumlah_sampah_tidak_terkelola();
                    
                    totalData++;
                }
                
                double meanTerkelola = jumlahTerkelola / totalData;
                double meanTidakTerkelola = jumlahTidakTerkelola / totalData;
                gpMeanTerkelola.setText(String.valueOf(koma.format(meanTerkelola)));
                gpMeanTidakTerkelola.setText(String.valueOf(koma.format(meanTidakTerkelola)));
                
                 //proses modus terkelola
                    int md1=0;
                    int md2=0;
                    int md3=0;
                    int jumlahData =0;
                    for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                    //pilihan interval
                        if(x.getJumlah_sampah_terkelola() >=1 && x.getJumlah_sampah_terkelola() <= 500){
                             md1++;
                        }
                         else if(x.getJumlah_sampah_terkelola()>= 501 && x.getJumlah_sampah_terkelola()<=1000){
                             md2++;
                        }
                         else if(x.getJumlah_sampah_terkelola()>=1001 && x.getJumlah_sampah_terkelola()<1500){
                             md3++;
                         }
                        jumlahData++;
                    }
                    
                      interval11.setText(String.valueOf(md1));
                      interval12.setText(String.valueOf(md2));
                      interval13.setText(String.valueOf(md3));
                    
                    //pilihan jumlah frekuensi
                    if(md1>md2){
                        if(md1>md3){
                            double bo = 1 - 0.5;
                            double d1 = md1;
                            double d2 = md1 -md2;
                            double l = (501-0.5) - (1-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                        else{
                            double bo = 1001 - 0.5;
                            double d1 = md3-md2;
                            double d2 = md3 ;
                            double l = (1500+0.5) - (1001-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    }
                    else{
                        if(md2>md3){
                        double bo = 501 - 0.5;
                        double d1 = md2-md1;
                        double d2 = md2-md3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = md3-md2;
                        double d2 = md3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median terkelola
                     int urut = jumlahData /2;    
                if(md1>=urut){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = md1;
                     double C = 500;
                     double median = tb + (((urut-F) /f) * C);
                     gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2 >= urut){
                    double tb = 501 - 0.5;
                    double F = md1;
                    double f = md2;
                    double C = 500;
                    double median = tb + (((urut-F) /f) * C);
                    gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2+md3 >= urut ){
                   double tb = 1001 - 0.5;
                   double F = md1 + md2;
                   double f = md3;
                   double C = 500;
                   double median = tb + (((urut-F) /f) * C);
                   gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
            
                // modus tidak terkelola
                    int mdtidak1=0;
                    int mdtidak2=0;
                    int mdtidak3=0;
                    int jumlahData2 =0;
                    for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                    //pilihan interval
                        if(x.getJumlah_sampah_tidak_terkelola() >=1 && x.getJumlah_sampah_tidak_terkelola() <= 500){
                             mdtidak1++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola()>= 501 && x.getJumlah_sampah_tidak_terkelola()<=1000){
                             mdtidak2++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola() >=1001 && x.getJumlah_sampah_tidak_terkelola()<1500){
                             mdtidak3++;
                         }
                        jumlahData2++;
                    }
                     tidakInterval1.setText(String.valueOf(mdtidak1));
                     tidakInterval2.setText(String.valueOf(mdtidak2));
                     tidakInterval3.setText(String.valueOf(mdtidak3));
                    
                    if(mdtidak1>mdtidak2){
                        if(mdtidak1>mdtidak3){
                            double bo = 1 - 0.5;
                            double d1 = mdtidak1;
                            double d2 = mdtidak1 -mdtidak2;
                            double l = (501-0.5) - (1-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                        else{
                            double bo = 1001 - 0.5;
                            double d1 = mdtidak3-mdtidak2;
                            double d2 = mdtidak3 ;
                            double l = (1500+0.5) - (1001-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    }
                    else{
                        if(mdtidak2>mdtidak3){
                        double bo = 501 - 0.5;
                        double d1 = mdtidak2-mdtidak1;
                        double d2 = mdtidak2-mdtidak3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = mdtidak3-mdtidak2;
                        double d2 = mdtidak3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median tidak terkelola
                     int urut2 = jumlahData2 /2;    
                if(mdtidak1>=urut2){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = mdtidak1;
                     double C = 500;
                     double median = tb + (((urut2-F) /f) * C);
                     gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2 >= urut2){
                    double tb = 501 - 0.5;
                    double F = mdtidak1;
                    double f = mdtidak2;
                    double C = 500;
                    double median = tb + (((urut2-F) /f) * C);
                    gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2+mdtidak3 >= urut2 ){
                   double tb = 1001 - 0.5;
                   double F = mdtidak1 + mdtidak2;
                   double f = mdtidak3;
                   double C = 500;
                   double median = tb + (((urut2-F) /f) * C);
                   gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }

                bcDataSampah.getData().add(series1);
                bcDataSampah.getData().add(series2);

            }
            else if(cmbKabupaten.getValue().equals("Kabupaten Sleman")){
                String a = (String) cmbKabupaten.getValue();
                DecimalFormat koma = new DecimalFormat("#.##");
                for(DataSampah p : dataSampah){
                    if(p.getNama_kota().equals(a)){
                        filter.add(p);

                    }
                }
                
                double jumlahTerkelola = 0;
                double jumlahTidakTerkelola = 0;
                double totalData =0;
                for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){
                    DataSampah x = it.next();

                    LocalDate tanggal = x.getTanggal_masuk();
                    String b = String.valueOf(tanggal);

                    series1.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_terkelola()));
                    series2.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_tidak_terkelola()));
                
                    jumlahTerkelola = jumlahTerkelola + x.getJumlah_sampah_terkelola();
                    jumlahTidakTerkelola = jumlahTidakTerkelola + x.getJumlah_sampah_tidak_terkelola();
                    totalData++;
                }

                double meanTerkelola = jumlahTerkelola / totalData;
                double meanTidakTerkelola = jumlahTidakTerkelola / totalData;
                gpMeanTerkelola.setText(String.valueOf(koma.format(meanTerkelola)));
                gpMeanTidakTerkelola.setText(String.valueOf(koma.format(meanTidakTerkelola)));
                
                 //proses modus terkelola
                    int md1=0;
                    int md2=0;
                    int md3=0;
                    int jumlahData =0;
                    for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                    //pilihan interval
                        if(x.getJumlah_sampah_terkelola() >=1 && x.getJumlah_sampah_terkelola() <= 500){
                             md1++;
                        }
                         else if(x.getJumlah_sampah_terkelola()>= 501 && x.getJumlah_sampah_terkelola()<=1000){
                             md2++;
                        }
                         else if(x.getJumlah_sampah_terkelola()>=1001 && x.getJumlah_sampah_terkelola()<1500){
                             md3++;
                         }
                        jumlahData++;
                    }
                        interval11.setText(String.valueOf(md1));
                        interval12.setText(String.valueOf(md2));
                        interval13.setText(String.valueOf(md3));
                    
                    //pilihan jumlah frekuensi
                    if(md1>md2){
                        if(md1>md3){
                            double bo = 1 - 0.5;
                            double d1 = md1;
                            double d2 = md1 -md2;
                            double l = (501-0.5) - (1-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                        else{
                            double bo = 1001 - 0.5;
                            double d1 = md3-md2;
                            double d2 = md3 ;
                            double l = (1500+0.5) - (1001-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    }
                    else{
                        if(md2>md3){
                        double bo = 501 - 0.5;
                        double d1 = md2-md1;
                        double d2 = md2-md3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = md3-md2;
                        double d2 = md3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median terkelola
                     int urut = jumlahData /2;    
                if(md1>=urut){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = md1;
                     double C = 500;
                     double median = tb + (((urut-F) /f) * C);
                     gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2 >= urut){
                    double tb = 501 - 0.5;
                    double F = md1;
                    double f = md2;
                    double C = 500;
                    double median = tb + (((urut-F) /f) * C);
                    gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2+md3 >= urut ){
                   double tb = 1001 - 0.5;
                   double F = md1 + md2;
                   double f = md3;
                   double C = 500;
                   double median = tb + (((urut-F) /f) * C);
                   gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
            
                // modus tidak terkelola
                    int mdtidak1=0;
                    int mdtidak2=0;
                    int mdtidak3=0;
                    int jumlahData2 =0;
                    for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                    //pilihan interval
                        if(x.getJumlah_sampah_tidak_terkelola() >=1 && x.getJumlah_sampah_tidak_terkelola() <= 500){
                             mdtidak1++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola()>= 501 && x.getJumlah_sampah_tidak_terkelola()<=1000){
                             mdtidak2++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola() >=1001 && x.getJumlah_sampah_tidak_terkelola()<1500){
                             mdtidak3++;
                         }
                        jumlahData2++;
                    }
                     tidakInterval1.setText(String.valueOf(mdtidak1));
                     tidakInterval2.setText(String.valueOf(mdtidak2));
                     tidakInterval3.setText(String.valueOf(mdtidak3));
                    
                    if(mdtidak1>mdtidak2){
                        if(mdtidak1>mdtidak3){
                            double bo = 1 - 0.5;
                            double d1 = mdtidak1;
                            double d2 = mdtidak1 -mdtidak2;
                            double l = (501-0.5) - (1-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                        else{
                            double bo = 1001 - 0.5;
                            double d1 = mdtidak3-mdtidak2;
                            double d2 = mdtidak3 ;
                            double l = (1500+0.5) - (1001-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    }
                    else{
                        if(mdtidak2>mdtidak3){
                        double bo = 501 - 0.5;
                        double d1 = mdtidak2-mdtidak1;
                        double d2 = mdtidak2-mdtidak3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = mdtidak3-mdtidak2;
                        double d2 = mdtidak3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median tidak terkelola
                     int urut2 = jumlahData2 /2;    
                if(mdtidak1>=urut2){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = mdtidak1;
                     double C = 500;
                     double median = tb + (((urut2-F) /f) * C);
                     gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2 >= urut2){
                    double tb = 501 - 0.5;
                    double F = mdtidak1;
                    double f = mdtidak2;
                    double C = 500;
                    double median = tb + (((urut2-F) /f) * C);
                    gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2+mdtidak3 >= urut2 ){
                   double tb = 1001 - 0.5;
                   double F = mdtidak1 + mdtidak2;
                   double f = mdtidak3;
                   double C = 500;
                   double median = tb + (((urut2-F) /f) * C);
                   gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                bcDataSampah.getData().add(series1);
                bcDataSampah.getData().add(series2);
            }
            else if(cmbKabupaten.getValue().equals("Kota Yogyakarta")){
                String a = (String) cmbKabupaten.getValue();
                DecimalFormat koma = new DecimalFormat("#.##");
                for(DataSampah p : dataSampah){
                    if(p.getNama_kota().equals(a)){
                        filter.add(p);
                    }
                }
                double jumlahTerkelola = 0;
                double jumlahTidakTerkelola = 0;
                double totalData = 0;
                for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){
                    DataSampah x = it.next();

                    LocalDate tanggal = x.getTanggal_masuk();
                    String b = String.valueOf(tanggal);

                    series1.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_terkelola()));
                    series2.getData().add(new XYChart.Data<String, Integer>(b, x.getJumlah_sampah_tidak_terkelola()));
                    
                    jumlahTerkelola = jumlahTerkelola + x.getJumlah_sampah_terkelola();
                    jumlahTidakTerkelola = jumlahTidakTerkelola + x.getJumlah_sampah_tidak_terkelola();
                     totalData++;

                }
                double meanTerkelola = jumlahTerkelola / totalData;
                double meanTidakTerkelola = jumlahTidakTerkelola / totalData;
                gpMeanTerkelola.setText(String.valueOf(koma.format(meanTerkelola)));
                gpMeanTidakTerkelola.setText(String.valueOf(koma.format(meanTidakTerkelola)));
                
                 //proses modus terkelola
                    int md1=0;
                    int md2=0;
                    int md3=0;
                    int jumlahData =0;
                    for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                    //pilihan interval
                        if(x.getJumlah_sampah_terkelola() >=1 && x.getJumlah_sampah_terkelola() <= 500){
                             md1++;
                        }
                         else if(x.getJumlah_sampah_terkelola()>= 501 && x.getJumlah_sampah_terkelola()<=1000){
                             md2++;
                        }
                         else if(x.getJumlah_sampah_terkelola()>=1001 && x.getJumlah_sampah_terkelola()<1500){
                             md3++;
                         }
                        jumlahData++;
                    }
                    
                        interval11.setText(String.valueOf(md1));  
                        interval12.setText(String.valueOf(md2));
                        interval13.setText(String.valueOf(md3));
                 //pilihan jumlah frekuensi
                    if(md1>md2){
                        if(md1>md3){
                            double bo = 1 - 0.5;
                            double d1 = md1;
                            double d2 = md1 -md2;
                            double l = (501-0.5) - (1-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                        else{
                            double bo = 1001 - 0.5;
                            double d1 = md3-md2;
                            double d2 = md3 ;
                            double l = (1500+0.5) - (1001-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    }
                    else{
                        if(md2>md3){
                        double bo = 501 - 0.5;
                        double d1 = md2-md1;
                        double d2 = md2-md3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = md3-md2;
                        double d2 = md3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median terkelola
                     int urut = jumlahData /2;    
                if(md1>=urut){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = md1;
                     double C = 500;
                     double median = tb + (((urut-F) /f) * C);
                     gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2 >= urut){
                    double tb = 501 - 0.5;
                    double F = md1;
                    double f = md2;
                    double C = 500;
                    double median = tb + (((urut-F) /f) * C);
                    gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(md1+md2+md3 >= urut ){
                   double tb = 1001 - 0.5;
                   double F = md1 + md2;
                   double f = md3;
                   double C = 500;
                   double median = tb + (((urut-F) /f) * C);
                   gpMedianTerkelola.setText(String.valueOf(koma.format(median)));
                }
            
                // modus tidak terkelola
                    int mdtidak1=0;
                    int mdtidak2=0;
                    int mdtidak3=0;
                    int jumlahData2 =0;
                    for(Iterator<DataSampah> it = filter.iterator(); it.hasNext();){            
                    DataSampah x = it.next();
                    //pilihan interval
                        if(x.getJumlah_sampah_tidak_terkelola() >=1 && x.getJumlah_sampah_tidak_terkelola() <= 500){
                             mdtidak1++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola()>= 501 && x.getJumlah_sampah_tidak_terkelola()<=1000){
                             mdtidak2++;
                        }
                         else if(x.getJumlah_sampah_tidak_terkelola() >=1001 && x.getJumlah_sampah_tidak_terkelola()<1500){
                             mdtidak3++;
                         }
                        jumlahData2++;
                    }
                     tidakInterval1.setText(String.valueOf(mdtidak1));
                     tidakInterval2.setText(String.valueOf(mdtidak2));
                     tidakInterval3.setText(String.valueOf(mdtidak3));
                    
                    if(mdtidak1>mdtidak2){
                        if(mdtidak1>mdtidak3){
                            double bo = 1 - 0.5;
                            double d1 = mdtidak1;
                            double d2 = mdtidak1 -mdtidak2;
                            double l = (501-0.5) - (1-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                        else{
                            double bo = 1001 - 0.5;
                            double d1 = mdtidak3-mdtidak2;
                            double d2 = mdtidak3 ;
                            double l = (1500+0.5) - (1001-0.5);
                            double hasil = bo + ((d1/(d1+d2))*l);
                            gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    }
                    else{
                        if(mdtidak2>mdtidak3){
                        double bo = 501 - 0.5;
                        double d1 = mdtidak2-mdtidak1;
                        double d2 = mdtidak2-mdtidak3 ;
                        double l = (1001-0.5) - (501-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));
                        }
                    else{
                        double bo = 1001 - 0.5;
                        double d1 = mdtidak3-mdtidak2;
                        double d2 = mdtidak3 ;
                        double l = (1500+0.5) - (1001-0.5);
                        double hasil = bo + ((d1/(d1+d2))*l);
                        gpModusTidakTerkelola.setText(String.valueOf(koma.format(hasil)));   
                        }
                    }
                
            //proses median tidak terkelola
                     int urut2 = jumlahData2 /2;    
                if(mdtidak1>=urut2){
                     double tb = 1 - 0.5;
                     double F = 0;
                     double f = mdtidak1;
                     double C = 500;
                     double median = tb + (((urut2-F) /f) * C);
                     gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2 >= urut2){
                    double tb = 501 - 0.5;
                    double F = mdtidak1;
                    double f = mdtidak2;
                    double C = 500;
                    double median = tb + (((urut2-F) /f) * C);
                    gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                else if(mdtidak1+mdtidak2+mdtidak3 >= urut2 ){
                   double tb = 1001 - 0.5;
                   double F = mdtidak1 + mdtidak2;
                   double f = mdtidak3;
                   double C = 500;
                   double median = tb + (((urut2-F) /f) * C);
                   gpMedianTidakTerkelola.setText(String.valueOf(koma.format(median)));
                }
                bcDataSampah.getData().add(series1);
                bcDataSampah.getData().add(series2);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleButtonActiongrafik(ActionEvent event) {
        bcDataSampah.setTitle("Grafik Data Sampah");
        paneBeranda.setVisible(false);
        paneDataSampah.setVisible(false);
        paneGrafik.setVisible(true);   
        bcDataSampah.getData().clear();
    }

    @FXML
    private void handleButtonActionprediksi(ActionEvent event) {
        paneBeranda.setVisible(false);
        paneDataSampah.setVisible(false);
        paneGrafik.setVisible(false);
    }

    @FXML
    private void handleButtonActionadd(ActionEvent event) {
        if(cbnmkota.getValue().equals(null) && txtjmlpddk.getText().equals("") && cbktgrsampah.getValue().equals(null) && txtjmlsampahmasuk.getText().equals("") && txtjmlterkelola.getText().equals("") && txtjmlnotterkelola.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Data Tidak Boleh Kosong!");
            alert.showAndWait();
        }else{
            try {
                DataSampah data = new DataSampah();

                data.setNama_kota((String) cbnmkota.getValue());
                data.setJumlah_penduduk(Integer.parseInt(txtjmlpddk.getText()));
                data.setKatagori_sampah((String) cbktgrsampah.getValue());
                data.setJumlah_sampah_masuk(Integer.parseInt(txtjmlsampahmasuk.getText()));
                data.setTanggal_masuk(dptglmasuk.getValue());
                data.setJumlah_sampah_terkelola(Integer.parseInt(txtjmlterkelola.getText()));
                data.setJumlah_sampah_tidak_terkelola(Integer.parseInt(txtjmlnotterkelola.getText()));
                dataSampah.add(data);

                saveSampahDataToFile();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Data tersimpan!");
                alert.showAndWait();
                kosong();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR!");
                alert.setHeaderText(null);
                alert.setContentText("Cek Pengisian Data!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void itemsClick(MouseEvent event) {
        try {
            if (event.getClickCount() == 1) //Checking double click
            {
                cbnmkota.setValue(tvsampah.getSelectionModel().getSelectedItem().getNama_kota());
                txtjmlpddk.setText(String.valueOf(tvsampah.getSelectionModel().getSelectedItem().getJumlah_penduduk()));
                cbktgrsampah.setValue((String)(tvsampah.getSelectionModel().getSelectedItem().getKatagori_sampah()));
                txtjmlsampahmasuk.setText(String.valueOf(tvsampah.getSelectionModel().getSelectedItem().getJumlah_sampah_masuk()));
                dptglmasuk.setValue(tvsampah.getSelectionModel().getSelectedItem().getTanggal_masuk());
                txtjmlterkelola.setText(String.valueOf(tvsampah.getSelectionModel().getSelectedItem().getJumlah_sampah_terkelola()));
                txtjmlnotterkelola.setText(String.valueOf(tvsampah.getSelectionModel().getSelectedItem().getJumlah_sampah_tidak_terkelola()));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("click terlebih dahulu di index!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleButtonActionupdate(ActionEvent event) {
        if(cbnmkota.getValue().equals(null) && txtjmlpddk.getText().equals("") && cbktgrsampah.getValue().equals(null) && txtjmlsampahmasuk.getText().equals("") && txtjmlterkelola.getText().equals("") && txtjmlnotterkelola.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Data Tidak Boleh Kosong!");
            alert.showAndWait();
        } else{
            try {
                int selectedIndex = tvsampah.getSelectionModel().getSelectedIndex();
                DataSampah data = new DataSampah();

                data.setNama_kota((String) cbnmkota.getValue());
                data.setJumlah_penduduk(Integer.parseInt(txtjmlpddk.getText()));
                data.setKatagori_sampah((String) cbktgrsampah.getValue());
                data.setJumlah_sampah_masuk(Integer.parseInt(txtjmlsampahmasuk.getText()));
                data.setTanggal_masuk(dptglmasuk.getValue());
                data.setJumlah_sampah_terkelola(Integer.parseInt(txtjmlterkelola.getText()));
                data.setJumlah_sampah_tidak_terkelola(Integer.parseInt(txtjmlnotterkelola.getText()));
                tvsampah.getItems().set(selectedIndex, data);

                saveSampahDataToFile();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informasi");
                alert.setHeaderText(null);
                alert.setContentText("Data Terupdate!");
                alert.showAndWait();
                kosong();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(String.valueOf(e));
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleButtonActionadelete(ActionEvent event) {
        try {
            int selectedIndex = tvsampah.getSelectionModel().getSelectedIndex();
            DataSampah data = new DataSampah();

            tvsampah.getItems().remove(selectedIndex);

            saveSampahDataToFile();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Data Terhapus!");
            alert.showAndWait();
            kosong();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleButtonActionlogout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Peringatan!");
        alert.setHeaderText(null);
        alert.setContentText("Anda yakin ingin keluar ?");
        Optional<ButtonType> option = alert.showAndWait();
        
        if(option.get() == ButtonType.CANCEL){
            System.out.println("Kembali");
        }else if(option.get() == ButtonType.OK){
            Stage primaryStage = (Stage) tutup.getScene().getWindow();
            primaryStage.close();
        }       
    }
    
    public File getSampahFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(DashboardMenuController.class);
        String filePath = prefs.get("filepath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    public void setSampahFilePath(File f) {
        Preferences prefs = Preferences.userNodeForPackage(DashboardMenuController.class);
        if (f != null) {
            prefs.put("filePath", f.getPath());
        } else {
            prefs.remove("filePath");
        }
    }
    
    public void loadSampahDataFromFile() {
        try {
            File file = new File("C:/XML/DataSampah.xml");
            JAXBContext context = JAXBContext.newInstance(DataSampahWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            
            DataSampahWrapper wrapper = (DataSampahWrapper) um.unmarshal(file);
            
            if (wrapper.getListSampah()!= null) {
                dataSampah.clear();
                dataSampah.addAll(wrapper.getListSampah());
            }
            
            setSampahFilePath(file);
            
        } catch (Exception e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.showAndWait();
        }
    }
    
    public void saveSampahDataToFile() {
        try {
            File file = new File("C:/XML/DataSampah.xml");
            JAXBContext context = JAXBContext.newInstance(DataSampahWrapper.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            DataSampahWrapper wrapper = new DataSampahWrapper();

            wrapper.setListSampah(dataSampah);
            m.marshal(wrapper, file);

            setSampahFilePath(file);
        } catch (Exception e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.showAndWait();
        }
    }
    
    public ObservableList getData() {
        return dataSampah;        
    }
    
    public void popUpDisplayLogin(){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                
                stage.initStyle(StageStyle.UTILITY);
                stage.setScene(new Scene(root1));
                stage.showAndWait();
            }
            catch (IOException ex){
                Logger.getLogger(DashboardMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    void kosong(){
        cbnmkota.setValue(null);
        txtjmlpddk.setText("");
        cbktgrsampah.setValue(null);
        txtjmlsampahmasuk.setText("");
        dptglmasuk.setValue(LocalDate.now());
        txtjmlterkelola.setText("");
        txtjmlnotterkelola.setText("");
    }
}