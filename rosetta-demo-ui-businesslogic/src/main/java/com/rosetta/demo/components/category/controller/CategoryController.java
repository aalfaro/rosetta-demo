package com.rosetta.demo.components.category.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rosetta.demo.components.category.entity.Category;
import com.rosetta.demo.components.generic.entity.GenericConfiguration;

public class CategoryController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

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
		
		String url = "http://localhost/wcs/resources/store/10152/categoryview/@top";
		int timeout = 5000;
		
		String json = getJSON(url, timeout);
		System.out.println("json : " + json);
		
		if (StringUtils.isNotBlank(json)) {
			topCategories = parseJSON(json);
		}

		return topCategories;
	}
	
	
	private String getJSON(String url, int timeout) {
	    try {
	        URL u = new URL(url);
	        HttpURLConnection c = (HttpURLConnection) u.openConnection();
	        c.setRequestMethod("GET");
	        c.setRequestProperty("Content-length", "0");
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	        c.setConnectTimeout(timeout);
	        c.setReadTimeout(timeout);
	        c.connect();
	        int status = c.getResponseCode();

	        switch (status) {
	            case 200:
	            case 201:
	                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
	                StringBuilder sb = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null) {
	                    sb.append(line+"\n");
	                }
	                br.close();
	                return sb.toString();
	        }

	    } catch (MalformedURLException e) {
	    	log.error(e.getMessage(), e);
	    } catch (IOException e) {
	    	log.error(e.getMessage(), e);
	    }
	    return null;
	}
	
	
	private List<Category> parseJSON(String json) {
		
		final String JSON_FIELD_TOP_CATEGORIES = "CatalogGroupView";
		final String JSON_FIELD_CATEGORY_NAME = "name";
		
		List<Category> topCategories = new ArrayList<Category>();
		
		if (StringUtils.isNotBlank(json)) {
			try {
				JSONObject jsonObject = new JSONObject(json);
				if (jsonObject.has(JSON_FIELD_TOP_CATEGORIES)){
					JSONArray jsonArray = jsonObject.getJSONArray(JSON_FIELD_TOP_CATEGORIES);
					for(int i=0;i<jsonArray.length();i++){
						Category category = new Category();
						JSONObject jsonCat = jsonArray.getJSONObject(i);
						category.setName(jsonCat.getString(JSON_FIELD_CATEGORY_NAME));
						topCategories.add(category);
					}
				}
			} catch (JSONException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return topCategories;
	}

}
