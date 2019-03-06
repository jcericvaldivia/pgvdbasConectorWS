/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.pgvdbas.conector.ws.integration.impl;

import com.novatronic.pgvdbas.conector.ws.integration.IntegradorConectorCore;
import com.novatronic.pgvdbas.conector.ws.model.RequestOperacion;
import com.novatronic.pgvdbas.conector.ws.model.ResponseOperacion;
import com.novatronic.pgvdbas.conector.ws.util.Parameter;
import com.novatronic.pgvdbas.conector.ws.util.ParametrosConectorWS;
import com.novatronic.pussdtr.conector.controller.CoreConectorControlador;
import com.novatronic.pussdtr.conector.dto.OperacionDto;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caujapuclla
 */
public class IntegradorDependencia implements IntegradorConectorCore {
    
    private static final Logger LOG = LoggerFactory.getLogger(IntegradorDependencia.class);
    
    @Override
    public ResponseOperacion procesar(RequestOperacion requestOperacion, Map paramDeCom) {
        
        LOG.debug("Entrando requestOperacion(requestOperacion={})", requestOperacion);
        
        ResponseOperacion responseOperacion = new ResponseOperacion();
        CoreConectorControlador controladorConectorBase = new CoreConectorControlador();
        
        OperacionDto dto = new OperacionDto(requestOperacion.getTrama());
        dto.setComponenteOrigen(ParametrosConectorWS.getProperty(Parameter.PARAM_ID_COMPONENTE));
        dto.setUniversoParametros(paramDeCom);
        
        String respuestaConectorCore = controladorConectorBase.procesarOperacion(dto);
        responseOperacion.setTrama(respuestaConectorCore);
        
        LOG.debug("Retornando responseOperacion(): ({})", responseOperacion);
        
        return responseOperacion;
    }
    
}
