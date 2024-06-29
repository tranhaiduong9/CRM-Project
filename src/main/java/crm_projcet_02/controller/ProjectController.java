package crm_projcet_02.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm_projcet_02.config.MysqlConfig;
import crm_projcet_02.entity.Job;
import crm_projcet_02.entity.Project;
import crm_projcet_02.entity.Users;
import crm_projcet_02.service.ProjectService;
import crm_projcet_02.service.UserService;

@WebServlet (name = "ProjectController", urlPatterns = {"/groupwork-add", "/groupwork", "/groupwork-details", "/groupwork-edit"})
public class ProjectController extends HttpServlet{
	
	private ProjectService projectService = new ProjectService();
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
		case "/groupwork-add":
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
			break;
			
		case "/groupwork":
			List<Project> listProjects = new ArrayList<Project>();
			if (user.getRole().getName().equals("LEADER")) {
				listProjects = projectService.getByUserId(user.getId());
			} else {
				listProjects = projectService.getAllProject();
			}
			
			req.setAttribute("listProjects", listProjects);
			req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
			break;
		
		case "/groupwork-details":
			int id = Integer.parseInt(req.getParameter("id"));
			List<Users> listUsers =  new ArrayList<>();
			listUsers = projectService.getUserByProjectId(id);
			List<Job> listJobs = new ArrayList<>();
			listJobs = projectService.getJobByProjectId(id);
			
			req.setAttribute("listUsers", listUsers);
			req.setAttribute("listJobs", listJobs);
			req.getRequestDispatcher("groupwork-details.jsp").forward(req, resp);
			break;
			
		case "/groupwork-edit":
			int idProject = Integer.parseInt(req.getParameter("id"));
			Project project = projectService.getById(idProject);
			
			req.setAttribute("project", project);
			req.getRequestDispatcher("groupwork-edit.jsp").forward(req, resp);
			break;
			
		default:
			break;
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html; charset=UTF-8");
		String path = req.getServletPath();
		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("user");
		
		switch (path) {
		case "/groupwork-add":
			String projectName = req.getParameter("project-name");
			String startDate = req.getParameter("startdate");
			String endDate = req.getParameter("enddate");
			boolean isSuccess = projectService.addProject(projectName, startDate, endDate);
			
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
			break;

		case "/groupwork":
			int idProject = Integer.parseInt(req.getParameter("idProject"));
			projectName = req.getParameter("projectName");
			startDate = req.getParameter("startDate");
			endDate = req.getParameter("endDate");
			isSuccess = projectService.updateById(projectName, startDate, endDate, idProject);
			
			List<Project> listProjects = new ArrayList<Project>();
			if (user.getRole().getName().equals("LEADER")) {
				listProjects = projectService.getByUserId(user.getId());
			} else {
				listProjects = projectService.getAllProject();
			}
			
			req.setAttribute("isSuccess", isSuccess);
			req.setAttribute("listProjects", listProjects);
			req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
			break;
			
		default:
			break;
		}
		
	}
}
