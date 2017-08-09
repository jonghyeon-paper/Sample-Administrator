package com.sample.administrator.model.code.persistence;

import java.util.List;

import com.sample.administrator.model.code.entity.Code;

public interface CodeMapper {

	List<Code> selectList(Code code);
	Code selectOne(Code code);
	int insert(Code code);
	int update(Code code);
	int delete(Code code);
}
