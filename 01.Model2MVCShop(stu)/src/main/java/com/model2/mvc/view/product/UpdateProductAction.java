package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdateProductAction extends Action {//상품정보수정 요청

	//1. 판매상품관리의 상품명을 클릭해서 온 경우 (menu=manage)
	//2. UpdateProductViewAction.java의 execute() 실행
	//3. updateProductView.jsp 이동
	//4. [수정] 버튼 클릭 시 ==> UpdateProductAction.java 이동
	//5. GetProductAction.java 이동
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//디버깅
		System.out.println("<<<여기는 UpdateProductAction >>>");
				
		//"prodNo"의 value를 가져와서 prodNo에 저장
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		//"menu"의 value를 가져와서 menu에 저장
		String menu = request.getParameter("menu");
		System.out.println("menu는? "+menu);//디버깅
		
		//ProductVO 인스턴스 생성
		ProductVO productVO = new ProductVO();
		
		//ProductVO에 업데이트 하려는 값을 가져와 셋팅
		productVO.setProdNo(prodNo);
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		
		//ProductServiceImpl 인스턴스 생성
		ProductService service = new ProductServiceImpl();
		
		//service에 ProductServiceImpl의 updateProduct() 메소드 결과값 저장
		service.updateProduct(productVO);
		System.out.println("<<< DAO : updateProduct() 종료 >>>");//디버깅
		System.out.println("service는? "+service);//디버깅
		
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu="+menu;
		
	}//end of execute()

}//end of class
