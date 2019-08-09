package com.biz.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.bbs.mapper.BBsDao;
import com.biz.bbs.model.BBsDto;
import com.biz.bbs.model.BBsVO;
import com.biz.bbs.model.FileVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BBsService {

	@Autowired
	BBsDao bDao;
	
	@Autowired
	FileService fileService;
	
	public List<BBsDto> bbsList() {
		List<BBsDto> bbsList = bDao.selectAll();
		return bbsList;
	}

	public List<BBsDto> bbsListForFile() {
		List<BBsDto> bbsList = bDao.selectAllForFile();
		return bbsList;
	}
	public int insert(BBsVO bbsVO) {

		int ret = bDao.insert(bbsVO);
		if(bbsVO.getBbs_files().size() > 0 && !bbsVO.getBbs_files().get(0).getOriginalFilename().isEmpty()) {
			fileService.uploadFileList(bbsVO);
			
		}
		
		return ret;
		}
	
	public int update(BBsVO bbsVO) {
		
		if(bbsVO.getBbs_files().size() > 0 && !bbsVO.getBbs_files().get(0).getOriginalFilename().isEmpty()) {
			
			fileService.uploadFileList(bbsVO);
			
		}	
		int ret = bDao.update(bbsVO);
		
		return ret;
	}	
	
	public int delete(long bbs_seq) {
		
		int ret = bDao.delete(bbs_seq);
		
		return ret;
	}

	public BBsDto getContent(long bbs_seq) {

		BBsDto bbsDto = bDao.findBySeqForFile(bbs_seq);
		return bbsDto;
	}
}
