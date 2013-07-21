package com.rosetta.demo.components.category.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.rosetta.demo.components.category.entity.Category;
import com.rosetta.demo.components.generic.entity.GenericConfiguration;

public class CategoryController {

	/**
	 * Method to get the top categories.
	 * 
	 * http://localhost/wcs/resources/store/10152/categoryview/@top
	 * 
	 * @param configuration
	 *            GenericConfiguration object
	 * @return List with the top categories
	 */
	public List<Category> getTopCategories(GenericConfiguration configuration) {

		List<Category> topCategories = new ArrayList<Category>();

		try {

			URL url = new URL(
					"http://localhost/wcs/resources/store/10152/categoryview/@top");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return topCategories;
	}

}
