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

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DataSampah")
public class DataSampahWrapper {
    private List<DataSampah> listSampah;
    
    @XmlElement(name = "DataSampah")
    public List<DataSampah> getListSampah() {
        return listSampah;
    }
    
    public void setListSampah(List<DataSampah> list) {
        listSampah = list;
    }
}
