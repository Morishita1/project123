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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.biz.bbs.model.BBsDto;
import com.biz.bbs.model.BBsVO;
import com.biz.bbs.service.BBsService;
import com.biz.bbs.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/bbs")
public class BBsController {

	@Autowired
	BBsService bbsService;
	
	@Autowired
	FileService fileService;
	
	
	@ModelAttribute("bbsVO")
	public BBsVO newBBsVO() {
		return new BBsVO();
	}

	
	@RequestMapping(value = "/album", method = RequestMethod.GET)
	public String album(Model model) {
		
		List<BBsDto> bbsList = bbsService.bbsListForFile();
		model.addAttribute("LIST",bbsList);
		model.addAttribute("BODY","BBS_ALBUM");
		return "home";
		
	}

		
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@ModelAttribute("bbsVO") BBsVO bbsVO, Model model) {
		
		LocalDateTime ldt = LocalDateTime.now();
		String curDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
		String curTime = ldt.format(DateTimeFormatter.ofPattern("HH-mm-ss")).toString();
		bbsVO.setBbs_date(curDate);
		bbsVO.setBbs_time(curTime);
		model.addAttribute("bbsVO",bbsVO);
		model.addAttribute("BODY","BBS_WRITE");
		return "home";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write_up(@ModelAttribute BBsVO bbsVO, //@RequestParam("bbs_file") List<MultipartFile> files, 
			Model model) {
		
		int ret= bbsService.insert(bbsVO);
		return "redirect:/bbs/album";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(@RequestParam long bbs_seq, Model model) {
		BBsDto bbsDto= bbsService.getContent(bbs_seq);
		model.addAttribute("BBS",bbsDto);
		model.addAttribute("BODY","BBS_VIEW");
		return "home";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(@RequestParam long bbs_seq, Model model) {
		
		BBsDto bbsDto = bbsService.getContent(bbs_seq);
		model.addAttribute("bbsVO",bbsDto);
		model.addAttribute("BODY","BBS_WRITE");
		return "home";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute BBsVO bbsVO, Model model) {
		
		int ret = bbsService.update(bbsVO);
		
		return "redirect:/bbs/album";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam long bbs_seq) {
		
		int fret = fileService.deletes(bbs_seq);
		int ret = bbsService.delete(bbs_seq);
		
		return "redirect:/bbs/album";
	}
	
	@ResponseBody
	@RequestMapping(value = "/file_delete")
	public String file_delete(long file_seq) {
		
		boolean okDelete = fileService.file_delete(file_seq);
		if(okDelete) return "OK";
		else return "FAIL";
	}
	
	
}

