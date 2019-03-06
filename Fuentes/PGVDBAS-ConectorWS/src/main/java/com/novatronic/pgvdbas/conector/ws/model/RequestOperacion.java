/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.pgvdbas.conector.ws.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author caujapuclla
 */


public class RequestOperacion implements Serializable{
    
    private String trama;

    public RequestOperacion() {
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    @Override
    public String toString() {
        return "RequestOperacion{" + "trama=" + trama + '}';
    }
    
}
