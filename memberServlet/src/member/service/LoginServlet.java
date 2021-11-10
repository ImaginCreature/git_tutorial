package member.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.bean.MemberDTO;
import member.dao.MemberDAO;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 데이터
		request.setCharacterEncoding("UTF-8"); // 한글처리하는 거라서 id pwd 할 땐 꼭 안 써도 된다. 
		
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		
				
		//DB
		MemberDAO memberDAO = MemberDAO.getInstance();
		String name = memberDAO.login(id,pwd);
				
		
		//응답
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
		if(name == null)
			out.println("아이디 또는 비밀번호가 맞지 않습니다.");
		else
			out.println(name+"님 로그인");
		out.println("</body>");
		out.println("</html>");
	}



}
