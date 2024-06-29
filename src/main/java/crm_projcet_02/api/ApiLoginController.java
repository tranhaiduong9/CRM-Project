package crm_projcet_02.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import crm_projcet_02.payload.response.BaseResponse;
import crm_projcet_02.service.LoginService;

@WebServlet (name = "ApiLoginController", urlPatterns = {"/api/login"})
public class ApiLoginController extends HttpServlet{
	
	private LoginService loginService = new LoginService();
	private Gson gson = new Gson();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		boolean isSuccess = loginService.userLogin(email, password) ;
		BaseResponse response = new BaseResponse();
		response.setStatusCode(200);
		response.setMessage(isSuccess ? "Dang nhap thanh cong" : "Dang nhap that bai");
		response.setData(isSuccess);
		
		String json = gson.toJson(response);
		
//		Trả dữ liệu dạng JSON
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		out.print(json);
		out.flush();
		
	}
}
