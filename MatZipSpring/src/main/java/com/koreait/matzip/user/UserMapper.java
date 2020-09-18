package com.koreait.matzip.user;

import org.apache.ibatis.annotations.Mapper; //찾기위한 용도

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Mapper //bean 등록은 아님, java(현재usermapper)와 (usermapper)xml을 합쳐서 dao를 만듬  
public interface UserMapper {
	public int insUser(UserVO p);
	public UserDMI selUser(UserDTO p);
}
