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

public class GetProductAction extends Action {//상품상세보기 요청

	//1. 판매상품관리 or 상품검색 메뉴 클릭
	//2. 판매상품관리의 상품명 ==> http://192.168.0.96:8080/getProduct.do?prodNo=prodNo&menu=manage 이동
	//3. 상품검색의 상품명    ==> http://192.168.0.96:8080/getProduct.do?prodNo=prodNo&menu=search 이동
	//4. GetProductAction.java의 execute() 실행
	//5. menu=manage 일 경우 ==> UpdateProductViewAction.java 이동
	//6. menu=search 일 경우 ==> getProduct.jsp 이동
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//디버깅
		System.out.println("<<<여기는 GetProductAction >>>");
		
		//"prodNo"의 value를 가져와서 prodNo에 저장
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("prodNo는? "+prodNo);//디버깅
		
		//"menu"의 value를 가져와서 menu에 저장
		String menu = request.getParameter("menu");
		System.out.println("menu는? "+menu);//디버깅
		
		//menu의 값이 manage일 경우
		if(menu != null && menu.equals("manage")) {
			return "forward:/updateProductView.do?prodNo="+prodNo+"&menu="+menu;
		}
		
		//ProductServiceImpl 인스턴스 생성
		ProductService service = new ProductServiceImpl();
		
		//vo에 ProductServiceImpl의 getProduct() 메소드 결과값 저장
		ProductVO vo = service.getProduct(prodNo);
		System.out.println("<<< DAO : getfindProduct() 종료 >>>");//디버깅
		System.out.println("vo는? "+vo);//디버깅
		
		//request에 vo 값을 셋팅
		request.setAttribute("vo", vo);

		return "forward:/product/getProduct.jsp";
		
	}//end of execute()

}//end of class
