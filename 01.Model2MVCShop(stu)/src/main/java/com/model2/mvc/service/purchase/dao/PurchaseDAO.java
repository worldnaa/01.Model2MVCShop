package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseDAO {
	//Constructor
	public PurchaseDAO() {
	}
	
	//Method
	public void insertPurchase(PurchaseVO purchaseVO) throws SQLException {	
		System.out.println("<<<<< PurchaseDAO : insertPurchase() 시작 >>>>>");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO transaction VALUES (seq_product_prod_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
//		pStmt.setString(1, purchaseVO.get());
//		pStmt.setString(2, purchaseVO.getProdDetail());
		
		
		pStmt.executeUpdate();
		
		con.close();	
		
		System.out.println("<<<<< Purchase DAO : insertProduct() 종료 >>>>>");
	}
	

}//end of class
