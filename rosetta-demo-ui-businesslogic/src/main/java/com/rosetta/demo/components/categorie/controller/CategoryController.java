package com.rosetta.demo.components.categorie.controller;

import java.util.ArrayList;
import java.util.List;

import com.rosetta.demo.components.categorie.entity.Category;
import com.rosetta.demo.components.generic.entity.GenericConfiguration;

public class CategoryController {
	
	/**
	 * Method to get the top categories.
	 * 
	 * http://localhost/wcs/resources/store/10152/categoryview/@top
	 * 
	 * @param configuration GenericConfiguration object
	 * @return List with the top categories
	 */
	public List<Category> getTopCategories(GenericConfiguration configuration) {

		List<Category> topCategories = new ArrayList<Category>();

		return topCategories;
	}

}
