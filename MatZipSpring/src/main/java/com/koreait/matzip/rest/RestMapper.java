package com.koreait.matzip.rest;

import java.util.List;

import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RestMapper {
	int insRest(RestPARAM param);
	int insRestRecMenu(RestRecMenuVO param);
	List<RestDMI> selRestList(RestPARAM param);
	RestDMI selRest(RestPARAM param);
	List<RestRecMenuVO> selRestRecMenus(RestPARAM param);
	int delRestRecMenu(RestPARAM param);
	int delRestMenu(RestPARAM param);
	int delRest(RestPARAM param);
	
}
