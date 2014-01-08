/**
 * Copyright 2012 INdigital telecom
 * Creation Date: May 23, 2012
 */
package com.logslie.handler;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import com.logslie.dao.KeyspaceDao;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

/**
 * @author lauragarcia
 * 
 */
public class KeyspaceHandler {
	private Logger logger = Logger.getLogger(KeyspaceHandler.class);
	private ConnectionHandler connection;
	private String pathPackage;
	private String fullPath;

	/**
	 * @param connection
	 * @param pathPackage
	 */
	public KeyspaceHandler(ConnectionHandler connection, String pathPackage, String fullPath) {
		super();
		this.connection = connection;
		this.pathPackage = pathPackage;
		this.fullPath = fullPath;
	}

	/**
	 * Run Reverse Engineering
	 */
	public void runRE() {

		logger.info("runRE");
		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster",
				this.connection.getHost() + ":" + this.connection.getPort());

		KeyspaceDao keyspaceDao = new KeyspaceDao(cluster);

		// Get the Column Family
		List<String> listClass = keyspaceDao.getColumnsFamily(this.connection
				.getKeyspace());

		// Create a file for each keyspace
		for (int i = 0; i < listClass.size(); i++) {

			String className = listClass.get(i);
			ArrayList<ArrayList<String>> attListPair = keyspaceDao
					.getConstructorAttributes(this.connection.getKeyspace(),
							className);
			ArrayList<String> totalAttributes = this.getAttributes(attListPair);

			ModelClassHandler modelClassHandler = new ModelClassHandler();
			modelClassHandler.createModelFile(className, attListPair,
					totalAttributes, this.connection, this.pathPackage, this.fullPath);
			
			BusinessAccessHandler baHandler = new BusinessAccessHandler();
			baHandler.createBODAOFile(className, this.connection, this.pathPackage, this.fullPath);
			

		}

	}

	/**
	 * 
	 * 
	 * @param attListPair
	 * @return a list with the attributes of the class
	 */
	private ArrayList<String> getAttributes(
			ArrayList<ArrayList<String>> attListPair) {
		ArrayList<String> attributes = new ArrayList<String>();
		for (int i = 0; i < attListPair.size(); i++) {
			ArrayList<String> objectList = attListPair.get(i);
			for (int j = 0; j < objectList.size(); j++) {
				String object = objectList.get(j);
				if (!this.contains(object, attributes)) {
					attributes.add(object);
				}
			}

		}

		return attributes;
	}

	/**
	 * 
	 * @param object
	 * @param list
	 * @return true if the list contains the object
	 */
	private boolean contains(String object, ArrayList<String> list) {
		boolean found = false;
		int i = 0;

		while (!found && i < list.size()) {

			if (list.get(i).equalsIgnoreCase(object)) {
				found = true;
			}

			i++;
		}
		return found;
	}

}
