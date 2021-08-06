/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.DataLocalDate;

/**
 *
 * @author Acer
 */
public class DataSampah {
    private SimpleStringProperty  nama_kota;
    private SimpleIntegerProperty jumlah_penduduk;
    private SimpleStringProperty katagori_sampah;
    private SimpleIntegerProperty jumlah_sampah_masuk;
    private SimpleObjectProperty <LocalDate> tanggal_masuk;
    private SimpleIntegerProperty jumlah_sampah_terkelola;
    private SimpleIntegerProperty jumlah_sampah_tidak_terkelola;

    public DataSampah(String nama_kota, int jumlah_penduduk, String katagori_sampah, int jumlah_sampah_masuk, LocalDate tanggal_masuk, int jumlah_sampah_terkelola, int jumlah_sampah_tidak_terkelola) {
        this.nama_kota = new SimpleStringProperty(nama_kota);
        this.jumlah_penduduk = new SimpleIntegerProperty(jumlah_penduduk);
        this.katagori_sampah = new SimpleStringProperty (katagori_sampah);
        this.jumlah_sampah_masuk = new SimpleIntegerProperty (jumlah_sampah_masuk);
        this.tanggal_masuk = new SimpleObjectProperty<>(tanggal_masuk);
        this.jumlah_sampah_terkelola = new SimpleIntegerProperty (jumlah_sampah_terkelola);
        this.jumlah_sampah_tidak_terkelola = new SimpleIntegerProperty (jumlah_sampah_tidak_terkelola);
    }
    
    public DataSampah(){
        this("", 0, "", 0, null, 0, 0);
    }

    public String getKatagori_sampah() {
        return katagori_sampah.get();
    }

    public String getNama_kota() {
        return nama_kota.get();
    }

    public int getJumlah_penduduk() {
        return jumlah_penduduk.get();
    }

    public int getJumlah_sampah_masuk() {
        return jumlah_sampah_masuk.get();
    }
    
    @XmlJavaTypeAdapter(DataLocalDate.class)
    public LocalDate getTanggal_masuk() {
        return tanggal_masuk.get();
    }

    public int getJumlah_sampah_terkelola() {
        return jumlah_sampah_terkelola.get();
    }

    public int getJumlah_sampah_tidak_terkelola() {
        return jumlah_sampah_tidak_terkelola.get();
    }   

    public void setNama_kota(String nama_kota) {
        this.nama_kota.set(nama_kota);
    }

    public void setJumlah_penduduk(int jumlah_penduduk) {
        this.jumlah_penduduk.set(jumlah_penduduk);
    }

    public void setKatagori_sampah(String katagori_sampah) {
        this.katagori_sampah.set(katagori_sampah);
    }

    public void setJumlah_sampah_masuk(int jumlah_sampah_masuk) {
        this.jumlah_sampah_masuk.set(jumlah_sampah_masuk);
    }

    public void setTanggal_masuk(LocalDate tanggal_masuk) {
        this.tanggal_masuk.set(tanggal_masuk);
    }

    public void setJumlah_sampah_terkelola(int jumlah_sampah_terkelola) {
        this.jumlah_sampah_terkelola.set(jumlah_sampah_terkelola);
    }

    public void setJumlah_sampah_tidak_terkelola(int jumlah_sampah_tidak_terkelola) {
        this.jumlah_sampah_tidak_terkelola.set(jumlah_sampah_tidak_terkelola);
    }
}
