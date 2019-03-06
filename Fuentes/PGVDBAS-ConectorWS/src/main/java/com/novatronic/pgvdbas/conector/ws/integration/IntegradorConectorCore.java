/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.pgvdbas.conector.ws.integration;


import com.novatronic.pgvdbas.conector.ws.model.RequestOperacion;
import com.novatronic.pgvdbas.conector.ws.model.ResponseOperacion;
import java.util.Map;

/**
 *
 * @author caujapuclla
 */
public interface IntegradorConectorCore {
    
    ResponseOperacion procesar(RequestOperacion requestOperacion, Map paramDeCom);
    
}