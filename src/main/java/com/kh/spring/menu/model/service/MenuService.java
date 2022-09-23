package com.kh.spring.menu.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

	public static final String MENU_BASE_URL = "http://localhost:10000/springboot/menu";
	
	@Autowired
	ResourceLoader resourceLoader;
	
	public Resource selectMenuList() {

		return resourceLoader.getResource(MENU_BASE_URL);  // UrlResource 객체
	}

	public Resource findByType(String type) {
		return resourceLoader.getResource(MENU_BASE_URL + "/type/" + type);
	}
	
	public Resource findByTypeAndTaste(String type, String taste) {
		return resourceLoader.getResource(MENU_BASE_URL + "/type/" + type + "/taste/" + taste);
	}


}
