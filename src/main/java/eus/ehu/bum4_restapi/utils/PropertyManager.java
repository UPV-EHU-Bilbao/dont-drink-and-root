/*
 * This file is part of the Project-MastodonFX project.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @authors - Geru-Scotland (Basajaun) | Github: https://github.com/geru-scotland
 *          - Unai Salaberria          | Github: https://github.com/unaisala
 *          - Martin Jimenez           | Github: https://github.com/Matx1n3
 *          - Iñaki Azpiroz            | Github: https://github.com/iazpiroz15
 *          - Diego Forniés            | Github: https://github.com/DiegoFornies
 *
 */

package eus.ehu.bum4_restapi.utils;

import java.io.*;
import java.util.Properties;

public class PropertyManager {
    private static final String RESOURCE_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = new FileInputStream(RESOURCE_FILE)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("[PROPERTY-MANAGER] There was an error loading the file:" + RESOURCE_FILE);
        }
    }

    public static String getProperty(Constants key) throws IOException {
        String value = PROPERTIES.getProperty(key.getKey());

        if(value != null)
            return value.replace("${basePath}", PROPERTIES.getProperty(Constants.BASE_PATH.getKey()));
        throw new IOException();
    }

    /**
     *
     * @param key
     * @param value
     */
    public static void setProperty(String key, String value) {
        try(OutputStream outputStream = new FileOutputStream(RESOURCE_FILE)){
            PROPERTIES.setProperty(key, value);
            PROPERTIES.store(outputStream, null);
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("[PROPERTY-MANAGER] There was an error loading the file:" + RESOURCE_FILE);
        }
    }
}
