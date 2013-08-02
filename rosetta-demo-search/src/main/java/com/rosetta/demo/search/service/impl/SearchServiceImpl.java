package com.rosetta.demo.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.dam.handler.standard.pdf.PdfHandler;
import com.day.cq.search.Predicate;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.NameConstants;
import com.rosetta.demo.search.service.SearchService;

@Component(immediate=true, label="Rosetta Demo Search Service")
@Service
public class SearchServiceImpl implements SearchService {

	private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);


	@Reference
	private QueryBuilder builder;
	
	private static final String WORDX_FORMAT = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	private static final String WORD_FORMAT = "application/msword";
	private static final String ASSET_FORMAT_PROPERTY = "@" + JcrConstants.JCR_CONTENT + "/"
	        + DamConstants.METADATA_FOLDER + "/@" + DamConstants.DC_FORMAT;

	public SearchResult search(Session session, int pageIndex, String textToLookFor, List<String> searchInPaths,
	        int maxHitsPerPage) {

		SearchResult searchResult = null;

		// replace underscore and hyphen with space, because search ignores this characters, so it won't work when using
		// wildcard (*)
		textToLookFor = textToLookFor.replace("-", " ").replace("_", " ");

		// create query description as hash map (simplest way, same as form post)
		Map<String, String> queryMap = new HashMap<String, String>();

		// filter out the hidden pages
		queryMap.put("property", "jcr:content/hideInNav");
		queryMap.put("property.value", "true");
		queryMap.put("property.operation", "not");

		queryMap.put("fulltext", "*" + textToLookFor + "*");
		queryMap.put("group.p.or", "true");
		queryMap.put("group.1_type", NameConstants.NT_PAGE);
		queryMap.put("group.2_type", DamConstants.NT_DAM_ASSET);

		// add filter to show only asset type pdf or word
		queryMap.put("group.2_group.property", ASSET_FORMAT_PROPERTY);
		queryMap.put("group.2_group.property.1_value", PdfHandler.CONTENT_MIMETYPE);
		queryMap.put("group.2_group.property.2_value", WORD_FORMAT);
		queryMap.put("group.2_group.property.3_value", WORDX_FORMAT);

		// add paths where to look for
		queryMap.put("2_group.p.or", "true");
		int index = 1;
		for (String path : searchInPaths) {
			queryMap.put("2_group." + index + "_path", path);
			index++;
		}

		// sorting by date, newest first
		queryMap.put("orderby", "@" + JcrConstants.JCR_CONTENT + "/" + NameConstants.PN_PAGE_LAST_MOD);
		queryMap.put("orderby.sort", Predicate.SORT_DESCENDING);

		try {
			PredicateGroup group = PredicateGroup.create(queryMap);
			Query query = builder.createQuery(group, session);
			query.setStart(pageIndex);
			query.setHitsPerPage(maxHitsPerPage);
			searchResult = query.getResult();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return searchResult;
	}

}