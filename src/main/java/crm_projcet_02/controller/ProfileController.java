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
import crm_projcet_02.entity.Status;
import crm_projcet_02.entity.Users;
import crm_projcet_02.service.JobService;
import crm_projcet_02.service.ProfileService;

@WebServlet(name = "ProfileController", urlPatterns = { "/profile", "/profile-edit", "/profile-add" })
public class ProfileController extends HttpServlet {

	private ProfileService profileService = new ProfileService();
	private JobService jobService = new JobService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("user");

		String path = req.getServletPath();

		switch (path) {
		case "/profile":
			List<Job> listJobs = new ArrayList<Job>();
			listJobs = profileService.getByUserId(user.getId());
			
			req.setAttribute("listJobs", listJobs);
			req.setAttribute("user", user);
			req.getRequestDispatcher("profile.jsp").forward(req, resp);
			break;

		case "/profile-edit":
			List<Status> listStatus = new ArrayList<Status>();
			listStatus = profileService.getAllStatus();
			int idJob = Integer.parseInt(req.getParameter("id-job"));

			listJobs = profileService.getByUserId(user.getId());

			req.setAttribute("idJob", idJob);
			req.setAttribute("listStatus", listStatus);
			req.setAttribute("listJobs", listJobs);
			req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
			break;

		default:
			break;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		switch (path) {
		case "/profile":
			HttpSession session = req.getSession();
			Users user = (Users) session.getAttribute("user");
			
			int idJob = Integer.parseInt(req.getParameter("idJob"));
			int status = Integer.parseInt(req.getParameter("status"));
			boolean isSuccess = profileService.updateStatus(user.getId(), idJob, status);
			
			List<Job> listJobs = new ArrayList<Job>();
			listJobs = profileService.getByUserId(user.getId());
			
			req.setAttribute("listJobs", listJobs);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("profile.jsp").forward(req, resp);
			break;

		default:
			break;
		}
	}
}
