package com.github.legionivo.testtask.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentUtils {
	public static void create(String browser, String url) {

		Properties properties = new Properties();
		properties.setProperty("Browser", browser);
		properties.setProperty("Base URL", url);
		properties.setProperty("Operating System", OsUtils.getOsName());
		FileWriter writer = null;
		File path = new File("build/allure-results/");
		try {
			FileUtils.forceMkdir(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			writer = new FileWriter("build/allure-results/environment.properties");
			properties.store(writer, "See https://github.com/allure-framework/allure-docs/blob/master/docs/features/environment.adoc");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}


