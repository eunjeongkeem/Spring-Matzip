package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.koreait.matzip.rest.RestMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;

public class RestInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private RestMapper mapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("rest - interceptor");
		
		String uri = request.getRequestURI();
		System.out.println("uri : " + uri);
		String[] uriArr = uri.split("/");
		
		String[] checkKeywords = {"del", "Del", "upd", "Upd"};
		for(String keyword : checkKeywords) {
			if(uriArr[2].contains(keyword)) {
				int i_rest = CommonUtils.getIntParameter("i_rest", request);
				if(i_rest == 0) {
					return false;
				}
				int i_user = SecurityUtils.getLoginUserPk(request); //로그인 하는 사람의 i_user값을 가져옴 
				
				boolean result = _authSuccess(i_rest, i_user); //i_rest와 i_user가 같다면 true 
				System.out.println("=== auth result : " + result);
				return result;
			}
		}
		return true;
	}
	
	
	private boolean _authSuccess(int i_rest, int i_user) { 
		return i_user == mapper.selRestChkUser(i_rest);
	}
	

}
