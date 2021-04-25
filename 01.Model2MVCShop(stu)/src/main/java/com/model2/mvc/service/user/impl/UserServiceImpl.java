package com.model2.mvc.service.user.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.vo.UserVO;

public class UserServiceImpl implements UserService{
	//Field
	private UserDAO userDAO;
	
	//Constructor
	public UserServiceImpl() {
		userDAO = new UserDAO();
	}

	//Method
	public void addUser(UserVO userVO) throws Exception {
		System.out.println("<<<<< UserServiceImpl : addUser() 실행 >>>>>");
		userDAO.insertUser(userVO);
	}

	public UserVO loginUser(UserVO userVO) throws Exception {
		System.out.println("<<<<< UserServiceImpl : loginUser() 실행 >>>>>");
		
		UserVO dbUser = userDAO.findUser(userVO.getUserId());
		
		if(! dbUser.getPassword().equals(userVO.getPassword()))
			throw new Exception("로그인에 실패했습니다.");
			
		return dbUser;
	}

	public UserVO getUser(String userId) throws Exception {
		System.out.println("<<<<< UserServiceImpl : getUser() 실행 >>>>>");
		return userDAO.findUser(userId);
	}

	public HashMap<String,Object> getUserList(SearchVO searchVO) throws Exception {
		System.out.println("<<<<< UserServiceImpl : getUserList() 실행 >>>>>");
		return userDAO.getUserList(searchVO);
	}

	public void updateUser(UserVO userVO) throws Exception {
		System.out.println("<<<<< UserServiceImpl : updateUser() 실행 >>>>>");
		userDAO.updateUser(userVO);
	}

	public boolean checkDuplication(String userId) throws Exception {
		System.out.println("<<<<< UserServiceImpl : checkDuplication() 실행 >>>>>");
		
		boolean result = true;
		UserVO userVO = userDAO.findUser(userId);
		if(userVO != null) {
			result = false;
		}
		return result;
	}
}//end of class