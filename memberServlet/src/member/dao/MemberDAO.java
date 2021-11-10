package member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import member.bean.MemberDTO;

public class MemberDAO {
	private Connection conn=null;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "bit";

	private static MemberDAO instance = null; // 싱글톤. static 이란 한 번 만들면 소멸되지 않는다.
	//				쿨래스명		객체명   = null 로 초기화되는 시점은 한 번 뿐이다. 
	
	public static MemberDAO getInstance() {
	// Instance 란 메모리 생성을 의미한다.
		if(instance == null) {
		// instance 는 static 이기 때문에 null로 초기화된 경후는 1번 밖에 없다. 
			synchronized (MemberDAO.class) {
				instance = new MemberDAO(); //생성
			}
		}
		
		return instance;
	}
	
	
	//driver loading
	public MemberDAO() {
		try {
			Class.forName(driver);
			System.out.println("드라이버 로딩 성공");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace(); //에러 내용을 출력
		}
	}
	
	//Connection
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password); //메소드 이용하여 생성
			System.out.println("접속 성공");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void write(MemberDTO memberDTO) {
		String sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,?,?, sysdate)";
		getConnection();
	
		try {
			pstmt = conn.prepareStatement(sql); // 생성
			pstmt.setString(1, memberDTO.getName());
			pstmt.setString(2, memberDTO.getId());
			pstmt.setString(3, memberDTO.getPwd());
			pstmt.setString(4, memberDTO.getGender());
			pstmt.setString(5, memberDTO.getEmail1());
			pstmt.setString(6, memberDTO.getEmail2());
			pstmt.setString(7, memberDTO.getTel1());
			pstmt.setString(8, memberDTO.getTel2());
			pstmt.setString(9, memberDTO.getTel3());
			pstmt.setString(10, memberDTO.getZipcode());
			pstmt.setString(11, memberDTO.getAddr1());
			pstmt.setString(12, memberDTO.getAddr2());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null)
				try {
					pstmt.close();
					if(conn != null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}


	public String login(String id, String pwd) {
		String name = null;
		String sql = "select * from member where id=? and pwd=?";
		getConnection();
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2,pwd);
			
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) name = rs.getString("name");
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return name;
	}


	public boolean isCheckId(String id) {
		boolean exist = false;
		String sql = "select * from member where id=?";

		getConnection();
				
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) exist = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return exist;
	}

	
}
