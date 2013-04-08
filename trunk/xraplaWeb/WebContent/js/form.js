$(document).ready(function() {
	var req = $(document.createElement("span"));
	req.attr("class", "required");
	req.html("(*)");
	
	$("[data-required='true']").each(function() {
		var e = $(this);
		req.insertBefore(e);
	});
	
	$("#form").submit(function() {
		$("#error").slideUp("slow");
		var success = true;
		$("[data-required='true']").each(function() {
			if (!$(this).val()) {
				error("Please fill in all required fields.");
				success = false;
				return false;
			}
		});
		return success;
	});
});

function error(msg) {
	$("#error").html(msg);
	$("#error").slideDown("slow");
}