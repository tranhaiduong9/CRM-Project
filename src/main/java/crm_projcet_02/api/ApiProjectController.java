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
import crm_projcet_02.service.ProjectService;

@WebServlet (name = "ApiProjectController", urlPatterns = "/api/project/delete")
public class ApiProjectController extends HttpServlet{
	
	ProjectService projectService = new ProjectService();
	Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id")) ;
		boolean isSuccess = projectService.deleteProject(id);
		
		BaseResponse response = new BaseResponse();
		response.setStatusCode(200);
		response.setMessage(isSuccess ? "Xóa thành công" : "Xóa thất bại");
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
