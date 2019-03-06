/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.novatronic.pgvdbas.conector.ws.service;

import com.novatronic.pgvdbas.conector.ws.integration.IntegradorConectorCore;
import com.novatronic.pgvdbas.conector.ws.integration.impl.IntegradorDependencia;
import com.novatronic.pgvdbas.conector.ws.model.RequestOperacion;
import com.novatronic.pgvdbas.conector.ws.model.ResponseOperacion;
import com.novatronic.pgvdbas.conector.ws.util.Parameter;
import com.novatronic.pgvdbas.conector.ws.util.ParametrosConectorWS;
import com.novatronic.pussdtr.conector.errorcode.CodigoErrorUtil;
import com.novatronic.pussdtr.conector.logging.LoggerPerformance;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author evaldivia
 */
@RestController
public class ConectorWS {

    private static final Logger LOG = LoggerFactory.getLogger(ConectorWS.class);

    @RequestMapping(value = "/PGVDBAS-ConectorWS/operacion/procesar", method = RequestMethod.POST)

    public ResponseOperacion procesarOperacion(@RequestBody RequestOperacion requestOperacion) {
        IntegradorConectorCore integradorConectorCore;
        ResponseOperacion response;
        Map paramDeCom = new HashMap();
        long inicio = 0;

        try {
            LOG.info("========== INI - CONECTOR WS PROCESANDO POR RECURSO ==========");

            inicio = System.currentTimeMillis();
            integradorConectorCore = new IntegradorDependencia();
            response = new ResponseOperacion();

            response = integradorConectorCore.procesar(requestOperacion, paramDeCom);
        } catch (Exception e) {
            //Estableciendo la respuesta a un error generico
            response = new ResponseOperacion();
            String origenRespuesta = ParametrosConectorWS.getProperty(Parameter.PARAM_ID_COMPONENTE);
            response.setTrama(CodigoErrorUtil.generarRespuestaErrorGenerico(origenRespuesta));
            LOG.error("[ConectorWS] Error al procesar el Request", e);
        } finally {
            LoggerPerformance.show("Ejecucion ConectorWS", inicio);
            LOG.info("========== FIN - CONECTOR WS PROCESANDO POR RECURSO ==========");
        }

        return response;
    }

}
