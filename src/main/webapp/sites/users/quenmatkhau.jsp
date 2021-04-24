<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="offset-4 col-4">
	<form action="QuenmatkhauServlet" method="post">
		<div class="card mt-5">
			<div class="card-header">
				<b>Quên mật khẩu</b>
			</div>
			<div class="card-body">
				<jsp:include page="/common/infom.jsp"></jsp:include>
				<div class="row">
					<div class="col">
						<div class="form-group">
					<label for="username">Username</label> <input type="text"
						class="form-control" name="username" id="username"
						aria-describedby="usernameHid" placeholder="Username"> <small
						id="usernameHid" class="form-text text-muted">Username is
						required</small>
				</div>
				<div class="form-group">
					<label for="email">Email</label> <input type="email"
						class="form-control" name="email" id="email" placeholder="Email"
						required>
				</div>
					</div>
				</div>
				
			</div>
			<div class="card-footer text-muted">
				<button class="btn btn-success">Thực hiện</button>
			</div>
		</div>
	</form>
</div>