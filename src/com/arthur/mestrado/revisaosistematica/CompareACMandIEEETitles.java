package com.arthur.mestrado.revisaosistematica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class CompareACMandIEEETitles {
	private final String ACM_FILE;
	private final String IEEE_FILE;
	private final String DUPLICATED_PATH;
	private final String NON_DUPLICATED_PATH;

	private ArrayList<String> duplicated = new ArrayList<String>();
	private ArrayList<String> notDuplicated = new ArrayList<String>();
	private ArrayList<String> acm = new ArrayList<String>();
	private ArrayList<String> ieee = new ArrayList<String>();

	public CompareACMandIEEETitles() {
		ACM_FILE = "/Users/arthur/revisao/compressed-bib-titles-file.txt";
		IEEE_FILE = "/Users/arthur/revisao/ieee-article-titles.txt";
		DUPLICATED_PATH = "/Users/arthur/revisao/duplicated.txt";
		NON_DUPLICATED_PATH = "/Users/arthur/revisao/non-duplicated.txt";
		System.out.println("PATHS- ACM{" + ACM_FILE + "} IEEE{" + IEEE_FILE + "}");
	}

	public CompareACMandIEEETitles(String acm, String ieee, String dupl, String nonDup) {
		ACM_FILE = acm;
		IEEE_FILE = ieee;
		DUPLICATED_PATH = dupl;
		NON_DUPLICATED_PATH = nonDup;
		System.out.println("PATHS- ACM{" + ACM_FILE + "} IEEE{" + IEEE_FILE + "}");
	}

	private ArrayList<String> readTitlesFile(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(path)));

		String title = "";
		ArrayList<String> array = new ArrayList<String>();

		while((title = reader.readLine()) != null) {
			array.add(title);
		}
		reader.close();

		System.out.println("LINES{" + array.size() + "} PATH{" + path + "}");

		return array;
	}

	public void seekForDuplicated() {
		try {
			acm = readTitlesFile(ACM_FILE);
			ieee = readTitlesFile(IEEE_FILE);
			separateDuplicated();
			writeCleannedFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void writeCleannedFiles() throws IOException {
		BufferedWriter w1 = new BufferedWriter(new FileWriter(new File(DUPLICATED_PATH)));
		BufferedWriter w2 = new BufferedWriter(new FileWriter(new File(NON_DUPLICATED_PATH)));

		for(int i = 0 ; i < duplicated.size() ; i++) {
			w1.write(duplicated.get(i));
			w1.newLine();
			w1.flush();
		}
		w1.close();
		
		for(int i = 0 ; i < notDuplicated.size() ; i++) {
			w2.write(notDuplicated.get(i));
			w2.newLine();
			w2.flush();
		}
		w2.close();
	}

	private void separateDuplicated() {
		String acm_title, ieee_title;
		for(int i = 0 ; i < acm.size() ; i++) {
			acm_title = acm.get(i).toLowerCase();
			boolean have_equal = false;
			for(int j = 0 ; j < ieee.size() ; j++) {
				ieee_title = ieee.get(j).toLowerCase();
				int length = acm_title.length() > ieee_title.length() ? acm_title.length() : ieee_title.length();
				int distance = StringUtils.getLevenshteinDistance(acm_title, ieee_title);
				double ratio = (double) distance / (double) length;
				if(ratio < 0.20) {
					duplicated.add(acm.get(i));
					have_equal = true;
					System.out.println("\nEQUALS: ACM{" + acm_title + "} IEEE{" + ieee_title + "}");
				} else {
					System.out.print(".");
				}
				if(have_equal)
					break;
			}
			if(!have_equal)
				notDuplicated.add(acm.get(i));
		}
	}

	public static void main(String[] args) {
		CompareACMandIEEETitles comp = new CompareACMandIEEETitles();
		comp.seekForDuplicated();
	}
}
