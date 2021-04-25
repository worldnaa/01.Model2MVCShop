package com.model2.mvc.service.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.user.vo.UserVO;

public class UserDAO {
	//Field
	//Constructor
	public UserDAO(){
	}
	
	//Method
	//유저 정보 등록을 위한 DBMS를 수행
	public void insertUser(UserVO userVO) throws Exception {
		System.out.println("<<<<< UserDAO : insertUser() 시작 >>>>>");
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into USERS values (?,?,?,'user',?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserId());
		stmt.setString(2, userVO.getUserName());
		stmt.setString(3, userVO.getPassword());
		stmt.setString(4, userVO.getSsn());
		stmt.setString(5, userVO.getPhone());
		stmt.setString(6, userVO.getAddr());
		stmt.setString(7, userVO.getEmail());
		stmt.executeUpdate();
		
		con.close();
		System.out.println("<<<<< UserDAO : insertUser() 종료 >>>>>");
	}

	//유저 정보 조회를 위한 DBMS를 수행
	public UserVO findUser(String userId) throws Exception {
		System.out.println("<<<<< UserDAO : findUser() 시작 >>>>>");
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from USERS where USER_ID=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userId);

		ResultSet rs = stmt.executeQuery();

		UserVO userVO = null;
		while (rs.next()) {
			userVO = new UserVO();
			userVO.setUserId(rs.getString("USER_ID"));
			userVO.setUserName(rs.getString("USER_NAME"));
			userVO.setPassword(rs.getString("PASSWORD"));
			userVO.setRole(rs.getString("ROLE"));
			userVO.setSsn(rs.getString("SSN"));
			userVO.setPhone(rs.getString("CELL_PHONE"));
			userVO.setAddr(rs.getString("ADDR"));
			userVO.setEmail(rs.getString("EMAIL"));
			userVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();

		System.out.println("<<<<< UserDAO : findUser() 종료 >>>>>");
		return userVO;
	}

	//유저 목록 조회를 위한 DBMS를 수행
	public HashMap<String,Object> getUserList(SearchVO searchVO) throws Exception {
		System.out.println("<<<<< UserDAO : getUserList() 시작 >>>>>");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from USERS ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where USER_ID like '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where USER_NAME like '%" + searchVO.getSearchKeyword() + "%'";
			}
			System.out.println("sql은? "+sql);//디버깅
		}
		sql += " order by USER_ID";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<UserVO> list = new ArrayList<UserVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				UserVO vo = new UserVO();
				vo.setUserId(rs.getString("USER_ID"));
				vo.setUserName(rs.getString("USER_NAME"));
				vo.setPassword(rs.getString("PASSWORD"));
				vo.setRole(rs.getString("ROLE"));
				vo.setSsn(rs.getString("SSN"));
				vo.setPhone(rs.getString("CELL_PHONE"));
				vo.setAddr(rs.getString("ADDR"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setRegDate(rs.getDate("REG_DATE"));

				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
		
		System.out.println("<<<<< UserDAO : getUserList() 종료 >>>>>");
		return map;
	}

	//유저 정보 수정을 위한 DBMS를 수행
	public void updateUser(UserVO userVO) throws Exception {
		System.out.println("<<<<< UserDAO : updateUser() 시작 >>>>>");
		
		Connection con = DBUtil.getConnection();

		String sql = "update USERS set USER_NAME=?,CELL_PHONE=?,ADDR=?,EMAIL=? where USER_ID=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, userVO.getUserName());
		stmt.setString(2, userVO.getPhone());
		stmt.setString(3, userVO.getAddr());
		stmt.setString(4, userVO.getEmail());
		stmt.setString(5, userVO.getUserId());
		stmt.executeUpdate();
		
		con.close();
		System.out.println("<<<<< UserDAO : updateUser() 종료 >>>>>");
	}
	
}//end of class