package edu.poly.site.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.poly.common.PageInfo;
import edu.poly.common.PageType;
import edu.poly.common.SessionUtils;
import edu.poly.dao.VideoDao;
import edu.poly.model.Video;


@WebServlet("/UnlikeVideo")
public class UnlikeVideoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!SessionUtils.isLogin(request)) {
			response.sendRedirect("Login");
			return;
		}
		
		String videoid = request.getParameter("videoid");
		
		if(videoid == null) {
			response.sendRedirect("/FavoriteServlet");
			return;
		}
//		request.setAttribute("videoid", videoid);	
		String id = request.getParameter("videoid");
		try {
			VideoDao dao = new VideoDao();
			Video video = dao.findById(id);
			
			if(video == null) {
				request.setAttribute("error", "Khong tim thay video!");
				PageInfo.prepareAndForwardAdmin(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
				return;
			}
			
			dao.delete(id);
			request.setAttribute("message", "Xoa video thanh cong !");			
			request.setAttribute("video", new Video());
			findAll(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error: "+e.getMessage());
		}
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_FAVORITE_PAGE);
	}
	private void findAll(HttpServletRequest request, HttpServletResponse response) {		
		try {
			
			VideoDao dao = new VideoDao();
			
			List<Video> list = dao.findAll();
						
			request.setAttribute("videos", list);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error: "+e.getMessage());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}

}
