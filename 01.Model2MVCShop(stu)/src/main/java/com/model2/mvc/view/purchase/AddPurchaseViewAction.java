package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class AddPurchaseViewAction extends Action {//���Ÿ� ���� ȭ���û
	
	//1. ��ǰ�˻� -> ��ǰ�� -> ��ǰ����ȸ�� [����] ��ư Ŭ��
	//=> http://IP:��Ʈ��ȣ/addPurchaseView.do?prod_no=prodNo
	//2. AddPurchaseViewAction.java�� �̵�
	//3. execute() ���� �� addPurchaseView.jsp�� �̵�

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddPurchaseViewAction : execute() ���� >>>>>");
		
		//"prodNo"�� value�� �����ͼ� prodNo�� ����
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("prodNo��? "+prodNo);//�����);
		
		//PurchaseServiceImpl �ν��Ͻ� ����
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
//		PurchaseVO purchaseVO = purchaseService.addPurchase(prodNo);
		
		System.out.println("<<<<< AddPurchaseViewAction : execute() ���� >>>>>");
		
		return "forward:/purchase/addPurchaseView.jsp";

	}//end of execute()
}//end of class
