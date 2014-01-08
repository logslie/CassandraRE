/**
 * Copyright 2012 INdigital telecom
 * Creation Date: May 23, 2012
 */
package com.logslie.handler;

import me.prettyprint.hector.api.Cluster;

/**
 * 
 * 
 * @author lauragarcia
 *
 */
public class ConnectionHandler {
	
	private String host;
	private String port;
	private String keyspace;
	private String persistenceUnit;
	private Cluster cluster;
	
	
	
	
	/**
	 * @param clusterName
	 * @param host
	 * @param port
	 * @param keyspace
	 * @param persistenceUnit
	 * @param cluster
	 */
	public ConnectionHandler(String host, String port,
			String keyspace, String persistenceUnit) {
		super();
		
		this.host = host;
		this.port = port;
		this.keyspace = keyspace;
		this.persistenceUnit = persistenceUnit;
	}
	
	

	/**
	 * @return the persistenceUnit
	 */
	public String getPersistenceUnit() {
		return persistenceUnit;
	}

	/**
	 * @param persistenceUnit the persistenceUnit to set
	 */
	public void setPersistenceUnit(String persistenceUnit) {
		this.persistenceUnit = persistenceUnit;
	}



	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the keyspace
	 */
	public String getKeyspace() {
		return keyspace;
	}

	/**
	 * @param keyspace the keyspace to set
	 */
	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	/**
	 * @return the cluster
	 */
	public Cluster getCluster() {
		return cluster;
	}

	/**
	 * @param cluster the cluster to set
	 */
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	

}
