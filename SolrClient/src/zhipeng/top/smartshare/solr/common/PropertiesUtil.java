package zhipeng.top.smartshare.solr.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * PropertiesUtil
 * 
 * @author shaozhipeng
 * 
 */
public class PropertiesUtil {
	protected static final Log logger = LogFactory.getLog(PropertiesUtil.class);

	private PropertiesUtil() {
	}

	private static Properties props;

	static {
		props = new Properties();
		try {
			String filePath = getFilePath()
					+ "/resource/solr_config.properties";
			logger.info("读取solr_config.properties的路径->" + filePath);
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return props.getProperty(key);
	}

	private static String getFilePath() {
		File file = new File("");
		try {
			return file.getCanonicalFile().getAbsolutePath();
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
