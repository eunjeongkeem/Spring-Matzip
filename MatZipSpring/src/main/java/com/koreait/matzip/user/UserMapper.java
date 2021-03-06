package com.koreait.matzip.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper; //찾기위한 용도

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserPARAM;
import com.koreait.matzip.user.model.UserVO;

@Mapper //bean 등록은 아님, java(현재usermapper)와 (usermapper)xml을 합쳐서 dao를 만듬  
public interface UserMapper {
	public int insUser(UserVO param);
	public int insFavorite(UserPARAM param);
	public UserDMI selUser(UserPARAM param);
	
	List<UserDMI> selFavoriteList(UserPARAM param);
	public int delFavorite(UserPARAM param);
}
