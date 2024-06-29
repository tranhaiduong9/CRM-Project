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

import crm_projcet_02.entity.Role;
import crm_projcet_02.entity.Users;
import crm_projcet_02.service.RoleService;

@WebServlet(name="RoleController", urlPatterns = {"/role-add","/role", "/role-edit"})
public class RoleController extends HttpServlet{
	
	private RoleService roleService = new RoleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html; charset=UTF-8"); 
	    
		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("user");
		
//		Lấy ra đường dẫn mà người dùng đang gọi
		String path = req.getServletPath();
//		Kiểm tra đường dẫn người dùng đang gọi là đường dẫn nào để hiển thị giao diện tương ứng
		if (path.equals("/role-add")) {
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
		} 
		else if (path.equals("/role")) {
			List<Role> listRoles = new ArrayList<>();
			listRoles = roleService.getAllRole();
			
			req.setAttribute("listRoles", listRoles);
			req.getRequestDispatcher("role-table.jsp").forward(req, resp);
		} 
		else if (path.equals("/role-edit")) {
			int id = Integer.parseInt(req.getParameter("id")) ;
			Role roleSelected = roleService.getById(id);
			
			req.setAttribute("roleSelected", roleSelected);
			req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html; charset=UTF-8"); 
	    
		String path = req.getServletPath();
		
		switch (path) {
		case "/role-add":
			String roleName = req.getParameter("role-name");
			String desc = req.getParameter("desc");
			boolean isSuccess = roleService.addRole(roleName, desc);
			
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			break;
			
		case "/role":
			roleName = req.getParameter("role-name");
			desc = req.getParameter("desc");
			int id = Integer.parseInt(req.getParameter("id")); 
			isSuccess = roleService.updateById(roleName, desc, id);
			
			List<Role> listRoles = new ArrayList<>();
			listRoles = roleService.getAllRole();
			
			req.setAttribute("listRoles", listRoles);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("role-table.jsp").forward(req, resp);
			break;

		default:
			break;
		}
	}	
}
