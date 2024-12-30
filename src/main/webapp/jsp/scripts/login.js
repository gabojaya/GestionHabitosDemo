const $btnSignIn = document.querySelector('.sign-in-btn'),
	$btnSignUp = document.querySelector('.sign-up-btn'),
	$signUp = document.querySelector('.sign-up'),
	$signIn = document.querySelector('.sign-in'),
	$registerBtn = document.querySelector('#register-btn'),
	$confirmationModal = document.querySelector('#confirmation-modal'),
	$confirmBtn = document.querySelector('#confirm-btn');



// Maneja el cambio de formularios entre "Iniciar sesión" y "Registrarse"
document.addEventListener('click', (e) => {
    if (e.target.classList.contains('sign-in-btn')) {
        // Realiza la llamada al controlador para mostrar el formulario de registro
        cambiarFormulario('solicitarRegistro');
    } else if (e.target.classList.contains('sign-up-btn')) {
        // Realiza la llamada al controlador para mostrar el formulario de inicio de sesión
        cambiarFormulario('solicitarIniciar');  
    }
});

function cambiarFormulario(ruta) {
    fetch(`LoginController?ruta=${ruta}`, {
        method: 'GET', // Usamos GET porque solo vamos a cambiar el contenido de la página
        headers: {
            'Accept': 'text/html' // Aceptamos una respuesta HTML (el mismo login.jsp con contenido actualizado)
        }
    })
    .then(response => response.text()) // Obtenemos el HTML actualizado
    .then(html => {
        // Actualizamos el contenido del body con el nuevo HTML
        document.body.innerHTML = html;
    })
    .catch(error => console.error('Error al cambiar de formulario:', error));
}





//// Maneja el cambio de formularios entre "Iniciar sesión" y "Registrarse"
//document.addEventListener('click', (e) => {
//    if (e.target.classList.contains('sign-in-btn')) {
//        // Mostrar la vista de registro
//        cambiarFormulario('registrar');
//    } else if (e.target.classList.contains('sign-up-btn')) {
//        // Mostrar la vista de iniciar sesión
//        cambiarFormulario('iniciar');  
//    }
//});
//
//function cambiarFormulario(formType) {
//    // Obtener los formularios de inicio de sesión y registro
//    const signInForm = document.querySelector('.container-form.sign-in');
//    const signUpForm = document.querySelector('.container-form.sign-up');
//    
//    // Resetear las clases activas
//    signInForm.classList.remove('active');
//    signUpForm.classList.remove('active');
//
//    // Cambiar la clase "active" según el formulario que queremos mostrar
//    if (formType === 'registrar') {
//        signUpForm.classList.add('active');
//    } else if (formType === 'iniciar') {
//        signInForm.classList.add('active');
//    }
//}


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



