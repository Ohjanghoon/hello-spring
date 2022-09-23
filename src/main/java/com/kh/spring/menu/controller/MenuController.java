package com.kh.spring.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.menu.model.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	MenuService menuService;
	
	@GetMapping("/menu.do")
	public void menu() {}
	
	@GetMapping("/menuList.do")
	public ResponseEntity<?> menuList(){
		
		return ResponseEntity.ok(menuService.selectMenuList());
	}
	
	@GetMapping("/findByType.do")
	public ResponseEntity<?> findByType(@RequestParam String type){
		Resource resource = menuService.findByType(type);

		if(!resource.exists()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(resource);
	}
	
	@GetMapping("/findByTypeAndTaste.do")
	public ResponseEntity<?> findByTypeAndTaste(@RequestParam String type, @RequestParam String taste){
		Resource resource = menuService.findByTypeAndTaste(type, taste);
		
		if(!resource.exists()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(resource);
	}
}