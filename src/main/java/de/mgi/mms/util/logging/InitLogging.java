package de.mgi.mms.util.logging;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import de.mgi.mms.util.spring.ProvideFirstFileFound;

/**
 * this utility class is able to use different configuration files of the log4j
 * frame work
 * 
 * use it like this in your spring configuration
 * 
 * <code>
 * 
 * <bean 
 * 		class="de.mgi.mms.catbuyer.util.InitLogging" 
 * 		init-method="init"
 * 		lazy-init="false">
 * 		
 * 			<property 
 * 				name="configurationFile"
 * 				value="/oracle/projects/iasde.mgi.shs.eb.iasversion/de.mgi.shs.eb.project/de.mgi.shs.eb.country/de.mgi.shs.eb.salesline/de.mgi.shs.eb.version/appconfig/catbuyer_ws_log4j.xml" />
 * 	</bean>
 * 
 * </code>
 * 
 * The Trick of this logging initialization is that it will scan the log4j
 * configuration file every fife minutes. So you are able to change the logging
 * level without any restart of your container.
 * 
 * @author Johannes.Hoehne
 * 
 */
public class InitLogging {

	private String configurationFile;

	private ProvideFirstFileFound provideFirstFileFound;

	/**
	 * singleton instance
	 */
	private static boolean initLogging = false;

	/**
	 * singleton
	 */
	public InitLogging() {
	}

	public void setConfigurationFile(String configurationFile) {
		this.configurationFile = configurationFile;
	}

	public void setProvideFirstFileFound(ProvideFirstFileFound provideFirstFileFound) {
		this.provideFirstFileFound = provideFirstFileFound;
	}

	public synchronized void init() {
		if (initLogging) {
			return;
		}

		initLogging = true;
		if (configurationFile == null && provideFirstFileFound == null) {
			initDefaultLogger(null);
			return;
		}

		File file = (configurationFile != null) ? new File(configurationFile) : provideFirstFileFound.getFile();
		if (!file.isFile()) {
			initDefaultLogger(null);
			return;
		}

		try {
			LogManager.resetConfiguration();
			// delay of fife minutes
			DOMConfigurator.configureAndWatch(file.getAbsolutePath(), (5 * 60 * 1000));
			Logger logger = Logger.getLogger(InitLogging.class);
			logger.info("Logging is initialized with the following configuration file: " + file.getAbsolutePath());

		} catch (Exception e) {
			initDefaultLogger(e);
		}

		return;
	}

	private static void initDefaultLogger(Exception e) {
		Logger logger = Logger.getLogger(InitLogging.class);
		if (e != null) {
			logger.warn("While we tried to initialize the logger we got a exception, so we went back to default logging", e);
		}
		logger.info("Default logging is initialized");
	}

}
