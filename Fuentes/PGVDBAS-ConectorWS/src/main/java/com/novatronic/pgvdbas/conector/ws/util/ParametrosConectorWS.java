/**
 * Novatronic S.A.C Todos los derechos reservados www.novatronic.com
 */
package com.novatronic.pgvdbas.conector.ws.util;

import com.novatronic.pussdtr.conector.util.PropertyUtil;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author evaldivia
 */
public final class ParametrosConectorWS {

    private static Properties propiedadesConfiguracion = null;

    private ParametrosConectorWS() {
    }

    /**
     * Carga el archivo properties que contiene los datos generales del proyecto
     *
     * @param url URL que apunta a la ruta con las propiedades a cargar
     */
    public static void init(URL url) {
        propiedadesConfiguracion = PropertyUtil.loadPropertiesFromURL(url);
    }

    /**
     * Se obtiene el valor asignado a key
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return PropertyUtil.getProperty(propiedadesConfiguracion, key);
    }

    public static void destroy() {
        if (propiedadesConfiguracion != null) {
            propiedadesConfiguracion.clear();
        }
    }

}
