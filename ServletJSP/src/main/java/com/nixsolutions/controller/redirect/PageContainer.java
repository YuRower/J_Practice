package com.nixsolutions.controller.redirect;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.nixsolutions.util.Path;

public class PageContainer {

	private static final Logger LOGGER = Logger.getLogger(PageContainer.class);

	private static Map<String, String> pages = new TreeMap<String, String>();

	static {
		pages.put("new_user", Path.USER_PROFILE);
		pages.put("pageNotFound", Path.ERROR_PAGE);
	}

	public static String get(String pageName) {
		if (pageName == null || !pages.containsKey(pageName)) {
			LOGGER.trace("Page not found, name --> " + pageName);
			return pages.get("pageNotFound");
		}
		return pages.get(pageName);
	}
}
