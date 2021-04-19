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

public class ListProductAction extends Action {//상품목록조회 요청

	//1. 판매상품관리 or 상품검색 메뉴 클릭
	//2. 판매상품관리 ==> http://192.168.0.96:8080/listProduct.do?menu=manage 이동
	//3. 상품검색    ==> http://192.168.0.96:8080/listProduct.do?menu=search 이동
	//4. ListProductAction.java의 execute() 실행
	//5. listProduct.jsp 이동
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//디버깅
		System.out.println("<<<여기는 ListProductAction >>>");
		
		//SearchVO 인스턴스 생성
		SearchVO searchVO = new SearchVO();
		
		//처음 들어올 경우 page는 1
		int page = 1;
		
		//"page"의 value가 null이 아닐 경우 (page를 눌러 들어올 경우)
		//page에 "page"의 값을 저장 (현재 페이지 값 저장)
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			System.out.println("page는? "+page);//디버깅
		}
		
		//SearchVO의 page에 "page"의 값 저장(처음 들어올 경우 1 저장)
		//SearchVO의 searchCondition에 "searchCondition"의 값 저장
		//SearchVO의 searchKeyword에 "searchKeyword"의 값 저장
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		//디버깅
		System.out.println("page는? "+page);
		System.out.println("searchCondition은? "+request.getParameter("searchCondition"));
		System.out.println("searchKeyword는? "+request.getParameter("searchKeyword"));
		
		//pageUnit에 web.xml의 "pageSize" 값 3을 저장하고
		//SearchVO의 pageUnit에 3 저장
		String pageUnit = getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		System.out.println("pageUnit은? "+pageUnit);//디버깅
		
		//ProductServiceImpl 인스턴스 생성
		ProductService service = new ProductServiceImpl();
		
		//map에 ProductServiceImpl의 getProductList() 메소드 결과값 저장
		HashMap<String,Object> map = service.getProductList(searchVO);
		System.out.println("<<< DAO : getProductList() 종료 >>>");//디버깅
		System.out.println("map은? "+map);//디버깅
		
		//menu에 "menu"의 value(manage 혹은 search)를 불러와 저장
		String menu = request.getParameter("menu");
		System.out.println("menu는? "+menu);//디버깅

		//request에 map, searchVO, menu 값을 셋팅
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
		
	}//end of execute()
	
}//end of class
