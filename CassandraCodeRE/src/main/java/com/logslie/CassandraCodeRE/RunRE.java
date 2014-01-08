package com.logslie.CassandraCodeRE;

import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.logslie.handler.ConnectionHandler;
import com.logslie.handler.KeyspaceHandler;

/**
 * This class run the code reverse engineering of a Keyspace from Cassandra
 * database
 * 
 */
public class RunRE {

	private final static Logger logger = Logger.getLogger(RunRE.class);
	private static Document persistenceFile;
	private static String pathPackage;
	private static String fullPath;
	private static ArrayList<ConnectionHandler> connections = new ArrayList<ConnectionHandler>();

	public RunRE(){
		
	}
	
	public static void main(String[] args) {
		SAXReader reader = new SAXReader();
		File file;

		if (args.length == 3) {

			file = new File(args[0]);
			pathPackage = args[1];
			fullPath = args[2];
			
			
			

			try {
				persistenceFile = reader.read(file);

				// Read the persistenceFile
				readPersistenceFile(persistenceFile);

				// Start the startReverseEngineering process
				startReverseEngineering();

			} catch (DocumentException e) {
				logger.error("DocumentException:" + e.getMessage());
				// e.printStackTrace();
			}

		} else {

			logger.error("Usage java -jar CassandraCodeRE.jar persistence.xml name.package.example");

		}

	}
	
	public void runRE(String[] args){
		
		SAXReader reader = new SAXReader();
		File file;

		if (args.length == 3) {

			file = new File(args[0]);
			pathPackage = args[1];
			fullPath = args[2];
			logger.info("arg0:"+args[0]);
			logger.info("arg1:"+args[1]);
			logger.info("arg2:"+args[2]);

			try {
				persistenceFile = reader.read(file);

				// Read the persistenceFile
				readPersistenceFile(persistenceFile);

				// Start the startReverseEngineering process
				startReverseEngineering();

			} catch (DocumentException e) {
				logger.error("DocumentException:" + e.getMessage());
				// e.printStackTrace();
			}

		} else {

			logger.error("Usage java -jar CassandraCodeRE.jar persistence.xml name.package.example");

		}
		
	}

	/**
	 * Start the reverse engineering for each connection
	 */
	private static void startReverseEngineering() {

		logger.info("startReverseEngineering..." + connections.size());
		for (int i = 0; i < connections.size(); i++) {
			ConnectionHandler connection = connections.get(i);
			KeyspaceHandler ksHandler = new KeyspaceHandler(connection,
					pathPackage, fullPath);
			ksHandler.runRE();

		}
	}

	/**
	 * Read the persistenc.xml file and get the instance of Connection
	 * 
	 * @param file
	 *            Persistence.xml where is the data connection
	 */


	private static void readPersistenceFile(Document file) {

		Element rootElement = file.getRootElement();
		Iterator iterator = rootElement.elements("persistence-unit").iterator();
		ConnectionHandler connection;
		while (iterator.hasNext()) {
			Element elem = (Element) iterator.next();
			// Name persistence-unit
			String name = elem.attributeValue("name");
			String host = "";
			String port = "";
			String keyspace = "";
			Iterator propertiesIt = elem.elements("properties").iterator();
			while (propertiesIt.hasNext()) {
				Element propsNode = (Element) propertiesIt.next();
				Iterator propertyIt = propsNode.elements("property").iterator();
				while (propertyIt.hasNext()) {
					Element prop = (Element) propertyIt.next();
					if (prop.attributeValue("name").equalsIgnoreCase("kundera.nodes")) {
						host = prop.attributeValue("value");
					} else if (prop.attributeValue("name").equalsIgnoreCase("kundera.port")) {
						port = prop.attributeValue("value");
					} else if (prop.attributeValue("name").equalsIgnoreCase("kundera.keyspace")) {
						keyspace = prop.attributeValue("value");
					}

				}
			}
			connection = new ConnectionHandler(host, port, keyspace, name);
			connections.add(connection);

		}

		
			
		
	}
}
