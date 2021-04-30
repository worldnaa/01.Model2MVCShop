package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
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
		
		pStmt.close();
		con.close();	
		System.out.println("<<<<< PurchaseDAO : insertProduct() ���� >>>>>");
	}
	
	
	//�������� �� ��ȸ�� ���� DBMS�� ����
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		System.out.println("<<<<< PurchaseDAO : findPurchase() ���� >>>>>");
		System.out.println("tranNo ��? " + tranNo);
		
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

			System.out.println("purchaseVO ��? " + purchaseVO);
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : findPurchase() ���� >>>>>");
		return purchaseVO; 
	}
	
	
	//���� ��� ���θ� �ľ��ϱ� ���� DBMS�� ����
	public PurchaseVO findPurchase2(int prodNo) throws Exception {
		System.out.println("<<<<< PurchaseDAO : findPurchase2() ���� >>>>>");
		System.out.println("prodNo ��? " + prodNo);
			
		Connection con = DBUtil.getConnection();
			
		String sql = "SELECT * FROM transaction WHERE prod_no = ? ";
			
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
			
		ResultSet rs = pStmt.executeQuery();
			
		PurchaseVO purchaseVO = new PurchaseVO();
		while (rs.next()) {
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			System.out.println("findPurchase2 �� tran_no ��? " + purchaseVO.getTranNo());
			System.out.println("findPurchase2 �� tran_status_code ��? " + purchaseVO.getTranCode());
		}
			
		rs.close();
		pStmt.close();
		con.close();
			
		System.out.println("<<<<< PurchaseDAO : findPurchase2() ���� >>>>>");
		return purchaseVO; 
	}
		
	
	//���Ÿ�� ���⸦ ���� DBMS�� ����
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() ���� >>>>>");
		System.out.println("buyerId ��? " + buyerId);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE buyer_id = ? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, 
															ResultSet.CONCUR_UPDATABLE);
		pStmt.setString(1, buyerId);
		
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql ��? " + sql);
		
		rs.last(); //boolean last() : ������ ������ Ŀ�� �̵�
		int total = rs.getRow(); //int getRow() : ���� ���ȣ �˻� (������ ���ȣ = ��ü ���� ��)
		System.out.println("��ü �ο��� �� : " + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", total);
		System.out.println("map ��? " + map);
		
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
				
				UserService userService = new UserServiceImpl();
				purchaseVO.setBuyer(userService.getUser(rs.getString("buyer_id")));
				
				list.add(purchaseVO);
				
				if (!rs.next()) {
					break;
				}
			}
		}
		System.out.println("list.size() : "+ list.size());
		System.out.println("list ��? " + list);
		
		map.put("list", list);
		System.out.println("map().size() : "+ map.size()); 
		System.out.println("map ��? " + map);
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() ���� >>>>>");
		return map;
	}
	
	
	//�ǸŸ�� ���⸦ ���� DBMS�� ����
	public HashMap<String,Object> getSaleList(SearchVO searchVO) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getSaleList() ���� >>>>>");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT ";
		System.out.println("sql��? "+sql);//�����
		
		//SearchCondition�� ���� ���� ���
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where PROD_NO like '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where PROD_NAME like '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("2")) {
				sql += " where PRICE like '%" + searchVO.getSearchKeyword() + "%'";
			}
			System.out.println("sql��? "+sql);//�����
		}
		
		sql += " order by PROD_NO";
		System.out.println("sql��? "+sql);//�����

		PreparedStatement stmt = con.prepareStatement(sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		//boolean last() : ������ ������ Ŀ�� �̵�
		rs.last();
		
		//int getRow() : ���� ���ȣ �˻� (������ ���ȣ = ��ü ���� ��)
		int total = rs.getRow();
		System.out.println("��ü �ο��� ��: " + total);//�����

		HashMap<String,Object> map = new HashMap<String,Object>();
		
		//"count"�� total(��ü �ο��� ��) ���� 
		map.put("count", new Integer(total));
		System.out.println("map��? "+map);//�����

		//boolean absolute(int row) : ������ ���ȣ�� Ŀ�� �̵�
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		
		//�����
		System.out.println("searchVO.getPage(): " + searchVO.getPage());
		System.out.println("searchVO.getPageUnit(): " + searchVO.getPageUnit());
 
		//<ProductVO>�� �� �� �ִ� ArrayList ����
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		
		//��ü �ο��� ���� 1�̻��̸�
		if (total > 0) {
			//0~2���� �ݺ��� 3�� ����
			for (int i=0; i<searchVO.getPageUnit(); i++) {
				//ProductVO�� PRODUCT ���̺��� ���� ������ ����
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("PROD_NO"));
				vo.setProdName(rs.getString("PROD_NAME"));
				vo.setProdDetail(rs.getString("PROD_DETAIL"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setPrice(rs.getInt("PRICE"));
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setRegDate(rs.getDate("REG_DATE"));
				
				if(findPurchase2(vo.getProdNo()).getTranCode().trim().equals("1")) {
					System.out.println("tranCode ��? " + findPurchase2(vo.getProdNo()).getTranCode());
					vo.setProTranCode("1");//���ſϷ�
				}else if(findPurchase2(vo.getProdNo()).getTranCode().trim().equals("2")) {
					System.out.println("tranCode ��? " + findPurchase2(vo.getProdNo()).getTranCode());
					vo.setProTranCode("3");//�����
				}else if(findPurchase2(vo.getProdNo()).getTranCode().trim().equals("3")) {
					System.out.println("tranCode ��? " + findPurchase2(vo.getProdNo()).getTranCode());
					vo.setProTranCode("4");//��ۿϷ�
				}
				System.out.println("proTranCode ��? " + vo.getProTranCode());
				
				//list�� ProductVO�� ���õ� �� ����
				list.add(vo);
				
				if (!rs.next()) {
					break;
				}				
			}
		}
		
		System.out.println("list.size() : "+ list.size()); //�����
		
		//"list"�� list(ProductVO ������) ����
		map.put("list", list);
		
		System.out.println("map().size() : "+ map.size()); //�����
		System.out.println("map��? "+map);
		
		rs.close();
		stmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : getSaleList() ���� >>>>>");
		return map;
	}
	
	
	//�������� ������ ���� DBMS�� ����
	public void updatePurchase(PurchaseVO purchaseVO) throws SQLException {
		System.out.println("<<<<< PurchaseDAO : updatePurchase() ���� >>>>>");
		
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
		System.out.println("purchaseVO ��? " + purchaseVO);
		
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : updatePurchase() ���� >>>>>");
	}
	
	
	//���� �����ڵ� ������ ���� DBMS�� ����
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		System.out.println("<<<<< PurchaseDAO : updateTranCode() ���� >>>>>");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET tran_status_code=? WHERE tran_no=?";
		System.out.println("sql��? " + sql);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchaseVO.getTranCode());
		pStmt.setInt(2, purchaseVO.getTranNo());
		
		System.out.println("tranCode ��? " + purchaseVO.getTranCode());
		System.out.println("tranNo ��? " + purchaseVO.getTranNo());
		
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : updateTranCode() ���� >>>>>");
	}

}//end of class
