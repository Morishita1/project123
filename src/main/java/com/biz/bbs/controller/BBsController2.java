package com.biz.bbs.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.bbs.model.BBsDto2;
import com.biz.bbs.model.BBsVO2;
import com.biz.bbs.service.BBsService2;
import com.biz.bbs.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/bbs2")
public class BBsController2 {

	@Autowired
	BBsService2 bbsService;
	
	@Autowired
	FileService fileService;
	
	
	
	@ModelAttribute("bbsVO")
	public BBsVO2 newBBsVO2() {
		return new BBsVO2();
	}
	@RequestMapping(value = "/list2", method = RequestMethod.GET)
	public String list(Model model) {
		
		List<BBsDto2> bbsList = bbsService.bbsList();
		model.addAttribute("LIST2",bbsList);
		model.addAttribute("BODY","BBS_LIST2");
		return "home";
		
	}
	
	
	@RequestMapping(value = "/write2", method = RequestMethod.GET)
	public String write(@ModelAttribute("bbsVO") BBsVO2 bbsVO, Model model) {
		
		LocalDateTime ldt = LocalDateTime.now();
		String curDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
		String curTime = ldt.format(DateTimeFormatter.ofPattern("HH-mm-ss")).toString();
		bbsVO.setBbs_date(curDate);
		bbsVO.setBbs_time(curTime);
		model.addAttribute("bbsVO",bbsVO);
		model.addAttribute("BODY","BBS_WRITE2");
		return "home";
	}
	
	@RequestMapping(value = "/write2", method = RequestMethod.POST)
	public String write_up(@ModelAttribute BBsVO2 bbsVO, //@RequestParam("bbs_file") List<MultipartFile> files, 
			Model model) {
		
		int ret= bbsService.insert(bbsVO);
		return "redirect:/bbs2/list2";
	}
	
	@RequestMapping(value = "/view2", method = RequestMethod.GET)
	public String view(@RequestParam long bbs_seq, Model model) {
		BBsDto2 bbsDto= bbsService.getContent(bbs_seq);
		model.addAttribute("BBS",bbsDto);
		model.addAttribute("BODY","BBS_VIEW2");
		return "home";
	}
	
	@RequestMapping(value = "/update2", method = RequestMethod.GET)
	public String update(@RequestParam long bbs_seq, Model model) {
		
		BBsDto2 bbsDto = bbsService.getContent(bbs_seq);
		model.addAttribute("bbsVO",bbsDto);
		model.addAttribute("BODY","BBS_WRITE2");
		return "home";
	}
	
	@RequestMapping(value = "/update2", method = RequestMethod.POST)
	public String update(@ModelAttribute BBsVO2 bbsVO, Model model) {
		
		int ret = bbsService.update(bbsVO);
		
		return "redirect:/bbs2/list2";
	}
	
	
	
	@RequestMapping(value = "/delete2", method = RequestMethod.GET)
	public String delete(@RequestParam long bbs_seq) {
		
		int ret = bbsService.delete(bbs_seq);
		
		return "redirect:/bbs2/list2";
	}
	
	
}

