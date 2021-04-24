package edu.poly.site.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.poly.common.EmailUtils;
import edu.poly.common.PageInfo;
import edu.poly.common.PageType;
import edu.poly.common.SessionUtils;
import edu.poly.dao.ShareDao;
import edu.poly.domain.Email;
import edu.poly.model.Share;
import edu.poly.model.User;
import edu.poly.model.Video;


@WebServlet("/ShareVideo")
public class ShareVideoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!SessionUtils.isLogin(request)) {
			response.sendRedirect("Login");
			return;
		}
		
		String videoid = request.getParameter("videoid");
		
		if(videoid == null) {
			response.sendRedirect("/HomePage");
			return;
		}
		request.setAttribute("videoid", videoid);		
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_SHARE_PAGE);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String emailAdddress = request.getParameter("email");
			String videoid = request.getParameter("videoid");			
			
			
			if(videoid == null) {
				request.setAttribute("error", "Video Id is null");
			}else {
				Email email = new Email();
				email.setFrom("nguyenhutuanh@gmail.com");
				email.setFromPassword("anhsairoi");
				email.setTo(emailAdddress);
				email.setSubject("Share Favorite Video");
				StringBuilder sb = new StringBuilder();
				sb.append("Dear Ms/Mr. <br>");
				sb.append("Video that thu vi toi muon chia se voi ban <br>");
				sb.append("Please click the link ").append(String.format("<a href='https://youtu.be/SGlBQR-ftVI'>View Video</a>", videoid));
				sb.append(" Regards<br>");
				sb.append("Administretor");
				
				email.setContent(sb.toString());
				EmailUtils.send(email);
				
				ShareDao dao = new ShareDao();
				Share share = new Share();
				share.setEmails(emailAdddress);
				share.setSharedDate(new Date());
				
				String username = SessionUtils.getLoginedUsername(request);
				User user = new User();
				user.setUsername(username);
				
				share.setUser(user);
				Video video = new Video();
				video.setVideoid(videoid);
				share.setVideoid(video);
				
				dao.insert(share);
				request.setAttribute("message", "Video da duoc gui link chia se !");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			request.setAttribute("error", e.getMessage());
		}
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_SHARE_PAGE);
	}

}
