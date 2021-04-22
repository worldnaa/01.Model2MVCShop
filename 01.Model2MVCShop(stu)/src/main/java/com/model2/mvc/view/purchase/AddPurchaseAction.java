package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action {//���� ��û

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddPurchaseAction : execute() ���� >>>>>");
		
		PurchaseVO purchaseVO = new PurchaseVO();
		ProductVO productVO = new ProductVO();
		UserVO userVO = new UserVO();
		
//		purchaseVO.setPurchaseProd(request.getParameter("purchaseProd"));  //��ǰ��ȣ
//		purchaseVO.setBuyer(request.getParameter("buyer"));                //�����ھ��̵�    
		
		productVO.setProdNo(Integer.parseInt(request.getParameter("prodNo")));  
		purchaseVO.setPurchaseProd(productVO);//��ǰ��ȣ
		
		userVO.setUserId(request.getParameter("userId"));
		purchaseVO.setBuyer(userVO);//�����ھ��̵�
		
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));//���Ź��
		purchaseVO.setReceiverName(request.getParameter("receiverName"));  //�������̸�
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));//�����ڿ���ó
		purchaseVO.setDivyAddr(request.getParameter("divyAddr"));          //�������ּ�
		purchaseVO.setDivyRequest(request.getParameter("divyRequest"));    //���ſ�û����
		purchaseVO.setDivyDate(request.getParameter("divyDate"));          //����������
		
		
//		purchaseVO.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));//��������
		
		System.out.println(purchaseVO);//�����
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.addPurchase(purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		
		System.out.println("<<<<< AddPurchaseAction : execute() ���� >>>>>");
		
		return "forward:/purchase/addPurchase.jsp";
		
	}//end of execute()
}//end of class
