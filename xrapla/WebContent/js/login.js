$(document).ready(function() {
	$("#submit").click(function() {
		var user = $("#username").val();
		var pass = $("#password").val();
		
		if (user && pass) {
			$.post("ajaxLogin", {
				username: user,
				password: pass
			}).done(function(data) {
				if (data == "success") {
					window.location.href = "page?p=index";
				}
				else {
					error("Invalid username / password!");
				}
			}).fail(function() {
				error("An error occured while logging in.<br>Please try again later.");
			});
		}
		else {
			error("Please enter an username<br>and a password.");
		}
	});
});

function error(msg) {
	$("#error").html(msg);
	$("#error").slideDown("slow");
}