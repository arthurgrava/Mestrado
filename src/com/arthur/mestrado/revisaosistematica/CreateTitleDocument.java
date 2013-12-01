package com.arthur.mestrado.revisaosistematica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Seek for all titles imported from ACM and create a unique file
 * containing the titles of the articles. 
 * 
 * @author arthur
 *
 */
public class CreateTitleDocument {
	private final String MERGED_FILE_PATH;
	private final String DESTINATION_PATH;
	public CreateTitleDocument(String file, String dest) {
		MERGED_FILE_PATH = file;
		DESTINATION_PATH = dest;
		System.out.println("INSTACE CREATED, FILE IS: " + MERGED_FILE_PATH + " | " + DESTINATION_PATH);
	}
	public CreateTitleDocument() {
		MERGED_FILE_PATH = "/Users/arthur/revisao/compressed-bib-files.txt"; //default path
		DESTINATION_PATH = "/Users/arthur/revisao/compressed-bib-titles-file.txt";
		System.out.println("INSTACE CREATED, FILE IS: " + MERGED_FILE_PATH);
	}
	
	public void createFileWithTitles() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(MERGED_FILE_PATH)));
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(DESTINATION_PATH)));
			
			String line = "";
			Integer article = 1;
			while((line = reader.readLine()) != null) {
				if(line.startsWith(" title = ")) {
					String title = line.substring(line.indexOf("{") + 1, line.indexOf("}"));
					System.out.println("WROTE (" + article + "):{" + title + "} of LINE {" + line + "}");
					writer.write(title);
					writer.newLine();
					writer.flush();
					article++;
				}
			}
			
			writer.close();
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CreateTitleDocument acm = new CreateTitleDocument();
		acm.createFileWithTitles();
	}
}
