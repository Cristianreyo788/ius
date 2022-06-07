package com.bbva.libranza.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	 public static List<String> getLines(File file) throws IOException {
	    Reader fr = new FileReader(file);
	    BufferedReader br = new BufferedReader(fr);
	    List<String> lines = new ArrayList<String>();
	    while(br.ready()) {
	    	lines.add(br.readLine());
	    }
	    br.close();
	    fr.close();
	    return lines;
    }
}
