package com.arthur.mestrado.revisaosistematica;

/**
 * Creates a file with tabs (\t), this file will be used to
 * create a .csv file of its content.
 * 
 * @author arthur
 *
 */
public class CreateTabDocument {
	private final String MERGED_FILE_PATH;
	public CreateTabDocument(String file) {
		MERGED_FILE_PATH = file;
		System.out.println("INSTACE CREATED, FILE IS: " + MERGED_FILE_PATH);
	}
	public CreateTabDocument() {
		MERGED_FILE_PATH = "/Users/arthur/revisao/compressed-bib-files.txt"; //default path
		System.out.println("INSTACE CREATED, FILE IS: " + MERGED_FILE_PATH);
	}
}
