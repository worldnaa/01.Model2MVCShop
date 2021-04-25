package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class AddPurchaseViewAction extends Action {//구매를 위한 화면요청
	
	//1. 상품검색 -> 상품명 -> 상품상세조회의 [구매] 버튼 클릭
	//=> http://IP:포트번호/addPurchaseView.do?prod_no=prodNo
	//2. AddPurchaseViewAction.java로 이동
	//3. execute() 실행 후 addPurchaseView.jsp로 이동

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddPurchaseViewAction : execute() 시작 >>>>>");
		
		//1. 구매하려는 상품의 상세정보를 가져오기 위해 식별성을 가진 prodNo가 필요함
		//그러므로 getProduct.jsp에서 "prodNo"의 value를 가져와서 prodNo 변수에 저장
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));
		System.out.println("prod_no는? "+prodNo);//디버깅
		
		//2. 상품번호에 해당하는 상품정보를 가져와 addPurchaseView.jsp 화면에 보여줘야 함
		//그러므로 ProductServiceImpl 인스턴스를 생성하여 상품정보를 가져오는 getProduct() 실행
		ProductService productService = new ProductServiceImpl();
		
		//3. getProduct()의 리턴값으로 productDAO.findProduct(prodNo)를 실행하고, 결과값을 productVO 변수에 저장
		ProductVO productVO = productService.getProduct(prodNo);
		System.out.println("purchaseVO는? "+productVO);//디버깅
		
		//4. 상품정보가 담긴 productVO를 addPurchaseView.jsp에 넘겨주기 위해 Request Object Scope에 setAttribute를 통해 저장
		request.setAttribute("productVO", productVO);
		
		System.out.println("<<<<< AddPurchaseViewAction : execute() 종료 >>>>>");
		
		return "forward:/purchase/addPurchaseView.jsp";

	}//end of execute()
}//end of class
