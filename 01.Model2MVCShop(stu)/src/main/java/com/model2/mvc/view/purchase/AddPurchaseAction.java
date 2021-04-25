package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action {//구매 요청

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddPurchaseAction : execute() 시작 >>>>>");
		
		//1. addPurchaseView.jsp에서 가져온 값을 DB에 쉽게 전달하기 위해 PurchaseVO 인스턴스를 생성하여, 가져온 값을 저장
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));//구매방법
		purchaseVO.setReceiverName(request.getParameter("receiverName"));  //구매자이름
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));//구매자연락처
		purchaseVO.setDivyAddr(request.getParameter("divyAddr"));          //구매자주소
		purchaseVO.setDivyRequest(request.getParameter("divyRequest"));    //구매요청사항
		purchaseVO.setDivyDate(request.getParameter("divyDate"));          //배송희망일자
		System.out.println("1.purchaseVO는? " + purchaseVO);//디버깅
		
		//2. ProductVO의 값을 PurchaseVO에 저장
		//방법1)
		ProductService productService = new ProductServiceImpl();
		ProductVO productVO = productService.getProduct((Integer.parseInt(request.getParameter("prodNo"))));
		purchaseVO.setPurchaseProd(productVO);                             //상품정보
		System.out.println("2.purchaseVO는? " + purchaseVO);//디버깅
		//방법2)
//		ProductVO productVO = new ProductVO();
//		productVO.setProdNo(Integer.parseInt(request.getParameter("prodNo"))); 
//		purchaseVO.setPurchaseProd(productVO);
		
		//3. UserVO의 값을 PurchaseVO에 저장
		//방법1)
		HttpSession session = request.getSession();
		purchaseVO.setBuyer((UserVO)session.getAttribute("user"));		   //구매자아이디
		System.out.println("3.purchaseVO는? " + purchaseVO);//디버깅
		//방법2)
//		UserVO userVO = new UserVO();
//		userVO.setUserId(request.getParameter("userId"));
//		purchaseVO.setBuyer(userVO);	
		
		//4. purchaseVO를 DB에 저장하기 위해 PurchaseServiceImpl 인스턴스 생성
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		//5. purchaseVO를 인자로 넘겨주며 addPurchase() 실행
		purchaseService.addPurchase(purchaseVO);
		
		//6. 구매정보가 담긴 purchaseVO를 addPurchase.jsp에 넘겨주기 위해 Request Object Scope에 setAttribute를 통해 저장
		request.setAttribute("purchaseVO", purchaseVO);
		
		System.out.println("<<<<< AddPurchaseAction : execute() 종료 >>>>>");
		
		return "forward:/purchase/addPurchase.jsp";
		
	}//end of execute()
}//end of class
