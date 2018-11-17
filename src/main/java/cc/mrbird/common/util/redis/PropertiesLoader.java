/**
 * 
 */
package cc.mrbird.common.util.redis;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载Redis配置文件
 * 
 * 
 */
public class PropertiesLoader {
	private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    private static String REDIS_CONFIGURATION_FILE = "/redis.properties";

    private static Properties propertie = null;

    static {
        InputStream inputStream = PropertiesLoader.class.getResourceAsStream(REDIS_CONFIGURATION_FILE);
        try {
            if (inputStream != null) {
                propertie = new Properties();
                propertie.load(inputStream);
            }
        } catch (IOException e) {
        	logger.error("read file failed!", e);
        } finally {
        	try {
        		if(inputStream != null){
        			inputStream.close();
        		}
			} catch (IOException e) {
			}
        }
    }

    public static Properties getPropertie() {
        return propertie;
    }

}
