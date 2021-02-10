$('document').ready(function(){
function clickHandler(event) {
	event.preventDefault();
	var path = $(this).attr('href');
	$('#exampleModalDefault').on('show.bs.modal', () => {
		$.get(path, function (data) {
            $('#exampleModalDefault').find('.modal-content').html(data);	
		}); 
	});
	$("#exampleModalDefault").modal('show');
}
$('td a').on('click', clickHandler);
})
