package com.rosetta.demo.search.service;

import java.util.List;

import javax.jcr.Session;

import com.day.cq.search.result.SearchResult;

public interface SearchService {

	/**
	 * Search for content that matches the text.
	 * 
	 * @param session
	 * @param pageIndex
	 * @param textToLookFor
	 * @param searchInPaths
	 * @param maxHitsPerPage
	 * @return SearchResult result.
	 */
	SearchResult search(Session session, int pageIndex, String textToLookFor, List<String> searchInPaths,
	        int maxHitsPerPage);

}