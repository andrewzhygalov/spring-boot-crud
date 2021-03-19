$('document').ready(async function(){
	let data = await fetch('/users/');
	let users = await data.json();
	refresh();
	function refresh() {
		var tbody = $('tbody');
		tbody.html('');
		users.forEach(user => tbody.append(userRow(user)));
		$('.table .btn').on('click', clickHandler);
	}

$("#add-user").submit(add);

async function clickHandler (event) {
		event.preventDefault();
		var uri = $(this).attr('href');
		$("#updateForm").attr('action', uri);
		switch($(this).attr('name')) {
			case 'edit':
				$("#modalLabel").html('Edit user');
				$("#update").html('Edit');
				$("#updateForm").unbind();
				$("#updateForm").submit(edit);
				disableModalFields(false);
				break;
			default:
				$("#modalLabel").html('Delete user');
				$("#update").html('Delete');
				$("#updateForm").unbind();
				$("#updateForm").submit(del);
				disableModalFields(true);
		}
		
		var id = uri.slice(uri.length - 1);
		var user = users.find(u => u.id == id);
		setModalFields(user);
		$("#updateModal").modal('show');
}

async function add(event) {
		event.preventDefault();
		let response = await fetch($(this).attr('action'), 
			  { 
				method: 'POST', 
				headers: { 'Content-Type': 'application/json' }, 
				body: JSON.stringify({
					firstName: $("#floatingFirstName").val(), 
					lastName: $("#floatingLastName").val(),
					age: $("#floatingAge").val(),
					email: $("#floatingEmail").val(),
					password: $("#floatingPassword").val(),
					roles: $("#floatingRoles").val().split(',')
						.map(role => roleJson(role.trim()))
					})
			  }
			);
			if(response.ok) {
				user = await response.json();
				users.push(user);
			}
			refresh();
			$("#add-user input").val('');
			$('#nav-tab a[href="#nav-users"]').tab('show');
}
	
async function edit(event) {
		event.preventDefault();
		var response = await fetch($(this).attr('action'), 
			  { 
				method: 'PUT', 
				headers: { 'Content-Type': 'application/json' }, 
				body: JSON.stringify({
					id: $("#id").val(), 
					firstName: $("#firstName").val(), 
					lastName: $("#lastName").val(),
					age: $("#age").val(),
					email: $("#email").val(),
					password: $("#password").val(),
					roles: $("#updateRoleSelect").val().split(',')
						.map(role => roleJson(role.trim()))
					})
			  }
			);
			if(response.ok) {
				updated = await response.json();
				users = users.map(user => {
						if(user.id == updated.id) return updated;
						else return user;
					});
			}
			refresh();
			$("#updateModal").modal('hide');
}

async function del(event) {
		event.preventDefault();
		var id =  $("#id").val();
		let response = await fetch($(this).attr('action'), {method: 'DELETE'});
		if(response.ok) {
			users = users.filter(user => user.id != id);
		}
		refresh();
		$("#updateModal").modal('hide');
}

	function userRow(user) {
		return ` <tr>
					<td> ${user.id} </td>
					<td> ${user.firstName} </td>
					<td> ${user.lastName} </td>
					<td> ${user.age} </td>
					<td> ${user.email} </td>
					<td> ${rolesToString(user.roles)} </td>
					<td>
						<a href=${"/users/" + user.id} name="edit" class="btn btn-primary">
							Edit
						</a>
					</td>
					<td>
						<a href=${"/users/" + user.id} name="delete" class="btn btn-danger">
							Delete
						</a>
					</td>
				<tr>`;
	}
	
	
	
	function disableModalFields(disable) {
		$("#firstName").attr('disabled', disable);
		$("#lastName").attr('disabled', disable);
		$("#age").attr('disabled', disable);
		$("#email").attr('disabled', disable);
		$("#password").attr('disabled', disable);
		$("#updateRoleSelect").attr('disabled', disable);
	}
	
	function setModalFields(user) {
		$("#id").val(user.id);
		$("#firstName").val(user.firstName);
		$("#lastName").val(user.lastName);
		$("#age").val(user.age);
		$("#email").val(user.email);
		$("#password").val(user.password);
		$("#updateRoleSelect option").prop('selected', false);
		var roles = rolesToString(user.roles);
		if(roles.length == 2) {
			$("#updateRoleSelect option:last").prop('selected', true);
		} else if(roles.includes("USER")) {
			$("#updateRoleSelect option:first").prop('selected', true);
		} else {
			$("#adminOption").prop('selected', true);
		}
			
	}
	
	
	function rolesToString(roles) { 
		return roles.map(role => role.role.slice(5));
	}
	
	
	
})

function roleJson(role) {
		return {role: 'ROLE_' + role};
}