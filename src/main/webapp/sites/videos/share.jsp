<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="offset-3 col-6">
	<form action="" method="post">
	<input type="hidden" name="videoid" value="${videoid }"/>
		<div class="card">
			<div class="card-header">Send Video to Your Friends</div>
			<div class="card-body">
			<jsp:include page="/common/infom.jsp"></jsp:include>
				<div class="row">
					<div class="col">
						<div class="form-group">
						<label for="email">Your Friends emails:</label> 
						<input type="text"
						class="form-control" name="email" id="email"
						aria-describedby="emailHelperId" placeholder="Emails"> <small
						id="emailHelperId" class="form-text text-muted"> Email is
						required! </small>
				</div>
					</div>
				</div>
				
			</div>
			<div class="card-footer">
				<button class="btn btn-success">Send</button>
			</div>
		</div>
	</form>
</div>