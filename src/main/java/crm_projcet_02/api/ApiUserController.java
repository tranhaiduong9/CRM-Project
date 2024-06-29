package crm_projcet_02.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import crm_projcet_02.entity.Users;
import crm_projcet_02.payload.response.BaseResponse;
import crm_projcet_02.service.UserService;

/**
 *  packet "payload":
 *  	- packet response: Chứa các class liên quan tới format json trả ra cho client
 *  	- packet request: Chứa các class liên quan tới format json request (tham số) client truyền lên server
 *
 */

@WebServlet (name="ApiUserController", urlPatterns = {"/api/user","/api/user/delete"})
public class ApiUserController extends HttpServlet{

	private UserService userService = new UserService();
//	Khởi tạo thư viện GSON để xử dụng
	private Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getServletPath();
		
		if (path.equals("/api/user")) {
			List<Users> listUser = userService.getAllUsersAndRole();
			
			BaseResponse response = new BaseResponse();
			response.setStatusCode(200);
			response.setMessage("");
			response.setData(listUser);
			
//			Chuyển list hoặc mảng về dạng JSON
			String dataJson = gson.toJson(response);
			
//			Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.print(dataJson);
			out.flush();
		} else if(path.equals("/api/user/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			boolean isSuccess = userService.deleteUser(id);
			
			BaseResponse response = new BaseResponse();
			response.setStatusCode(200);
			response.setMessage(isSuccess ? "Xóa thành công" : "Xóa thất bại");
			response.setData(isSuccess);
			
			String json = gson.toJson(response);
			
//			Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.print(json);
			out.flush();
		}
		
	}
}
