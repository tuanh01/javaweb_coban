package edu.poly.admin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import edu.poly.common.PageInfo;
import edu.poly.common.PageType;
import edu.poly.common.UploadUtils;
import edu.poly.dao.VideoDao;
import edu.poly.model.Video;


@WebServlet({"/Admin/VideosManagement", "/Admin/VideosManagement/create","/Admin/VideosManagement/update", "/Admin/VideosManagement/delete", "/Admin/VideosManagement/edit","/Admin/VideosManagement/reset", })
@MultipartConfig
public class VideosManagementServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI().toString();
		if(url.contains("edit")) {
			edit(request, response);
			return;
		}
		if(url.contains("delete")) {
			delete(request, response);
			return;
		}
		if(url.contains("reset")) {
			reset(request, response);
			return;
		}
		
		Video video = new Video();
		video.setPoster("images/desktop.jpg");
		
		findAll(request, response);
		request.setAttribute("video", video);
		
		PageInfo.prepareAndForwardAdmin(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
	}	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI().toString();
		
		if(url.contains("create")) {
			create(request, response);
			return;
		}
		
		if(url.contains("update")) {
			update(request, response);
			return;
		}
		if(url.contains("delete")) {
			create(request, response);
			return;
		}
		if(url.contains("reset")) {
			reset(request, response);
			return;
		}
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("videoid");
		
		if (id == null) {
			request.setAttribute("error", "Video id khong duoc de trong!");
			PageInfo.prepareAndForwardAdmin(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
			return;
		}
		
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
		PageInfo.prepareAndForwardAdmin(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
		
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Video video = new Video();
		try {
			BeanUtils.populate(video, request.getParameterMap());
			VideoDao dao = new VideoDao();
			Video oldVideo = dao.findById(video.getVideoid());
			
			if(request.getPart("cover").getSize()==0) {
				video.setPoster(oldVideo.getPoster());
			}else {
				video.setPoster("uploads/" + UploadUtils.processUploadFiled("cover", request, 
						"/uploads", video.getVideoid()));
			}
						
			dao.update(video);
			
			request.setAttribute("video", video);
			request.setAttribute("message", "Cap nhat video thanh cong!");
			findAll(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error: "+e.getMessage());
		}
		PageInfo.prepareAndForwardAdmin(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
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

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("videoid");
		
		if(id == null) {
			request.setAttribute("error", "Video id khong duoc de trong!");
			PageInfo.prepareAndForwardAdmin(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
			return;
		}
		
		try {
			VideoDao dao = new VideoDao();
			Video video = dao.findById(id);
			
			request.setAttribute("video", video);
			findAll(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error: "+e.getMessage());
		}
		PageInfo.prepareAndForwardAdmin(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Video video = new Video();
	try {
		BeanUtils.populate(video, request.getParameterMap());
		
		video.setPoster("uploads/" + UploadUtils.processUploadFiled("cover", request, 
				"/uploads", video.getVideoid()));
		
		VideoDao dao = new VideoDao();
		dao.insert(video);
		
		request.setAttribute("video", video);
		request.setAttribute("message", "Video duoc chen thanh cong!");
	} catch (Exception e) {
		e.printStackTrace();
		request.setAttribute("error",  "Error: " + e.getMessage());
	}
	findAll(request, response);
	PageInfo.prepareAndForwardAdmin(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
}
	
	private void reset(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		Video video = new Video();
		video.setPoster("images/desktop.jpg");
		request.setAttribute("video", video);
		findAll(request, response);
		PageInfo.prepareAndForwardAdmin(request, response, PageType.VIDEO_MANAGEMENT_PAGE);
	}

}
