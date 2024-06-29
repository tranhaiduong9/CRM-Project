$(document).ready(function(){
		
	$(".btn-login").click(function(){
		var userEmail = document.getElementById("user-email").value;
		var userPassword = document.getElementById("user-password").value;
		$.ajax({
			method: "POST",
			url: "http://localhost:8080/crm_projcet_02/api/login",
			data: { email: userEmail, password: userPassword }
		})
		.done(function(msg){
			console.log(msg)
		})
	})
	
})