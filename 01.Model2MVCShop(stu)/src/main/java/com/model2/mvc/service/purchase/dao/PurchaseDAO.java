package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

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
		
		con.close();	
		
		System.out.println("<<<<< PurchaseDAO : insertProduct() 종료 >>>>>");
	}
	
	//구매정보 상세 조회를 위한 DBMS를 수행
//	public PurchaseVO findPurchase(int prodNo) throws Exception {
//		System.out.println("<<<<< PurchaseDAO : findPurchase() 시작 >>>>>");
//		
//		Connection con = DBUtil.getConnection();
//		
//		String sql = "SELECT * FROM product WHERE prod_no=?";
//		
//		PreparedStatement pStmt = con.prepareStatement(sql);
//		pStmt.setInt(1, prodNo);
//		
//		ResultSet rs = pStmt.executeQuery();
//		
//		ProductVO productVO = null;
//		while (rs.next()) {
//			productVO = new ProductVO();
//			
//		}
//		
//		System.out.println("<<<<< PurchaseDAO : findPurchase() 종료 >>>>>");
//		
//		return PurchaseVO; 
//	}
	
	
	

}//end of class
