$(document).ready(function() {
	$("#login").submit(function() {
		$("#error").slideUp("slow");
		
		var user = $("#username").val();
		var pass = $("#password").val();
		
		if (user && pass) {
			var login = $("#submit").val();
			$("#submit").val("Please wait...");
			$("#login input").prop("disabled", true);
			
			$.post("ajaxLogin", {
				username: user,
				password: pass
			}).done(function(data) {
				if (data == "success") {
					window.location.href = "page?p=index";
				}
				else {
					error("Invalid username / password!");
					$("#submit").val(login);
					$("#login input").prop("disabled", false);
				}				
			}).fail(function() {
				error("An error occured while logging in.<br>Please try again later.");
				$("#submit").val(login);
				$("#login input").prop("disabled", false);
			});
		}
		else {
			error("Please enter an username<br>and a password.");
		}
		
		return false;
	});
});

function error(msg) {
	$("#error").html(msg);
	$("#error").slideDown("slow");
}