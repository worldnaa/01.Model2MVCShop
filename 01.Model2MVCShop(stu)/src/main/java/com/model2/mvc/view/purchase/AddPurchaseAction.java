package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action {//구매 요청

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddPurchaseAction : execute() 시작 >>>>>");
		
		PurchaseVO purchaseVO = new PurchaseVO();
		ProductVO productVO = new ProductVO();
		UserVO userVO = new UserVO();
		
//		purchaseVO.setPurchaseProd(request.getParameter("purchaseProd"));  //상품번호
//		purchaseVO.setBuyer(request.getParameter("buyer"));                //구매자아이디    
		
		productVO.setProdNo(Integer.parseInt(request.getParameter("prodNo")));  
		purchaseVO.setPurchaseProd(productVO);//상품번호
		
		userVO.setUserId(request.getParameter("userId"));
		purchaseVO.setBuyer(userVO);//구매자아이디
		
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));//구매방법
		purchaseVO.setReceiverName(request.getParameter("receiverName"));  //구매자이름
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));//구매자연락처
		purchaseVO.setDivyAddr(request.getParameter("divyAddr"));          //구매자주소
		purchaseVO.setDivyRequest(request.getParameter("divyRequest"));    //구매요청사항
		purchaseVO.setDivyDate(request.getParameter("divyDate"));          //배송희망일자
		
		
//		purchaseVO.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));//제조일자
		
		System.out.println(purchaseVO);//디버깅
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.addPurchase(purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		
		System.out.println("<<<<< AddPurchaseAction : execute() 종료 >>>>>");
		
		return "forward:/purchase/addPurchase.jsp";
		
	}//end of execute()
}//end of class
