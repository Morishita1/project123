package com.biz.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.bbs.mapper.BBsDao2;
import com.biz.bbs.model.BBsDto;
import com.biz.bbs.model.BBsDto2;
import com.biz.bbs.model.BBsVO2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BBsService2 {

	@Autowired
	BBsDao2 bDao;
	
	
	public List<BBsDto2> bbsList() {
		List<BBsDto2> bbsList = bDao.selectAll();
		return bbsList;
	}


	public int insert(BBsVO2 bbsVO) {

		int ret = bDao.insert(bbsVO);
		
		return ret;
		}
	
	public int update(BBsVO2 bbsVO) {
		
	
		int ret = bDao.update(bbsVO);
		
		return ret;
	}	
	
	public int delete(long bbs_seq) {
		
		int ret = bDao.delete(bbs_seq);
		
		return ret;
	}

	public BBsDto2 getContent(long bbs_seq) {
		BBsDto2 bbsDto = bDao.findBySeqForFile(bbs_seq);
		return bbsDto;
	}
	
}
