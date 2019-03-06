/**
 * Novatronic S.A.C Todos los derechos reservados www.novatronic.com
 */
package com.novatronic.pgvdbas.conector.ws.listener;

import com.novatronic.estandares.config.ConfigurationHelper;
import com.novatronic.pussdtr.conector.cache.ManejadorCache;
import com.novatronic.pussdtr.conector.errorcode.DiccionarioCodigoError;
import com.novatronic.pussdtr.conector.formatter.ConfiguradorFormatter;
import com.novatronic.pussdtr.conector.integration.pool.PoolDistribuidorPort;
import com.novatronic.pussdtr.conector.util.ParametrosConectorBase;
import com.novatronic.pgvdbas.conector.ws.exception.StartContextException;
import com.novatronic.pgvdbas.conector.ws.util.ParametrosConectorWS;
import com.novatronic.estandares.helper.ResourceHelper;
import com.novatronic.pussdtr.conector.util.ParametrosExtraComunicacion;
import com.novatronic.pgvdbas.conector.ws.util.Parameter;
import java.net.URL;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caujapuclla
 */
@WebListener
public class ConectorWSListener implements ServletContextListener {

    private static final String conectorWsConfig = "ConectorWSConfig.properties";
    private static final String conectorBaseConfig = "ConectorBaseConfig.properties";
    private static final String conectorBaseFormatterConfig = "formatConector.xml";
    private static final String conectorBaseErrorCode = "codigoError.properties";

    private static final Logger LOG = LoggerFactory.getLogger(ConectorWSListener.class);

    // Llaves para inicializar la cache
    private interface ConfigConectorBase {

        String CACHE_CONFIG_NAME = "CacheConfigName";
        String CACHE_CONFIG_FILE = "CacheConfigFile";
    }

    /**
     * {@inheritDoc }
     *
     * @param sce {@inheritDoc }
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext sc;
        sc = sce.getServletContext();

        ConfigurationHelper.init();
        LOG.info("conectorHttpConfig: [{}]", "VALOR PRUEBA");

        try {
            iniciarConectorHttp();
        } catch (Exception e) {
            destruirInstanciasConector();
            throw new StartContextException("Error al iniciar la configuracion cgenbas", e);
        }

        LOG.info("Proceso de inicio realizado correctamente");
    }

    /**
     * {@inheritDoc }
     *
     * @param sce {@inheritDoc }
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ConfigurationHelper.stop();

        destruirInstanciasConector();
//        LOG.info("Conexto Conector WS finalizado");
    }

    private void iniciarConectorHttp() {
        URL pathConectorWsConfig;
        URL pathConectorBaseConfig;

        LOG.debug("Inicializando contexto Conector WS");

        pathConectorWsConfig = ResourceHelper.find(conectorWsConfig);
        LOG.debug("Parameter conectorWsConfig URL:[{}]", pathConectorWsConfig);

        ParametrosConectorWS.init(pathConectorWsConfig);
        LOG.debug("Parametros Conector WS cargados PATH: [{}]", pathConectorWsConfig);

        pathConectorBaseConfig = ResourceHelper.find(conectorBaseConfig);
        LOG.debug("Parameter pathConectorBaseConfig URL:[{}]", pathConectorBaseConfig);

        ParametrosConectorBase.init(pathConectorBaseConfig);
        LOG.debug("Parametros Conector Base cargados PATH: [{}]", pathConectorBaseConfig);

        String configFile = ParametrosConectorBase.getProperty(ConfigConectorBase.CACHE_CONFIG_FILE);
        String cacheName = ParametrosConectorBase.getProperty(ConfigConectorBase.CACHE_CONFIG_NAME);

        ManejadorCache.iniciarCache(configFile, cacheName);
        LOG.info("Cache Conector Base iniciada: [{}], [{}]", configFile, cacheName);

        ConfiguradorFormatter.initResources(conectorBaseFormatterConfig);
        LOG.debug("Componente Formateador inicializado");

        DiccionarioCodigoError.initResources(conectorBaseErrorCode);
        LOG.info("Parametros de Error cargados");

        PoolDistribuidorPort.instanciarPorts();
        LOG.info("Configuracion del pool DistribuidorPorts inicializado");

        String origenComponente = ParametrosConectorWS.getProperty(Parameter.PARAM_ID_COMPONENTE);
        ParametrosExtraComunicacion.inicializar(Integer.parseInt(origenComponente));
        LOG.info("Parametros de Comunicacion cargados");

        LOG.info("Contexto iniciado Conector WS");
    }

    private void destruirInstanciasConector() {

        LOG.debug("Finalizando contexto Conector WS...");
        try {
            ManejadorCache.destroy();
            LOG.debug("Cache del Conector WS destruido");

            ParametrosConectorBase.destroy();
            LOG.debug("Parametros de Conector Base finalizado");

            ParametrosConectorWS.destroy();
            LOG.debug("Parametros de Conector WS finalizado");

            PoolDistribuidorPort.limpiar();
            LOG.info("Pool DistribuidorPorts limpiado");

            ParametrosExtraComunicacion.limpiar();
            LOG.info("Parametros de Comunicacion limpiados");

            LOG.info("Contexto Conector WS finalizado");
        } catch (Exception e) {
            LOG.error("Contexto Conector WS finalizado con error", e);
        }
    }

}
