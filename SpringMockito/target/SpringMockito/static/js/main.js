function checkPassword(form) {

	password1 = form.password.value;
	password2 = form.confirmed_password.value;
	if (password1 != password2) {
		alert("\nPassword did not match: Please try again...")
		return false;
	} else {
		console.log("password match");
		return true;
	}
}

function goBack() {
	window.history.back();
}
