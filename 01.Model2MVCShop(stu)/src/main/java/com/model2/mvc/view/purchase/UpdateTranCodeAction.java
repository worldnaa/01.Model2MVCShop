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

public class UpdateTranCodeAction extends Action {//구매상태코드 수정 요청 (인자 : tranNo)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdateTranCodeAction : execute() 시작 >>>>>");
		
		PurchaseVO purchaseVO = new PurchaseVO();
		
		//listPurchase.jsp에서 물건도착 버튼 클릭 시 'tranNo' 가져옴
		purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		System.out.println("tranNo 는? " + purchaseVO.getTranNo());	
		
		//listPurchase.jsp에서 물건도착 버튼 클릭 시 'tranCode = 3' 가져옴 ==> 배송완료 상태
		purchaseVO.setTranCode(request.getParameter("tranCode")); 
		System.out.println("tranCode 는? " + purchaseVO.getTranCode());
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.updateTranCode(purchaseVO);
		
		System.out.println("<<<<< UpdateTranCodeAction : execute() 종료 >>>>>");
		
		return "redirect:/listPurchase.do?menu=manage&page="+request.getParameter("page");
	}

}
