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
	
	
	
	/*
	 * 현재의 controller내의 어떤 메서드에서
	 * BBsVO를 객체로 취급하여 값을 setter, getter 하려고 시도할때
	 * 만약 객체가 초기화 되지 않아 NullPointException이 발생하려고 하면
	 * 이 메서드가 자동으로 호출되어 bbsVO 객체를 생성 및 초기화 한다
	 */
	@ModelAttribute("bbsVO")
	public BBsVO2 newBBsVO2() {
		return new BBsVO2();
	}
	@RequestMapping(value = "/list2", method = RequestMethod.GET)
	public String list(Model model) {
		
		//List<BBsVO> bbsList = bbsService.bbsList();
		List<BBsDto2> bbsList = bbsService.bbsList();
		model.addAttribute("LIST2",bbsList);
		model.addAttribute("BODY","BBS_LIST2");
		return "home";
		
	}
	
	
	@RequestMapping(value = "/write2", method = RequestMethod.GET)
	public String write(@ModelAttribute("bbsVO") BBsVO2 bbsVO, Model model) {
		
		//LocalDate ld = LocalDate.now();
		//LocalTime lt= LocalTime.now();
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
		
//		
//		log.debug("파일개수:" + files.size());
//		
//		for(MultipartFile f : files) {
//			log.debug("파일명 : " + f.getOriginalFilename());
//		}
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
		
		//LocalDate ld = LocalDate.now();
		//LocalTime lt= LocalTime.now();
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
	
	
	/*
	 * PathVariable
	 * GET 방식으로 서버에 데이터를 보낼때
	 * ?변수=값 형식으로 만이 사용을 한다.
	 * 그런데 이 방식에 보안이 취약점이 될수 있다.
	 * 
	 * path/값 형식의 주소처럼 서버로 데이터를 보내고
	 * 컨트롤러에서는 주소의 일부를 값으로 인식하여
	 * 변수에 할당하는 방식이다.
	 */
	
	@RequestMapping(value = "/delete2", method = RequestMethod.GET)
	public String delete(@RequestParam long bbs_seq) {
		
		int ret = bbsService.delete(bbs_seq);
		
		return "redirect:/bbs2/list2";
	}
	
	
	@RequestMapping(value = "/reply/{bbs_seq}", method = RequestMethod.GET)
	public String reply(@PathVariable long bbs_seq, Model model) {
		
		BBsDto2 bbsDto = bbsService.getContent(bbs_seq);
		
		BBsVO2 bbsVO = new BBsVO2();
		
		LocalDateTime ldt = LocalDateTime.now();
		String curDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
		String curTime = ldt.format(DateTimeFormatter.ofPattern("HH-mm-ss")).toString();
		bbsVO.setBbs_date(curDate);
		bbsVO.setBbs_time(curTime);
		// 답글달기에서는 bbs_main_seq에 원글의 bbs_seq값을 포함해야한다
		bbsVO.setBbs_main_seq(bbs_seq);
		bbsVO.setBbs_title("re"+bbsDto.getBbs_title());
		
		model.addAttribute("bbsVO",bbsVO);
		model.addAttribute("BODY","BBS_WRITE2");
		
		return "home";
		
	}
	
	@RequestMapping(value = "/reply/{bbs_seq}", method = RequestMethod.POST)
	public String reply(@PathVariable long bbs_seq, @ModelAttribute BBsVO2 bbsVO, Model model) {
		
		bbsVO.setBbs_main_seq(bbs_seq);
		bbsService.insert(bbsVO);
		return "redirect:/bbs/list2";
	}
	
}

