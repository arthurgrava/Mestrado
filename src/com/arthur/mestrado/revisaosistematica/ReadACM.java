package com.arthur.mestrado.revisaosistematica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadACM {

	private final String DIRECTORY_URL;
	private final String FINAL_FILE;
	private BufferedWriter writer = null;
	private BufferedReader reader = null;

	public ReadACM(String directory, String file) {
		DIRECTORY_URL = directory;
		FINAL_FILE = file;
		readContentAndCreateFile();
	}

	private void readContentAndCreateFile() {
		System.out.println("-------------\tINITIATING\t-------------");
		System.out.println();
		
		File directory = new File(DIRECTORY_URL);
		File file = new File(FINAL_FILE);
		try {
			file.createNewFile();
			if(directory.isDirectory()) {
				writer = new BufferedWriter(new FileWriter(file));
				File[] files = directory.listFiles();
				for(int i = 0 ; i < files.length ; i++) {
					readThisFile(files[i]);
				}
				writer.close();
			} else {
				System.out.println("THAT URL DOES NOT REPRESENT A DIRECTORY: " + DIRECTORY_URL);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println("-------------\tENDED\t\t-------------");
	}

	private void readThisFile(File file) {
		String name = file.getName();
		name = name.substring(0, name.indexOf("."));
		System.out.println("FILE NAME IS: " + name);
		try {
			Integer.parseInt(name);
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
				writer.flush();
			}
			reader.close();
		} catch (NumberFormatException e) {
			System.out.println("THIS FILE IS NOT A BIB TEX");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ok(){
		System.out.println("OK");
	}
	
	public static void main (String [] args) {
		String dir = "/Users/arthur/revisao/bibfiles/";
		String fil = "/Users/arthur/revisao/compressed-bib-files.txt";
		ReadACM acm = new ReadACM(dir, fil);
		acm.ok();
	}

}
