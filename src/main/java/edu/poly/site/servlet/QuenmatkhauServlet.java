package edu.poly.site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.poly.common.EmailUtils;
import edu.poly.common.PageInfo;
import edu.poly.common.PageType;
import edu.poly.dao.UserDao;
import edu.poly.domain.Email;
import edu.poly.model.User;

/**
 * Servlet implementation class QuenmatkhauServlet
 */
@WebServlet("/QuenmatkhauServlet")
public class QuenmatkhauServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_QUENMATKHAU_PAGE);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String emailAdddress = request.getParameter("email");
			String username = request.getParameter("username");
			
			UserDao dao = new UserDao();
			User user = dao.findByUsernameAndEmail(username, emailAdddress);
			
			if(user == null) {
				request.setAttribute("error", "Khong tim thay dia chi email");
			}else {
				Email email = new Email();
				email.setFrom("nguyenhutuanh@gmail.com");
				email.setFromPassword("anhsairoi");
				email.setTo(emailAdddress);
				email.setSubject("Forgot Password Function");
				StringBuilder sb = new StringBuilder();
				sb.append("Dear ").append(username).append("<br>");
				sb.append("Bạn đang sử dụng chức năng quên mật khẩu. <br>");
				sb.append("Mật khẩu của bạn là:  <b>").append(user.getPassword()).append("</b>").append("<br>");
				sb.append(" Regards<br>");
				sb.append("Administretor");
				
				email.setContent(sb.toString());
				EmailUtils.send(email);
				
				request.setAttribute("message", "Email da duoc gui den dia chi." + "Xin vui long kiem tra email cua ban");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			request.setAttribute("error", e.getMessage());
		}
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_QUENMATKHAU_PAGE);
	}

}
