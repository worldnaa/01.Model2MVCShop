package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class GetPurchaseAction extends Action {//구매상세정보 요청

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< GetPurchaseAction : execute() 시작 >>>>>");
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("받은 tranNo : " + tranNo);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		PurchaseVO purchaseVO = purchaseService.getPurchase(tranNo);
		System.out.println("purchaseVO 셋팅완료 : " + purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		System.out.println("<<<<< GetPurchaseAction : execute() 종료 >>>>>");
		return "forward:/purchase/getPurchase.jsp";
	}

}
