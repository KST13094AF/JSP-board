package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
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
	
	public int login(String userID, String userPW) {
		String query = "select userPW from user where userID = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPW)) {
					return 1; //로그인 성공
				}
				else
					return 0; //비밀번호 틀림
			}
			return -1; //아이디 없
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //
	}
	
	public int join(User user) {
		String query = "insert into user values(?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPW());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserNum());
			pstmt.setString(5, user.getUserGender());
			pstmt.setString(6, user.getUserEmail());
			
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //DB오류
	}
}
