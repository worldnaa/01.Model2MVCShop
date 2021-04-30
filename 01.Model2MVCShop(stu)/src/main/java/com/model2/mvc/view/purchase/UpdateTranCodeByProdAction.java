package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeByProdAction extends Action {//구매상태코드 수정 요청 (인자 : prodNo)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdateTranCodeByProdAction : execute() 시작 >>>>>");
		System.out.println("prodNo 는? " + request.getParameter("prodNo"));
		System.out.println("tranCode 는? " + request.getParameter("tranCode"));
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		PurchaseVO purchaseVO1 = new PurchaseVO();
		PurchaseVO purchaseVO2 = new PurchaseVO();
		
		//listProduct.jsp에서 배송하기 버튼 클릭 시 'prodNo' 가져옴
		//updateTranCode()에서 'tranNo'가 필요하므로 getPurchase2() 실행
		purchaseVO1 = purchaseService.getPurchase2(Integer.parseInt(request.getParameter("prodNo")));
		System.out.println("purchaseVO1 의 tranNo 는? " + purchaseVO1.getTranNo());
		System.out.println("purchaseVO1 의 tranCode 는? " + purchaseVO1.getTranCode());
		
		//listProduct.jsp에서 배송하기 버튼 클릭 시 'tranCode = 2' 가져옴 ==> 배송중 상태
		purchaseVO2.setTranNo(purchaseVO1.getTranNo());
		purchaseVO2.setTranCode(request.getParameter("tranCode"));
		System.out.println("purchaseVO2 의 tranNo 는? " + purchaseVO2.getTranNo());
		System.out.println("purchaseVO2 의 tranCode 는? " + purchaseVO2.getTranCode());
		
		purchaseService.updateTranCode(purchaseVO2);
		
		System.out.println("<<<<< UpdateTranCodeByProdAction : execute() 종료 >>>>>");
		
		return "forward:/listSale.do?menu=manage&page="+request.getParameter("page")+"&tranCode="+request.getParameter("tranCode");
		//redirect / +"&tranCode="+request.getParameter("tranCode")
	}

}
