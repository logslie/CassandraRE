/**
 * Copyright 2012 INdigital telecom
 * Creation Date: May 25, 2012
 */
package com.indigital.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @author lauragarcia
 * 
 */
public class ModelClassHandler {

	private Logger logger = Logger.getLogger(ModelClassHandler.class);

	/**
	 * 
	 */
	public ModelClassHandler() {
		super();

	}
	
	/**
	 * Create a class with a keyspace mapped
	 * 
	 * @param columnFamily
	 * @param attListPair
	 * @param totalAttributes
	 * @param connection
	 * @param pathPackage
	 */

	public void createModelFile(String columnFamily,
			ArrayList<ArrayList<String>> attListPair,
			ArrayList<String> totalAttributes, ConnectionHandler connection,
			String pathPackage, String fullPath) {

		logger.info("CreateModelFile of:" + columnFamily);

		// example: SIMPLE_COMMENT
		String tableName = columnFamily;

		// example: KunderaExamples@cassandra_pu
		String schema = connection.getKeyspace() + "@"
				+ connection.getPersistenceUnit();

		FileWriter fstream;
		try {

			String className = getPlainName(tableName);
			className = className+"_"+connection.getKeyspace();
			String path = pathPackage.replace(".", "/");
			String modelPath = fullPath+"/"+path+"/model";
			boolean success = new File(modelPath).mkdirs();
			logger.info(success);
			String modelFile = modelPath+"/"+className + ".java";
			fstream = new FileWriter(modelFile);
			logger.info("bofile:"+modelFile);

			BufferedWriter out = new BufferedWriter(fstream);
			// Package
			out.write("package " + pathPackage + ".model;\n");

			// Imports
			out.write("import java.io.Serializable;\n"
					+ "import javax.persistence.Entity;\n"
					+ "import javax.persistence.Id;\n"
					+ "import javax.persistence.Column;\n"
					+ "import javax.xml.bind.annotation.XmlRootElement;\n"
					+ "import javax.persistence.Table;\n");

			// Head
			out.write("/**\n* @author\n*\n*/\n\n");

			out.write("@Entity\n");
			out.write("@Table(name = \"" + tableName + "\", schema = \""
					+ schema + "\")\n @XmlRootElement(name = \"" + className
					+ "\")\n");

			out.write("public class " + className
					+ " implements Serializable {\n");
			out.write("\n");
			out.write("private static final long serialVersionUID = 1L;\n");
			out.write("\n");
			out.write("@Id\n");
			out.write("@Column(name = \"" + tableName + "_ID\")\n");
			out.write("private String id;\n");
			out.write("\n");
			ArrayList<String> attributesList = new ArrayList<String>();

			for (int i = 0; i < totalAttributes.size(); i++) {
				String att = totalAttributes.get(i);
				out.write("@Column(name = \"" + att + "\")\n");
				att = getPlainName(att);
				att = toAttribute(att);
				out.write("private String " + att + ";\n");
				attributesList.add(att);
				out.write("\n");

			}

			// Constructor without fields
			out.write("public " + className + "() {\n}\n");
			out.write("\n");
			// Constructor with fields

			for (int k = 0; k < attListPair.size(); k++) {
				out.write("public " + className + "(String id ");
				ArrayList<String> subList = attListPair.get(k);
				for (int j = 0; j < subList.size(); j++) {
					out.write(", String "
							+ toAttribute(getPlainName(subList.get(j))));
				}

				out.write(") {\n");
				out.write("\n");
				out.write("this.id = id;\n");

				for (int j = 0; j < subList.size(); j++) {
					out.write("this."
							+ toAttribute(getPlainName(subList.get(j))) + " = "
							+ toAttribute(getPlainName(subList.get(j))) + ";\n");

				}
				out.write("\n");
				out.write("}\n");

				out.write("\n\n");
			}

			// Getters and Setters

			// Get
			out.write("/**\n* @return the id\n*/\n public String getId() { \n return this.id;\n }\n");
			out.write("\n\n");
			// Set
			out.write("/**\n* @param id the id to set \n*/ \n public void setId(String id){\n this.id = id;\n}");
			out.write("\n\n");

			for (int j = 0; j < attributesList.size(); j++) {

				String attribute = attributesList.get(j);
				// Get
				out.write("/**\n* @return the "
						+ attribute
						+ "id\n*/\n public String get"
						+ attribute.subSequence(0, 1).toString().toUpperCase()
						+ attribute.subSequence(1, attribute.length())
								.toString() + "() { \n return this."
						+ attribute + ";\n }\n");
				out.write("\n\n");
				// Set
				out.write("/**\n* @param "
						+ attribute
						+ " the "
						+ attribute
						+ " to set \n*/ \n public void set"
						+ attribute.subSequence(0, 1).toString().toUpperCase()
						+ attribute.subSequence(1, attribute.length())
								.toString() + "(String " + attribute
						+ "){\n this." + attribute + " = " + attribute + ";\n}");
				out.write("\n\n");
			}

			out.write("}\n");

			// Close the output stream
			
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("IOException:" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Create a string without '_' character
	 * 
	 * @param name
	 * @return
	 */
	private String getPlainName(String name) {

		String plainName = "";

		String[] splitName = name.split("_");
		for (int i = 0; i < splitName.length; i++) {

			String subName = splitName[i];
			String word = subName.subSequence(0, 1).toString().toUpperCase()
					+ subName.subSequence(1, subName.length()).toString()
							.toLowerCase();
			plainName = plainName + word;
		}
		return plainName;
	}

	/**
	 * Create a string con the fist letter to lower case
	 * 
	 * @param name
	 * @return
	 */
	private String toAttribute(String name) {

		String plainName = "";
		plainName = name.subSequence(0, 1).toString().toLowerCase()
				+ name.subSequence(1, name.length()).toString();
		return plainName;

	}

}
