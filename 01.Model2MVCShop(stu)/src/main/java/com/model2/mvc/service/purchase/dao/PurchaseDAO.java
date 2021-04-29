package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {
	//Field
	//Constructor
	public PurchaseDAO() {
	}
	
	//Method
	//구매를 위한 DBMS를 수행
	public void insertPurchase(PurchaseVO purchaseVO) throws SQLException {	
		System.out.println("<<<<< PurchaseDAO : insertPurchase() 시작 >>>>>");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO transaction VALUES (seq_product_prod_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		pStmt.setString(2, purchaseVO.getBuyer().getUserId());
		pStmt.setString(3, purchaseVO.getPaymentOption());
		pStmt.setString(4, purchaseVO.getReceiverName());
		pStmt.setString(5, purchaseVO.getReceiverPhone());
		pStmt.setString(6, purchaseVO.getDivyAddr());
		pStmt.setString(7, purchaseVO.getDivyRequest());
		pStmt.setString(8, purchaseVO.getTranCode());
		pStmt.setString(9, purchaseVO.getDivyDate());
		
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();	
		System.out.println("<<<<< PurchaseDAO : insertProduct() 종료 >>>>>");
	}
	
	
	//구매정보 상세 조회를 위한 DBMS를 수행
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		System.out.println("<<<<< PurchaseDAO : findPurchase() 시작 >>>>>");
		System.out.println("tranNo 는? " + tranNo);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE tran_no = ? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, tranNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		PurchaseVO purchaseVO = new PurchaseVO();
		while (rs.next()) {
			
			ProductVO productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("prod_no"));
			purchaseVO.setPurchaseProd(productVO);
			
			UserVO userVO = new UserVO();
			userVO.setUserId(rs.getString("buyer_id"));
			purchaseVO.setBuyer(userVO);
			
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setTranNo(rs.getInt("tran_no"));

			System.out.println("purchaseVO 는? " + purchaseVO);
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : findPurchase() 종료 >>>>>");
		return purchaseVO; 
	}
	
	
	//구매목록 보기를 위한 DBMS를 수행
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() 시작 >>>>>");
		System.out.println("buyerId 는? " + buyerId);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE buyer_id = ? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, 
															ResultSet.CONCUR_UPDATABLE);
		pStmt.setString(1, buyerId);
		
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql 은? " + sql);
		
		rs.last(); //boolean last() : 마지막 행으로 커서 이동
		int total = rs.getRow(); //int getRow() : 현재 행번호 검색 (마지막 행번호 = 전체 행의 수)
		System.out.println("전체 로우의 수 : " + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", total);
		System.out.println("map 은? " + map);
		
		//boolean absolute(int row) : 지정된 행번호로 커서 이동
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage(): " + searchVO.getPage());
		System.out.println("searchVO.getPageUnit(): " + searchVO.getPageUnit());
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		//전체 로우의 수가 1 이상 이면
		if (total > 0) {
			for (int i=0; i<searchVO.getPageUnit(); i++) {
				
				PurchaseVO purchaseVO = new PurchaseVO();
				purchaseVO.setTranNo(rs.getInt("tran_no"));
				purchaseVO.setReceiverName(rs.getString("receiver_name"));
				purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
				purchaseVO.setTranCode(rs.getString("tran_status_code"));
								
				UserService service = new UserServiceImpl();
				purchaseVO.setBuyer(service.getUser(rs.getString("BUYER_ID")));
				
				list.add(purchaseVO);
				
				if (!rs.next()) {
					break;
				}
			}
		}
		System.out.println("list.size() : "+ list.size());
		System.out.println("list 는? " + list);
		
		map.put("list", list);
		System.out.println("map().size() : "+ map.size()); 
		System.out.println("map 은? " + map);
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() 종료 >>>>>");
		return map;
	}
	
	
	//구매정보 수정을 위한 DBMS를 수행
	public void updatePurchase(PurchaseVO purchaseVO) throws SQLException {
		System.out.println("<<<<< PurchaseDAO : updatePurchase() 시작 >>>>>");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction "
					+ "SET payment_option=?, receiver_name=?, receiver_phone=?,"
					+ "demailaddr=?, dlvy_request=?, dlvy_date=? "
					+ "WHERE tran_no=? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchaseVO.getPaymentOption());
		pStmt.setString(2, purchaseVO.getReceiverName());
		pStmt.setString(3, purchaseVO.getReceiverPhone());
		pStmt.setString(4, purchaseVO.getDivyAddr());
		pStmt.setString(5, purchaseVO.getDivyRequest());
		pStmt.setString(6, purchaseVO.getDivyDate());
		pStmt.setInt(7, purchaseVO.getTranNo());
		System.out.println("purchaseVO 는? " + purchaseVO);
		
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : updatePurchase() 종료 >>>>>");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}//end of class
