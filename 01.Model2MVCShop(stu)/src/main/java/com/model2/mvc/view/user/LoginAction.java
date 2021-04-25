package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class LoginAction extends Action{ //로그인 요청

	@Override
	public String execute(	HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< LoginAction : execute() 시작 >>>>>");
		
		//1. user정보를 가져오기 위한 UserVO 인스턴스 생성
		UserVO userVO = new UserVO();
		
		//2. userVO에 "userId","password"의 value 값을 저장
		userVO.setUserId(request.getParameter("userId"));
		userVO.setPassword(request.getParameter("password"));
		
		//3. loginUser() 실행을 위한 UserServiceImpl 인스턴스 생성
		UserService service = new UserServiceImpl();
		
		//4. 인자로 userId,password를 넘겨주며 메서드 실행 ==> 결과값인 user 정보를 dbVO에 저장
		UserVO dbVO = service.loginUser(userVO);
		
		//5. 세션을 만들어 "user"라는 name으로, dbVO의 value를 저장
		HttpSession session = request.getSession();
		session.setAttribute("user", dbVO);
		
		System.out.println("<<<<< LoginAction : execute() 종료 >>>>>");
		
		return "redirect:/index.jsp";
	}
}