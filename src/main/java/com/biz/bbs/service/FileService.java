package com.biz.bbs.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biz.bbs.mapper.FileDao;
import com.biz.bbs.model.BBsVO;
import com.biz.bbs.model.FileVO;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class FileService {

	@Autowired
	FileDao fDao;
	
	private String upLoadFolder = "c:/bizwork/upload/";
	
	
	public void uploadFileList(BBsVO bbsVO) {
		
		
		List<MultipartFile> files = bbsVO.getBbs_files();
		long bbs_seq = bbsVO.getBbs_seq();
		
		
		for(MultipartFile file : files) {
			
			String originName =file.getOriginalFilename();
			
			String uuString= UUID.randomUUID().toString();
			
			String savaName = uuString + "_" +originName;
			
			File uploadFile = new File(upLoadFolder, savaName);
			
			
				try {
					
					file.transferTo(uploadFile);
					
					
					fDao.insert(FileVO.builder()
							.file_bbs_seq(bbs_seq) 
							.file_name(savaName) 
							.file_origin_name(originName).build()); 
						
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			
		}
	
	}
	
	


	public boolean file_delete(long file_seq) {
		
		FileVO fileVO = fDao.findBySeq(file_seq);
		File delFlie = new File(upLoadFolder, fileVO.getFile_name());
		
		if(delFlie.exists()) {
			delFlie.delete();
			
			fDao.delete(file_seq);
			
			return true;
		}
		return false;
	}




	public int deletes(long bbs_seq) {

			
			int fret = fDao.deletes(bbs_seq);
			return fret;
	
	}
	
	
	
}
