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
		//List<BBsVO> bbsList = bDao.selectAll();
		List<BBsDto2> bbsList = bDao.selectAll();
		return bbsList;
	}


	/*
	 * 글쓰기를 수행할때 file을 같이 upload를 수행하면
	 * 해당하는 글의 bbs_seq를 조회하여
	 * file table에 저장할때 같이 저장을 수행 해줘야 한다.
	 * 
	 * 1. 게시글을 저장하고
	 * 2. 게시글의 bbs_seq를 조회하여
	 * 3. 파일정보를 insert할때 해당하는 bbs_seq를 같이 저장해주어야 한다.
	 * 그래야 게시글을 조회할때 해당하는 파일들을 같이 조회할수 있다
	 */
	public int insert(BBsVO2 bbsVO) {

		// 1. 게시글을 저장
		//		저장후에는 bbsVo.bbs_seq에 새로 생성된 PK값이 담겨있다
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
