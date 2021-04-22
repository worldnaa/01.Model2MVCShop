package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
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
		
		//"prodNo"의 value를 가져와서 prodNo에 저장
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("prodNo는? "+prodNo);//디버깅);
		
		//PurchaseServiceImpl 인스턴스 생성
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
//		PurchaseVO purchaseVO = purchaseService.addPurchase(prodNo);
		
		System.out.println("<<<<< AddPurchaseViewAction : execute() 종료 >>>>>");
		
		return "forward:/purchase/addPurchaseView.jsp";

	}//end of execute()
}//end of class
