$(document).ready(function(){
	console.log("herh");
	$(".homepage-name").addClass('animated shake').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function() {
		$(this).removeClass('animated shake');
	})
}
)