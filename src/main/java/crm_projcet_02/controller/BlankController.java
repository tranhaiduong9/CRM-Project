package crm_projcet_02.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crm_projcet_02.entity.Users;

@WebServlet (name = "BlankController", urlPatterns = {"/blank","/404"})
public class BlankController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute("user");
		
		String path = req.getServletPath();
		switch (path) {
		case "/blank":
			req.getRequestDispatcher("blank.jsp").forward(req, resp);
			break;
		case "/404":
			req.getRequestDispatcher("404.jsp").forward(req, resp);
			break;
		default:
			break;
		}
	}
}
