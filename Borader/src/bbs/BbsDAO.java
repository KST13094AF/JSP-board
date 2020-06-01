package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {
	private Connection conn;
	private ResultSet rs;
	
	public BbsDAO() {
		try {
			String jdbcDriver = "jdbc:mysql://localhost:3306/border";
			String user = "root";
			String userPw = "1423";
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(jdbcDriver, user, userPw);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String getEamil(String userID) {
		String query = "select userEmail from user where userID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public String getDate() {
		String query = "select now()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public int getNext() {
		String query = "select bbsID from bbs order by bbsID desc";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1)+1;
			}
			return 1; //첫번째 게시물
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //DB 오류
	}
	
	public int write(String bbsTitle, String userID, String userEmail, String bbsContent) {
		String query = "insert into bbs values(?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, userEmail);
			pstmt.setString(5, getDate());
			pstmt.setString(6, bbsContent);
			pstmt.setInt(7, 1); //작성글 삭제 여부
			
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //DB 오류
	}
	
	public ArrayList<Bbs> getList(int pageNumber){
		String query = "select * from bbs where bbsID < ? and bbsAvailable = 1 order by bbsID desc limit 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setUserEmail(rs.getString(4));
				bbs.setBbsDate(rs.getString(5));
				bbs.setBbsContent(rs.getString(6));
				bbs.setBbsAvailable(rs.getInt(7));
				list.add(bbs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String query = "select * from bbs where bbsID < ? and bbsAvailable =1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			while(rs.next()){
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Bbs getBbs(int bbsID) {
		String query = "select * from bbs where bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsDate(rs.getString(5));
				bbs.setBbsContent(rs.getString(6));
				bbs.setBbsAvailable(rs.getInt(7));
				return bbs;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String query = "update bbs set bbsTitle = ?, bbsContent =? where bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //DB 오류
	}	
	public int delete(int bbsID) {
		String query = "update bbs set bbsAvailable = 0 where bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //DB 오류
	}
}
