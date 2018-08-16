package com.server.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Config {
	static boolean read = false;

	public static boolean parseConfig() {
		if (read == false) {
			String fileName = "C:\\config.ini";
			String line = null;
			try {
				System.out.println("HEY");
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line = bufferedReader.readLine()) != null) {
					if (line.split(";")[0].equals("URL")) {
						MySQLUtil.URL = line.split(";")[1];
					} else if (line.split(";")[0].equals("username")) {
						MySQLUtil.username = line.split(";")[1];
					} else if (line.split(";")[0].equals("password")) {
						MySQLUtil.password = line.split(";")[1];
					}
				}
				bufferedReader.close();
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open file '" + fileName + "'");
				return false;
			} catch (IOException ex) {
				System.out.println("Error reading file '" + fileName + "'");
				return false;
			}
			read = true;
		}
		return true;
	}

}
