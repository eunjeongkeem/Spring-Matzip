package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired//xml방법말고 ,,, 주소값주는방법,, bean을 자동으로 주입
	//자동 선 연결 bean이 등록된 것들 중에 (스프링컨테이너가 관리하는것, 알아서 스프링컨테이너가 객체화시킨것)
	//bean등록 된것이 단 하나만 있어야함 
	private UserService service;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute(Const.TITLE, "로그인");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(UserDTO param) {
		int result = service.login(param);
		if(result == 1) {
			return "redirect:/rest/map";
		}
		return "redirect:/user/login?err=" + result;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(defaultValue="0") int err) { 
		//required=false --> err값이 없어도 뜸... ,기본값은 true/ null값이 못들어가서 int 말고 Integer or
		//required=false 없고 -> defaultValue= 0 err=0 넣어주지 않아도 됨 join들어가짐
		System.out.println("err : " + err);
		
		if(err > 0) {
			model.addAttribute("msg", "에러가 발생하였습니다.");
		}
		model.addAttribute(Const.TITLE, "회원가입");
		model.addAttribute(Const.VIEW, "user/join");
		return ViewRef.TEMP_DEFAULT;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(UserVO param) {
		int result = service.join(param);
		if(result == 1) {
			return "redirect:/user/login";
		}
		return "redirect:/user/join?err=" + result;
	}

}
