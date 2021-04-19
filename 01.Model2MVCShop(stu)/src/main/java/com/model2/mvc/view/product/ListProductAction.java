package com.model2.mvc.view.product;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class ListProductAction extends Action {//��ǰ�����ȸ ��û

	//1. �ǸŻ�ǰ���� or ��ǰ�˻� �޴� Ŭ��
	//2. �ǸŻ�ǰ���� ==> http://192.168.0.96:8080/listProduct.do?menu=manage �̵�
	//3. ��ǰ�˻�    ==> http://192.168.0.96:8080/listProduct.do?menu=search �̵�
	//4. ListProductAction.java�� execute() ����
	//5. listProduct.jsp �̵�
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�����
		System.out.println("<<<����� ListProductAction >>>");
		
		//SearchVO �ν��Ͻ� ����
		SearchVO searchVO = new SearchVO();
		
		//ó�� ���� ��� page�� 1
		int page = 1;
		
		//"page"�� value�� null�� �ƴ� ��� (page�� ���� ���� ���)
		//page�� "page"�� ���� ���� (���� ������ �� ����)
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			System.out.println("page��? "+page);//�����
		}
		
		//SearchVO�� page�� "page"�� �� ����(ó�� ���� ��� 1 ����)
		//SearchVO�� searchCondition�� "searchCondition"�� �� ����
		//SearchVO�� searchKeyword�� "searchKeyword"�� �� ����
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		//�����
		System.out.println("page��? "+page);
		System.out.println("searchCondition��? "+request.getParameter("searchCondition"));
		System.out.println("searchKeyword��? "+request.getParameter("searchKeyword"));
		
		//pageUnit�� web.xml�� "pageSize" �� 3�� �����ϰ�
		//SearchVO�� pageUnit�� 3 ����
		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		System.out.println("pageUnit��? "+pageUnit);//�����
		
		//ProductServiceImpl �ν��Ͻ� ����
		ProductService service = new ProductServiceImpl();
		
		//map�� ProductServiceImpl�� getProductList() �޼ҵ� ����� ����
		HashMap<String,Object> map = service.getProductList(searchVO);
		System.out.println("<<< DAO : getProductList() ���� >>>");//�����
		System.out.println("map��? "+map);//�����
		
		//menu�� "menu"�� value(manage Ȥ�� search)�� �ҷ��� ����
		String menu = request.getParameter("menu");
		System.out.println("menu��? "+menu);//�����

		//request�� map, searchVO, menu ���� ����
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
		
	}//end of execute()
	
}//end of class
