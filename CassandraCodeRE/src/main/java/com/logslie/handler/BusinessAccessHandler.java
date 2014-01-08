/**
 * Copyright 2012 INdigital telecom
 * Creation Date: May 25, 2012
 */
package com.logslie.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author lauragarcia
 * 
 */
public class BusinessAccessHandler {
	private Logger logger = Logger.getLogger(BusinessAccessHandler.class);

	/**
	 * 
	 */
	public BusinessAccessHandler() {
		super();
	}

	public void createBODAOFile(String columnFamily, ConnectionHandler connection,
			String pathPackage, String fullPath) {
		logger.info("CreateModelFile of:" + columnFamily);
		String tableName = columnFamily;
		FileWriter fstream,fstreamdao;
		try {
			String className = getPlainName(tableName);
			className = className+"_"+connection.getKeyspace();
			String path = pathPackage.replace(".", "/");
			String boPath = fullPath+"/"+path+"/bo";
			String daoPath = fullPath+"/"+path+"/dao";
			boolean success = new File(boPath).mkdirs();
			
			logger.info(success);
			success = new File(daoPath).mkdirs();
			String bofile = boPath+"/"+className+"BO" + ".java";
			String daofile = daoPath+"/"+className+"DAO" + ".java";
		
			fstream = new FileWriter(bofile);
			fstreamdao = new FileWriter(daofile);
			
			logger.info("bofile:"+bofile);
			logger.info("daofile:"+daofile);
			BufferedWriter out = new BufferedWriter(fstream);
			BufferedWriter outDao = new BufferedWriter(fstreamdao);
			
			// Package
			out.write("package " + pathPackage + ".bo;\n");
			outDao.write("package " + pathPackage + ".dao;\n");
			
			out.write("\n");
			outDao.write("\n");
			
			// Import 
			out.write("import java.util.List;\n");
			out.write("import "+pathPackage+".model."+className+";\n");	
			out.write("public interface "+className+"BO {\n");
			
			outDao.write("import java.util.List;\n");
			outDao.write("import "+pathPackage+".model."+className+";\n");
			outDao.write("public interface "+className+"DAO {\n");
			
			out.write("\n");
			outDao.write("\n");
			
			// Add
			out.write("public void addElement("+className+" element);\n");
			outDao.write("public void addElement("+className+" element);\n");
			
			out.write("\n");
			outDao.write("\n");
			
			// Delete
			out.write("public void deleteElement(String id);\n");
			outDao.write("public void deleteElement(String id);\n");
			
			out.write("\n");
			outDao.write("\n");
			
			// GetElementByID
			out.write("public "+className+" getElementById(String id);\n");
			outDao.write("public "+className+" getElementById(String id);\n");
			
			out.write("\n");
			outDao.write("\n");
			
			// GetAllElements
			out.write("public List<"+className+"> getAllElement();\n");
			outDao.write("public List<"+className+"> getAllElement();\n");
			
			out.write("\n");
			outDao.write("\n");
			
			out.write("}\n");
			outDao.write("}\n");
			
			
			out.flush();
			outDao.flush();
			out.close();
			outDao.close();
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


}
