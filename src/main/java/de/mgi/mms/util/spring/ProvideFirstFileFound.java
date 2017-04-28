package de.mgi.mms.util.spring;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * You can use this bean if you intend to configure a bean with more than one
 * file resource.
 * 
 * e.g.: The first for the prod environment and the second for your test
 * environment
 * 
 * 
 * @author Johannes.Hoehne
 * 
 */
public class ProvideFirstFileFound {

	private File firstExistingFile = null;

	private List<String> locations = new ArrayList<String>();

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	/**
	 * Will return the first existing file
	 * 
	 * @return
	 */
	public File getFile() {
		if (firstExistingFile != null) {
			return firstExistingFile;
		}

		for (String location : locations) {
			location = location.trim();
			final File first = new File(location);
			if (first.exists()) {
				this.firstExistingFile = first;
				return this.firstExistingFile;
			}

			final URL resource = ProvideFirstFileFound.class.getResource(location);
			if (resource != null) {
				this.firstExistingFile = new File(resource.getFile());
				return this.firstExistingFile;
			}
		}

		throw new IllegalArgumentException("Non of your defined resources exists: " + this.locations);
	}

}
