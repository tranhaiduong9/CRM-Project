<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
</head>
<body>

	<div class="container">
		<div class="row mt-5">
			<div class="col-md-5 m-auto mt-5">
				<h3 class="text-center">ĐĂNG NHẬP HỆ THỐNG</h3>
				<div class="p-4 border mt-4">
					<form action="<c:url value ='/login'/>"
						method="post">
						<div class="form-group">
							<label>Email</label> <input type="email" class="form-control"
								name="email" id="user-email">
						</div>
						<div class="form-group">
							<label>Mật khẩu</label> <input type="password"
								class="form-control" name="password" id="user-password">
						</div>
						<button type="submit" class="btn btn-primary btn-login">Đăng
							nhập</button>
					</form>
					<c:if test="${isSuccess == false}">
						<span class="text-danger fs-6">Đăng nhập thất bại, vui lòng kiểm tra lại email hoặc mật khẩu</span>
					</c:if>
				</div>	
			</div>
		</div>
	</div>

	<script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
</body>
</html>
