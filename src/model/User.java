/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Acer
 */
public class User {
    private String nama;
    private String username;
    private String password;
    private String akses;

    public User(String nama, String username, String password, String akses) {
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.akses = akses;
    }
    
    public User(){
        this("", "", "", "");
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAkses() {
        return akses;
    }

    public void setAkses(String akses) {
        this.akses = akses;
    }
    
    
}
