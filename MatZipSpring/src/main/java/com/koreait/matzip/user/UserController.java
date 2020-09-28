package com.koreait.matzip.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired//xml방법말고 ,,, 주소값주는방법,, bean을 자동으로 주입
	//자동 선 연결 bean이 등록된 것들 중에 (스프링컨테이너가 관리하는것, 알아서 스프링컨테이너가 객체화시킨것)
	//bean등록 된것이 단 하나만 있어야함 
	private UserService service;
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpSession hs) {
		hs.invalidate();
		return "redirect:/user/login";
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {
		System.out.println("Controller - login");
		model.addAttribute(Const.TITLE, "로그인");
		model.addAttribute(Const.VIEW, "user/login");
		return ViewRef.TEMP_DEFAULT;
	}
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(UserPARAM param, HttpSession hs, RedirectAttributes ra) {
		int result = service.login(param);
		if(result == Const.SUCCESS) {
			hs.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/rest/map";
		}
		String msg = null;
		if(result == Const.NO_ID) {
			msg = "아이디를 확인해 주세요.";
		} else if(result == Const.NO_PW) {
			msg = "비밀번호를 확인해 주세요.";
		}
		param.setMsg(msg);
		ra.addFlashAttribute("data", param);  //addFlachAttribute 원리 -> session에 박히고 지워줌 
		return "redirect:/user/login";
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
	public String join(UserVO param, RedirectAttributes ra) {
		int result = service.join(param);
		if(result == 1) {
			return "redirect:/user/login";
		}
		ra.addAttribute("err", result);
		return "redirect:/user/join";
	}
	
	//addAttribute 쿼리스트링 만들어줌
	//addFlashAttribute redirect하고 응답해주면 지움(Session 같은 역할)
	
	@RequestMapping(value="/ajaxIdChk", method=RequestMethod.POST)
	@ResponseBody
	public String ajaxIdChk(@RequestBody UserPARAM param) {
		System.out.println("user_id : " + param.getUser_id());
		int result = service.login(param);
		return String.valueOf(result); //string 앞에 아무것두 없으니까  jsp를 찾는것 ?
	}
	
	@RequestMapping(value="/ajaxToggleFavorite", method=RequestMethod.GET)
	@ResponseBody
	public int ajaxToggleFavorite(UserPARAM param, HttpSession hs) {
		System.out.println("==> ajaxToggleFavorite");
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);
		return service.ajaxToggleFavorite(param);
	}
	
	
}

