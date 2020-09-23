package com.koreait.matzip.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Service
public class RestService {
	
	@Autowired
	private RestMapper mapper;
	
	@Autowired
	private CommonMapper cMapper;
	
	public List<RestDMI> selRestList(RestPARAM param) {
		return mapper.selRestList(param);
//		List<RestDMI> list = mapper.selRestList(param);
//		System.out.println("list : " + list);
//		System.out.println("size : " + list.size());
//		Gson gson = new Gson();
//		return gson.toJson(list);
	}
	
	 public List<CodeVO> selCategoryList() {
		CodeVO p = new CodeVO();
		p.setI_m(1); //음식점 카테고리 코드 = 1
		return cMapper.selCodeList(p);
	}
	
	public int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}
	
	public RestDMI selRest(RestPARAM param) {
		return mapper.selRest(param);
	}
	
	@Transactional
	public void delRestTran(RestPARAM param) {
		mapper.delRestRecMenu(param);
		mapper.delRestMenu(param);
		mapper.delRest(param);
	}
	
	public int delRestRecMenu(RestPARAM param) {
		return mapper.delRestRecMenu(param);
	}
	
	public int delRestMenu(RestPARAM param) {
		return mapper.delRestMenu(param);
	}
	
	public void insRecMenus(int i_rest, List<MultipartFile> fileList, 
			String[] menuNmArr, String[] menuPriceArr) {
		
		List<RestRecMenuVO> list = new ArrayList();
		for(int i=0; i<menuNmArr.length; i++) {
			//파일 값 저장
			String menu_nm = menuNmArr[i];
			int menu_price = CommonUtils.parseStringToInt(menuPriceArr[i]);
			
			RestRecMenuVO vo = new RestRecMenuVO();
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);
			
			list.add(vo);
			
		}
	}


}
