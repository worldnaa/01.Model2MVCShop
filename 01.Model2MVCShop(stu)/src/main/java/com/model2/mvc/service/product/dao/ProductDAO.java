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
	//매개변수로 받은 ProductVO 값을, PRODUCT 테이블에 순서대로 삽입
	public void insertProduct(ProductVO productVO) throws SQLException {
		System.out.println("<<<여기는 DAO : insertProduct() 실행>>>");
		
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
		System.out.println("<<<여기는 DAO : insertProduct() 종료>>>");
	}
	
	//매개변수로 받은 prodNo(상품번호)에 해당하는 정보를 PRODUCT 테이블에서 검색
	public ProductVO findProduct(int prodNo) throws Exception {
		//디버깅
		System.out.println("<<<여기는 DAO : findProduct() 실행>>>");
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		ProductVO productVO = null;
		while (rs.next()) {
			//ProductVO 인스턴스 생성 
			productVO = new ProductVO();
			//ProductVO에 PRODUCT 테이블에서 가져온 값 셋팅 
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
	
	//매개변수로 받은 SearchVO의 값을 이용하여 ArrayList에 ProductVO 값 저장
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		//디버깅
		System.out.println("<<<여기는 DAO : getProductList() 실행>>>");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT ";
		System.out.println("sql은? "+sql);//디버깅
		
		//SearchCondition에 값이 있을 경우
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where PROD_NO like '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where PROD_NAME like '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("2")) {
				sql += " where PRICE like '%" + searchVO.getSearchKeyword() + "%'";
			}
			System.out.println("sql은? "+sql);//디버깅
		}
		
		sql += " order by PROD_NO";
		System.out.println("sql은? "+sql);//디버깅

		PreparedStatement stmt = con.prepareStatement(sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		//boolean last() : 마지막 행으로 커서 이동
		rs.last();
		
		//int getRow() : 현재 행번호 검색 (마지막 행번호 = 전체 행의 수)
		int total = rs.getRow();
		System.out.println("전체 로우의 수: " + total);//디버깅

		HashMap<String,Object> map = new HashMap<String,Object>();
		
		//"count"에 total(전체 로우의 수) 저장 
		map.put("count", new Integer(total));
		System.out.println("map은? "+map);//디버깅

		//boolean absolute(int row) : 지정된 행번호로 커서 이동
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		
		//디버깅
		System.out.println("searchVO.getPage(): " + searchVO.getPage());
		System.out.println("searchVO.getPageUnit(): " + searchVO.getPageUnit());
 
		//<ProductVO>만 들어갈 수 있는 ArrayList 생성
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		
		//전체 로우의 수가 1이상이면
		if (total > 0) {
			//0~2까지 반복문 3번 실행
			for (int i=0; i<searchVO.getPageUnit(); i++) {
				//ProductVO에 PRODUCT 테이블에서 값을 가져와 셋팅
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
					vo.setProTranCode("1");//재고없음
				}else {
					vo.setProTranCode("2");//판매중
				}
				
				//list에 ProductVO의 셋팅된 값 저장
				list.add(vo);
				
				if (!rs.next()) {
					break;
				}				
			}
		}
		
		System.out.println("list.size() : "+ list.size()); //디버깅
		
		//"list"에 list(ProductVO 데이터) 저장
		map.put("list", list);
		
		System.out.println("map().size() : "+ map.size()); //디버깅
		System.out.println("map은? "+map);

		con.close();	
		return map;
	}
	
	//매개변수로 받은 ProductVO에 있는 값을 PRODUCT 테이블에 업데이트
	//상품번호를 조건으로 하여 상품명,상품상세정보,제조일자,가격,이미지파일명을 업데이트
	public void updateProduct(ProductVO productVO) throws Exception {
		//디버깅
		System.out.println("<<<여기는 DAO : updateProduct() 실행>>>");
		
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
