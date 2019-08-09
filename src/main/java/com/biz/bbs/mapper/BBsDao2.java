package com.biz.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.bbs.model.BBsDto2;
import com.biz.bbs.model.BBsVO2;

public interface BBsDao2 {

	@Select(" SELECT * " + " FROM tbl_bbs1 " + "WHERE bbs_main_seq = 0 " + " ORDER BY bbs_date DESC, bbs_time DESC ")
	@Results(value = {
			@Result(property = "bbs_seq", column = "bbs_seq"),
			@Result(property = "bbs_rep_list", column = "bbs_seq", javaType = List.class,
			many = @Many(select = "getRepList"))
	})
	public List<BBsDto2> selectAll();

	@InsertProvider(type = BBsSQL2.class, method = "bbs_insert_sql")
	@SelectKey(keyProperty = "bbs_seq", statement = " SELECT SEQ_BBS.NEXTVAL FROM DUAL ", resultType = Long.class,before = true)
	public int insert(BBsVO2 bbsVO);

	@Select(" SELECT * FROM tbl_bbs1 WHERE bbs_seq = #{bbs_seq} ")
	public BBsVO2 findBySeq(long bbs_seq);

	
		
	@Select("  SELECT * FROM tbl_bbs1 WHERE bbs_main_seq > 0 AND bbs_main_seq = #{bbs_main_seq} ")
	public List<BBsVO2> getRepList(long bbs_main_seq);
	
	@UpdateProvider(type = BBsSQL2.class, method = "bbs_update_sql")
	public int update(BBsVO2 bBsVO);
	
	@Delete(" DELETE FROM tbl_bbs1 WHERE bbs_seq = #{bbs_seq} ")
	public int delete(long bbs_seq);

	@Select(" SELECT * FROM tbl_bbs1 WHERE bbs_seq = #{bbs_seq} ")
	@Results(value = {
			@Result(property = "bbs_seq", column = "bbs_seq")
	})
	public BBsDto2 findBySeqForFile(long bbs_seq);
}
