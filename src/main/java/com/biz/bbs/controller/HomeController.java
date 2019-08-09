package com.biz.bbs.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.bbs.model.BBsDto;
import com.biz.bbs.model.BBsDto2;
import com.biz.bbs.model.MenuDto;
import com.biz.bbs.service.BBsService;
import com.biz.bbs.service.BBsService2;
import com.biz.bbs.service.MenuService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	BBsService bbsService;
	
	@Autowired
	BBsService2 bbsService2;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpSession httpSession) {
		
		List<MenuDto> menuList = menuService.getDBMenu();
		List<BBsDto> bbsList = bbsService.bbsListForFile();
		model.addAttribute("LIST",bbsList);
		List<BBsDto2> bbsList2 = bbsService2.bbsList();
		model.addAttribute("LIST2",bbsList2);
		httpSession.setAttribute("MENUS", menuList);
		return "home";
	}
	
}
