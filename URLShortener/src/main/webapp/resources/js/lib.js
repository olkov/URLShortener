var urlRegex = /(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9]\.[^\s]{2,})/;
var phoneNumberRegex = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/;
var passwordLength = 4;

function validatePhoneNumber(input, focus) {
	return validateInput(input, phoneNumberRegex.test(input.val()), focus);
}

function validatePassword(input, focus) {
	return validateInput(input, input.val().length >= passwordLength, focus);
}

function validatePasswords(input1, input2, focus) {
	return validateInput(input2, input1.val() == input2.val(), focus);
}

function validateInput(input, condition, focus) {
	if(condition) {
		input.removeClass("is-invalid");
		return true;
	} else {
		input.addClass("is-invalid");
		if(focus) {
			input.focus();
		}
		return false;
	}
}