package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdateProductViewAction extends Action {//상품정보수정을 위한 화면요청

	//1. 판매상품관리의 상품명을 클릭해서 온 경우 (menu=manage)
	//2. UpdateProductViewAction.java의 execute() 실행
	//3. updateProductView.jsp 이동
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//디버깅
		System.out.println("<<<여기는 UpdateProductViewAction >>>");
		
		//"prodNo"의 value를 가져와서 prodNo에 저장
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("prodNo는? "+prodNo);//디버깅
		
		//ProductServiceImpl 인스턴스 생성
		ProductService service = new ProductServiceImpl();
		
		//vo에 ProductServiceImpl의 getProduct() 메소드 결과값 저장
		ProductVO vo = service.getProduct(prodNo);
		System.out.println("<<< DAO : getfindProduct() 종료 >>>");//디버깅
		System.out.println("vo는? "+vo);//디버깅
		
		//request에 vo, menu 값을 셋팅
		request.setAttribute("vo", vo);	
		
		return "forward:/product/updateProductView.jsp";
		
	}//end of execute()

}//end of class
