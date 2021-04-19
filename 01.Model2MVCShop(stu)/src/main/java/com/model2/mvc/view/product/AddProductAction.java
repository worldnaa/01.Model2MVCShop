package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class AddProductAction extends Action {//��ǰ��� ��û

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ProductVO productVO = new ProductVO();
		productVO.setProdName(request.getParameter("prodName"));                    //��ǰ��
		productVO.setProdDetail(request.getParameter("prodDetail"));                //��ǰ������
		productVO.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));//��������
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));        //����		
		productVO.setFileName(request.getParameter("fileName"));                    //��ǰ�̹���
		
		System.out.println(productVO);//�����
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(productVO);
		
		//���1
//		HttpSession session = request.getSession(true);		
//		session.setAttribute("productVO",productVO);
		
		//���2
		request.setAttribute("vo", productVO);
		
		return "forward:/product/addProduct.jsp";
		
	}//end of execute()
}//end of class
