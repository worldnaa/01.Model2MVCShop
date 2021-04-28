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
	//���Ÿ� ���� DBMS�� ����
	public void insertPurchase(PurchaseVO purchaseVO) throws SQLException {	
		System.out.println("<<<<< PurchaseDAO : insertPurchase() ���� >>>>>");
		
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
		System.out.println("<<<<< PurchaseDAO : insertProduct() ���� >>>>>");
	}
	
	
	//�������� �� ��ȸ�� ���� DBMS�� ����
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		System.out.println("<<<<< PurchaseDAO : findPurchase() ���� >>>>>");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, tranNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		PurchaseVO purchaseVO = new PurchaseVO();
		ProductVO productVO = new ProductVO();
		UserVO userVO = new UserVO();
		
		while (rs.next()) {
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setTranCode(sql);
//			purchaseVO.setBuyer(rs.getString("buyer_id"));	
		}
		System.out.println("<<<<< PurchaseDAO : findPurchase() ���� >>>>>");
		return purchaseVO; 
	}
	
	
	//���Ÿ�� ���⸦ ���� DBMS�� ����
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() ���� >>>>>");
		System.out.println("buyerId ��? " + buyerId);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE buyer_id = ? ";
		System.out.println("1. sql��? " + sql);
		
		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, 
															ResultSet.CONCUR_UPDATABLE);
		pStmt.setString(1, buyerId);
		
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println("2. sql��? " + sql);
		
		rs.last(); //boolean last() : ������ ������ Ŀ�� �̵�
		int total = rs.getRow(); //int getRow() : ���� ���ȣ �˻� (������ ���ȣ = ��ü ���� ��)
		System.out.println("��ü �ο��� �� : " + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
//		map.put("count", new Integer(total));
		map.put("count", total);
		
		System.out.println("map��? " + map);
		
		//boolean absolute(int row) : ������ ���ȣ�� Ŀ�� �̵�
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage(): " + searchVO.getPage());
		System.out.println("searchVO.getPageUnit(): " + searchVO.getPageUnit());
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		//��ü �ο��� ���� 1 �̻� �̸�
		if (total > 0) {
			for (int i=0; i<searchVO.getPageUnit(); i++) {
				
				PurchaseVO purchaseVO = new PurchaseVO();
				purchaseVO.setTranNo(rs.getInt("tran_no"));
				purchaseVO.setReceiverName(rs.getString("receiver_name"));
				purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
				purchaseVO.setTranCode(rs.getString("tran_status_code"));
				
//				UserVO userVO = new UserVO();
//				userVO.setUserId(rs.getString("buyer_id"));
//				purchaseVO.setBuyer(userVO);
				
				UserService service = new UserServiceImpl();
				purchaseVO.setBuyer(service.getUser(rs.getString("BUYER_ID")));
				
				
				list.add(purchaseVO);
				
				if (!rs.next()) {
					break;
				}
			}
		}
		System.out.println("list.size() : "+ list.size());
		System.out.println("list��? " + list);
		
		map.put("list", list);
		System.out.println("map().size() : "+ map.size()); 
		System.out.println("map��? " + map);
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() ���� >>>>>");
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}//end of class
