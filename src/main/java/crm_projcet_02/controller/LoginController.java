package crm_projcet_02.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm_projcet_02.config.MysqlConfig;
import crm_projcet_02.entity.Users;
import crm_projcet_02.payload.response.BaseResponse;
import crm_projcet_02.service.LoginService;

/**
 * package: - Controller: là nơi chứa toàn bộ file liên quan tới khai báo đường
 * dẫn và xử lý đường dẫn
 * 
 * Tính năng login: Bước 1: lấy dữ liệu người dùng nhập vào ô email và password
 * Bước 2: Viết 1 câu query kiểm tra email và password người dùng nhập vào có
 * tồn tại trong database hay ko Bước 3: Dùng JDBC kết nối tới CSDL và truyền
 * câu query ở bước 2 cho CSDL thực thi Bước 4: Kiểm tra dữ liệu, nếu có dữ liệu
 * thì là đăng nhập thành công và ngược lại là đăng nhập thất bại
 *
 */

@WebServlet(name = "LoginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	private LoginService loginService = new LoginService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		int maxAge = 8*60*60;
//		Cookie cookie = new Cookie("hoTen", URLEncoder.encode("Nguyễn Văn A", "UTF-8"));
//		cookie.setMaxAge(maxAge);
//		
//		// Yêu cầu client tạo cookie
//		resp.addCookie(cookie);
		HttpSession session = req.getSession(false);
		if (session == null) {
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		} else {
			session.invalidate();
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}	
		
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		boolean isSuccess = loginService.userLogin(email, password);
		
		Users user = new Users();
		user = loginService.getUserByEmailAndPassword(email, password);
		
		req.setAttribute("isSuccess", isSuccess);
		
		if (isSuccess) {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			
			System.out.println("Đăng nhập thành công");
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		} else {
			req.setAttribute("isSuccess", isSuccess);
			System.out.println("Đăng nhập thất bại");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
		
////		Bước 2:
////		? : đại diện cho tham số sẽ được truyền vào khi sử dụng JDBC
//		String query = "SELECT * FROM Users u WHERE u.email = ? AND u.pwd = ?";
////		Mở kết nối tới CSDL
//		Connection conn = MysqlConfig.getConnect();
//		try {
////			Chuẩn bị câu query truyền xuống CSDL thông qua PreparedStatement
//			PreparedStatement statement = conn.prepareStatement(query);
////			Gán giá trị cho tham số trong câu query (phần ? trong câu query)
//			statement.setString(1, email);
//			statement.setString(2, password);
////			THực thi câu query
////			 	+ executeUpdate: Nếu câu query khác câu SELECT => INSERT, UPDATE, DELETE,...
////				+ executeQuery: Nếu như câu query là câu SELECT
//			ResultSet resultSet = statement.executeQuery();
//			List<Users> listUser = new ArrayList<Users>();
////			Khi nào resultSet mà còn qua dòng tiếp theo được thì làm
//			while (resultSet.next()) {
////				Duyệt qua từng dòng dữ liệu query được trong DATABASE
//				Users users = new Users();
////				Lấy dữ liệu từ cột duyệt được và lưu vào thuộc tính của đối tượng users
//				users.setId(resultSet.getInt("id"));
//				users.setEmail(resultSet.getString("email"));
//
//				listUser.add(users);
//			}
//			if (listUser.size() > 0) {
//				HttpSession session = req.getSession();
//				for (Users list : listUser) {
//					session.setAttribute("id", list.getId());
//				}
//				// User tồn tại = đăng nhập thành công
//				System.out.println("Đăng nhập thành công");
//				req.getRequestDispatcher("index.jsp").forward(req, resp);
//			} else {
//				// User không tồn tại = đăng nhập thất bại
//				boolean isSuccess = false;
//				req.setAttribute("isSuccess", isSuccess);
//				System.out.println("Đăng nhập thất bại");
//				req.getRequestDispatcher("login.jsp").forward(req, resp);
//			}
//		} catch (Exception e) {
//			System.out.println("Lỗi thực thi truy vấn " + e.getLocalizedMessage());
//		}
	}
}
