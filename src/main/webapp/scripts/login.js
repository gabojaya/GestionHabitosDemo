const $btnSignIn = document.querySelector('.sign-in-btn'),
	$btnSignUp = document.querySelector('.sign-up-btn'),
	$signUp = document.querySelector('.sign-up'),
	$signIn = document.querySelector('.sign-in'),
	$registerBtn = document.querySelector('#register-btn'),
	$confirmationModal = document.querySelector('#confirmation-modal'),
	$confirmBtn = document.querySelector('#confirm-btn');


// Cambiar entre los formularios de inicio y registro
// Agregar evento de click
document.addEventListener('click', e => {
    if (e.target.classList.contains('sign-in-btn')) {
        window.location.href = '../LoginController?ruta=solicitarIniciar';
    } else if (e.target.classList.contains('sign-up-btn')) {
        window.location.href = '../LoginController?ruta=solicitarRegistro';
    }
});





//document.addEventListener('click', e => {
//    if (e.target === $btnSignIn || e.target === $btnSignUp) {
//        $signIn.classList.toggle('active');
//        $signUp.classList.toggle('active');
//    }
//});

//// Mostrar el modal al presionar "Registrarse"
//$registerBtn.addEventListener('click', (e) => {
//	e.preventDefault(); // Evitar redirección automática
//	$confirmationModal.classList.remove('hidden'); // Mostrar el modal
//});



