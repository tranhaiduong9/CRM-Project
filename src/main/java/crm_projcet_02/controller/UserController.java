package crm_projcet_02.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm_projcet_02.entity.Job;
import crm_projcet_02.entity.Role;
import crm_projcet_02.entity.Users;
import crm_projcet_02.service.UserService;

@WebServlet(name = "UserController", urlPatterns = {"/users", "/user-add", "/user-details", "/user-edit"})
public class UserController extends HttpServlet {

	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("user");
		
		String path = req.getServletPath();
		
		switch (path) {
			case "/users": 
				List<Users> listUsers = new ArrayList<Users>();
				listUsers = userService.getAllUsersAndRole();
				
				req.setAttribute("listUsers", listUsers);
				req.getRequestDispatcher("user-table.jsp").forward(req, resp);
				break;
				
			case "/user-add":
				List<Role> list = new ArrayList<Role>();
				list = userService.getAllRole();
				
				req.setAttribute("listRole", list);
				req.getRequestDispatcher("user-add.jsp").forward(req, resp);
				break;
				
			case "/user-details":
				int idUser = Integer.parseInt(req.getParameter("id"));
				Users userView = userService.getUserById(idUser);
				List<Job> listJobs = new ArrayList<>();
				listJobs = userService.getJobsList(idUser);
				
				req.setAttribute("listJobs", listJobs);
				req.setAttribute("userView", userView);
				req.getRequestDispatcher("user-details.jsp").forward(req, resp);
				break;
				
			case "/user-edit":
				list = userService.getAllRole();
				int id = Integer.parseInt(req.getParameter("id"));
				Users userSelected = userService.getUserById(id);
				
				req.setAttribute("listRole", list);
				req.setAttribute("userSelected", userSelected);
				req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
				break;
				
			default:
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html; charset=UTF-8");
		String path = req.getServletPath();
		
		switch (path) {
		case "/user-details":
			String fullName = req.getParameter("fullname");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String phone = req.getParameter("phone");
			int idRole = Integer.parseInt(req.getParameter("role"));
			boolean isSuccess = userService.addUser(fullName, email, password, phone, idRole);
			List<Role> list = new ArrayList<Role>();
			list = userService.getAllRole();

			req.setAttribute("listRole", list);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
			break;
			
		case "/user":
			fullName = req.getParameter("fullname");
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String userName = req.getParameter("userName");
			phone = req.getParameter("phone");
			idRole = Integer.parseInt(req.getParameter("role"));
			int id = Integer.parseInt(req.getParameter("idUser"));
			
			
			isSuccess = userService.updateUser(fullName, firstName, lastName, userName, phone, idRole, id);
			list = userService.getAllRole();
			
			List<Users> listUsers = new ArrayList<Users>();
			listUsers = userService.getAllUsersAndRole();
			
			req.setAttribute("listRole", list);
			req.setAttribute("isSuccess", isSuccess);
			req.setAttribute("listUsers", listUsers);
			req.getRequestDispatcher("user-table.jsp").forward(req, resp);
			break;

		default:
			break;
		}
		

////		Lấy tham số từ thẻ form truyền qua khi người dùng click button submit
//		String fullName = req.getParameter("fullname");
//		String email = req.getParameter("email");
//		String password = req.getParameter("password");
//		String phone = req.getParameter("phone");
//		int idRole = Integer.parseInt(req.getParameter("role"));
//		
////		Tạo câu truy vấn và truyền giá trị
//		String query = "INSERT INTO Users (fullName, email, pwd, phone, id_role)\r\n"
//				+ "VALUES (?, ?, ?, ?, ?)";
////		Mở kết nối tới CSDL
//		Connection connection = MysqlConfig.getConnect();
//		boolean isSuccess = false;
//		try {
////			Truyền câu query vào DATABASE đã được kết nối
//			PreparedStatement statement = connection.prepareStatement(query);
//			statement.setString(1, fullName);
//			statement.setString(2, email);
//			statement.setString(3, password);
//			statement.setString(4, phone);
//			statement.setInt(5, idRole);
////			Thực hiện câu query
//			int count =  statement.executeUpdate();
////			Kiểm tra câu query
//			if (count >0) {
////				insert dữ liệu thành công
//				isSuccess = true;
//				System.out.println("Thêm thành công");
//			} 
//		} catch (Exception e) {
//			System.out.println("Lỗi thêm dữ liệu User " + e.getLocalizedMessage());
//		} finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				System.out.println("Lỗi đóng kết nối " + e.getLocalizedMessage());
//			}
//		}

//		List<Role> list = new ArrayList<Role>();
//		try {
//			list = getAllRole();
//		} catch (SQLException e) {
//			System.out.println("Lỗi get all role " + e.getLocalizedMessage());
//		}
//		req.setAttribute("listRole", list);
//		req.setAttribute("isSuccess", isSuccess);
////		Trả lại giao diện user-add
//		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}

//	Xây dựng hàm trả ra list Role
//	private List<Role> getAllRole() throws SQLException {
////		Chuẩn bị câu query
//		String query = "SELECT * FROM Role";
////		Mở kết nối CSDL
//		Connection connection = MysqlConfig.getConnect();
////		Truyền câu query vào connection
//		PreparedStatement preparedStatement = connection.prepareStatement(query);
////		Thực thi câu query và được 1 danh sách dữ liệu
//		ResultSet resultSet = preparedStatement.executeQuery();
//		List<Role> listRole = new ArrayList<Role>();
//		
////		Duyệt qua từng dòng dữ liệu
//		while(resultSet.next()) {
//			Role role = new Role();
//			role.setId(resultSet.getInt("id"));
//			role.setName(resultSet.getString("name"));
//			role.setDescription(resultSet.getString("description"));
//			
//			listRole.add(role);
//		}
//		return listRole;
//	}
}
