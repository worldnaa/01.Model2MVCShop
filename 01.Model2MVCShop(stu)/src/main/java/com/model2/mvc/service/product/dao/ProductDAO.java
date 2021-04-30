package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class ProductDAO {
	//Constructor
	public ProductDAO() {
	}
	
	//Method
	//�Ű������� ���� ProductVO ����, PRODUCT ���̺� ������� ����
	public void insertProduct(ProductVO productVO) throws SQLException {
		System.out.println("<<<����� DAO : insertProduct() ����>>>");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "insert into PRODUCT values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		
		con.close();	
		System.out.println("<<<����� DAO : insertProduct() ����>>>");
	}
	
	//�Ű������� ���� prodNo(��ǰ��ȣ)�� �ش��ϴ� ������ PRODUCT ���̺��� �˻�
	public ProductVO findProduct(int prodNo) throws Exception {
		//�����
		System.out.println("<<<����� DAO : findProduct() ����>>>");
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		ProductVO productVO = null;
		while (rs.next()) {
			//ProductVO �ν��Ͻ� ���� 
			productVO = new ProductVO();
			//ProductVO�� PRODUCT ���̺��� ������ �� ���� 
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();
		return productVO;
	}
	
	//�Ű������� ���� SearchVO�� ���� �̿��Ͽ� ArrayList�� ProductVO �� ����
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		//�����
		System.out.println("<<<����� DAO : getProductList() ����>>>");
		
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
				
				PurchaseService service = new PurchaseServiceImpl();
				
				if((service.getPurchase2(vo.getProdNo()).getTranNo()) > 0) {
					vo.setProTranCode("1");//������
				}else {
					vo.setProTranCode("2");//�Ǹ���
				}
				
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

		con.close();	
		return map;
	}
	
	//�Ű������� ���� ProductVO�� �ִ� ���� PRODUCT ���̺� ������Ʈ
	//��ǰ��ȣ�� �������� �Ͽ� ��ǰ��,��ǰ������,��������,����,�̹������ϸ��� ������Ʈ
	public void updateProduct(ProductVO productVO) throws Exception {
		//�����
		System.out.println("<<<����� DAO : updateProduct() ����>>>");
		
		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
	}

}//end of class
