package com.arthur.mestrado.revisaosistematica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates a file with tabs (\t), this file will be used to
 * create a .csv file of its content.
 * 
 * @author arthur
 *
 */
public class CreateTabDocument {
	private final String DIR_BIB_FILES;
	private final String TABBED_FILE;
	public CreateTabDocument(String file, String tabbed) {
		DIR_BIB_FILES = file;
		TABBED_FILE = tabbed;
		populateList();
		System.out.println("INSTACE CREATED, FILE IS: " + DIR_BIB_FILES);
	}
	public CreateTabDocument() {
		DIR_BIB_FILES = "/Users/arthur/revisao/bibfiles/"; //default path
		TABBED_FILE = "/Users/arthur/revisao/tabbed-acm.txt"; //default path
		populateList();
		System.out.println("INSTACE CREATED, FILE IS: " + DIR_BIB_FILES);
	}
	
	private void populateList() {
		columns.add("author");
		columns.add("title");
		columns.add("booktitle");
		columns.add("series");
		columns.add("year");
		columns.add("isbn");
		columns.add("location");
		columns.add("pages");
		columns.add("numpages");
		columns.add("url");
		columns.add("doi");
		columns.add("acmid");
		columns.add("publisher");
		columns.add("address");
		columns.add("keywords");
	}
	
	public void createTabFile() throws IOException {
		File[] files = (new File(DIR_BIB_FILES)).listFiles();
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(TABBED_FILE)));
		
		for(int i = 0 ; i < files.length ; i++) {
			readSingleFile(files[i], writer);
		}
		
		writer.close();
	}
	
	private void readSingleFile(File file, BufferedWriter writer) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		content = new HashMap<String, String>();
		while( (line = reader.readLine()) != null) {
			line = line.replace("{", "");
			line = line.replace("}", "");
			String[] aux = line.split("=");
			if(aux.length == 2) {
				String key = aux[0].replace(" ", ""); String value = aux[1].substring(1, aux[1].length() - 1);
				content.put(key, value);
			}
		}
		if(content.size() > 0) {
			createLineOnFinalFile(writer);
		}
		reader.close();
	}

	private void createLineOnFinalFile(BufferedWriter writer) throws IOException {
		String line = "";
		for(int i = 0 ; i < columns.size() ; i++) {
			String key = columns.get(i);
			if(content.containsKey(key) == true) {
				line += content.get(key) + "\t";
			} else {
				line += "\t";
			}
		}
		System.out.println("WRITING LINE " + line);
		writer.write(line);
		writer.newLine();
		writer.flush();
	}

	private ArrayList<String> columns = new ArrayList<String>();
	private HashMap<String, String> content = new HashMap<String, String>();
	
	public static void main(String...args) {
		CreateTabDocument doc = new CreateTabDocument();
		try {
			doc.createTabFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
