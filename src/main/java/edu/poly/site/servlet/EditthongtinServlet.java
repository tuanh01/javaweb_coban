package edu.poly.site.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import edu.poly.common.PageInfo;
import edu.poly.common.PageType;
import edu.poly.common.SessionUtils;
import edu.poly.dao.UserDao;
import edu.poly.model.User;

@WebServlet("/EditThongtin")
public class EditthongtinServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = SessionUtils.getLoginedUsername(request); //đọc thông tin username mà người dùng đã đăng nhập
		
		if(username == null) { //nếu username ko tồn tại thì người dùng chưa đăng nhập
			request.getRequestDispatcher("/Login").forward(request, response); // sẽ đưa tới trang đăng nhập để thực hiện đăng nhập
			return;
		}
		
		try {
			UserDao dao = new UserDao(); //khi username đăng nhập thành công
			User user = dao.findById(username); //Tìm kiếm đối tượng để trả về user
			
			request.setAttribute("user", user); //thiết lập đối tượng user để hiện thị lên form chỉnh sửa thông tin
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		
		}
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_EDITTHONGTIN_PAGE);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			User user = new User();// Tạo ra đối tượng user
			BeanUtils.populate(user, request.getParameterMap());//phương thức populate là để lấy thông tin mà người dùng đã gửi từ form Edit
			
			String username = SessionUtils.getLoginedUsername(request);//lấy thông tin username được đăng nhập hiện tại 
			UserDao dao = new UserDao();
			User oldUser = dao.findById(username);//Tìm kiếm username ở trong CSDL và trae về đối tượng user
			
			user.setAdmin(oldUser.getAdmin());//Không được thay đổi trường Admin mà phải dữ nguyên
			dao.update(user); //cập nhật thông tin của user vào trong CSDL
			request.setAttribute("message", "Thong tin da duoc cap nhat");//Hiển thị cho người dùng là đã cập nhật thành công
			request.setAttribute("user", user);//và thiết lập trở lại thông tin cua user
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}
		PageInfo.prepareAndForwardSite(request, response, PageType.SITE_EDITTHONGTIN_PAGE);//Hiển thị trang Edit
	}

}
