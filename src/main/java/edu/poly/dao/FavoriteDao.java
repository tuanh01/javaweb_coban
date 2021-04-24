package edu.poly.dao;

import java.util.List;
 
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import edu.poly.domain.FavoriteReport;
import edu.poly.domain.FavoriteUserRerport;
import edu.poly.model.Favorite;

public class FavoriteDao extends AbtracEntityDao<Favorite>{
	public FavoriteDao() {
		super(Favorite.class);
	}
	
	public List<FavoriteUserRerport> reportFavoriteUserByVideo(String videoid){
		String jpql = "select new edu.poly.domain.FavoriteUserRerport(f.user.username, f.user.fullname"
				+"f.user.email, f.likedDate) from Favorite f where f.video.videoid = :videoid";
		EntityManager em = JpaUtils.getEntityManager();
		
		List<FavoriteUserRerport> list = null;
		
		try {
			TypedQuery<FavoriteUserRerport> query = em.createQuery(jpql, FavoriteUserRerport.class);
			
			query.setParameter("videoid", videoid);
			list = query.getResultList();
		} finally {
			em.close();
		}
		return list;
	}
	
	public List<FavoriteReport> reportFavoriteByVideos(){
		String jpql = "select new edu.poly.domain.FavoriteReport(f.video.title, count(f), min(f.likedDate), max(f.likedDate) )"
					+ "from Favorite f group by f.video.title";
		EntityManager em = JpaUtils.getEntityManager();
		
		List<FavoriteReport> list = null;
		
		try {
			TypedQuery<FavoriteReport> query = em.createQuery(jpql, FavoriteReport.class);
			
			list = query.getResultList();
		} finally {
			em.close();
		}
		return list;
	}
}
