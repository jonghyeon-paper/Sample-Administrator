package com.skplanet.iba.domain.code;

import java.util.List;

public interface CodeMapper {

	List<Code> selectList(Code code);
	Code selectOne(Code code);
	int insert(List<Code> code);
	int update(Code code);
	int delete(Code code);
}
