<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="col-3">
	<div class="row mt-3 mb-3">
		<div class="col">
			<div class="card">
				<div class="card-header ml-2 list-group-item active aria-current="true"">
					<i class="fa fa-align-justify mr-2"></i>Favorites
				</div>
				<ul class="list-group list-group-flush">
					<div class="list-group-item btn btn-outline-info">Laptop</div>
					<div class="list-group-item btn btn-outline-info">Computer</div>
					<div class="list-group-item btn btn-outline-info">Tai Nghe</div>
					<div class="list-group-item btn btn-outline-info">Bàn Phím</div>
					<div class="list-group-item btn btn-outline-info">Iphone 13</div>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="col-9">
	<div class="row p-2">
		<c:forEach var="item" items="${ videos }">
			<div class="col-3 mt-2">
				<div class="card text-center">
					<div class="card-body">
						<img
							src="${ empty item.poster ? 'images/macbook.jpg': item.poster}"
							width="100%" alt="" class="img-fluid">
						<div class="row border-top mt-2">
							<b>${ item.title }</b>
						</div>
					</div>
					<div class="card-footer">
						<a href="LikeVideo?videoid=${ item.videoid }"
							class="btn btn-info ">Like</a> <a
							href="ShareVideo?videoid=${ item.videoid }"
							 class="btn btn-dark">Share</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="row">
		<div class="col">
			<nav aria-label="Page navigation">
				<ul class="pagination justify-content-center">
					<li class="page-item disabled"><a class="page-link" href="#"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							<span class="sr-only">Previous</span>
					</a></li>
					<li class="page-item active"><a class="page-link" href="#">1</a></li>
					<li class="page-item"><a class="page-link" href="#">2</a></li>
					<li class="page-item"><a class="page-link" href="#"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
							class="sr-only">Next</span>
					</a></li>
				</ul>
			</nav>
		</div>
	</div>
</div>
