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
import crm_projcet_02.entity.Project;
import crm_projcet_02.entity.Status;
import crm_projcet_02.entity.Users;
import crm_projcet_02.service.JobService;
import crm_projcet_02.service.ProfileService;
import crm_projcet_02.service.ProjectService;

@WebServlet (name="JobController", urlPatterns = {"/task-add", "/task", "/task-edit"})
public class JobController extends HttpServlet {
	
	private JobService jobService = new JobService();
	private ProjectService projectService = new ProjectService();
	private ProfileService profileService = new ProfileService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    resp.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("user");
		
		String path = req.getServletPath();
		
		switch (path) {
		case "/task-add":
			List<Project> listProjects = new ArrayList<Project>();
			listProjects = jobService.getAllProject();
			List<Users> listUsersFullName = new ArrayList<Users>();
			listUsersFullName = jobService.getAllUserFullName();
			
			req.setAttribute("listUsersFullName", listUsersFullName);
			req.setAttribute("listProjects", listProjects);
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
			break;
			
		case "/task":
			List<Job> listJobs = new ArrayList<Job>();
			listJobs = jobService.getAllJob();
			
			req.setAttribute("listJobs", listJobs);
			req.getRequestDispatcher("task.jsp").forward(req, resp);
			break;
			
		case "/task-edit":
			List<Status> listStatus = new ArrayList<Status>();
			listStatus = profileService.getAllStatus();
			
			int idJob = Integer.parseInt(req.getParameter("id"));
			Job job = jobService.getById(idJob);
			
			listJobs = jobService.getAllJob();
			
			req.setAttribute("listJobs", listJobs);
			req.setAttribute("listStatus", listStatus);
			req.setAttribute("job", job);
			req.getRequestDispatcher("task-edit.jsp").forward(req, resp);
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
		
		switch (path) {
		case "/task-add":
			String jobName = req.getParameter("job-name");
			int idUser = Integer.parseInt(req.getParameter("user-full-name")) ;
			String startDate = req.getParameter("startdate");
			String endDate = req.getParameter("enddate");
			int idProject = Integer.parseInt(req.getParameter("project")) ;
			
			boolean isSuccess = jobService.addJob(jobName, startDate, endDate, idProject, idUser);
			List<Project> listProjects = new ArrayList<Project>();
			listProjects = jobService.getAllProject();
			List<Users> listUsersFullName = new ArrayList<Users>();
			listUsersFullName = jobService.getAllUserFullName();
			
			req.setAttribute("isSuccess", isSuccess);
			req.setAttribute("listUsersFullName", listUsersFullName);
			req.setAttribute("listProjects", listProjects);
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
			break;
		
		case "/task":
			jobName = req.getParameter("jobName");
			startDate = req.getParameter("startDate");
			endDate = req.getParameter("endDate");
			int status = Integer.parseInt(req.getParameter("status"));
			int idJob = Integer.parseInt(req.getParameter("idJob")) ;
			isSuccess = jobService.updateById(jobName, startDate, endDate, status, idJob);
			
			List<Job> listJobs = new ArrayList<Job>();
			listJobs = jobService.getAllJob();
			
			req.setAttribute("isSuccess", isSuccess);
			req.setAttribute("listJobs", listJobs);
			req.getRequestDispatcher("task.jsp").forward(req, resp);
			break;
			
		default:
			break;
		}
		
	}
}
