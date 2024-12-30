<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/login.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap"
	rel="stylesheet">
<link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css'
	rel='stylesheet'>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container-form sign-up ${form == 'registrar' ? 'active' : ''}">
		<div class="welcome-back">
			<div class="message">
				<h2>Bienvenido de nuevo</h2>
				<p>Si ya tienes una cuenta por favor inicia sesion aqui</p>
				<button class="sign-up-btn" id="signUpBtn">Iniciar Sesion</button>
			</div>
		</div>
		<!-- Sección 1: Registrar Usuario -->
		<form class="formulario" method="POST" action="LoginController?ruta=registrarUsuario">
			<h2 class="create-account">Crear una cuenta</h2>
			<i class="fa-regular fa-user fa-5x"></i> 
			<input type="text" name="nombreN" placeholder="Nombre" required> 
			<input type="text" name="apellidoN" placeholder="Apellido" required> 
			<input type="text" name="nombreUsuarioN" placeholder="Nombre Usuario" required> 
			<input type="email" name="email"placeholder="Email" required> 
			<input type="text" name="clave" placeholder="Contraseña" required>
			<button class="button">Registrarse</button>
		</form>
	</div>
	<div class="container-form sign-in ${form == 'iniciar' ? 'active' : ''}">
		<!-- Sección 2: Iniciar Sesion -->
		<form class="formulario" method="POST" action="LoginController?ruta=iniciarSesion">
			<h2 class="create-account">Iniciar Sesión</h2>
			<i class="fa-regular fa-user fa-5x "></i> 
			<input type="text" name="nombreUsuario" placeholder="Username" required> 
			<input type="password" name="clave" placeholder="Contraseña" required> 
			<!-- <input type="submit" class="button" value="Iniciar Sesión"> -->
			<button class=" register-btn button">Iniciar Sesión</button>
		</form>
		<div class="welcome-back">
			<div class="message">
				<h2>Bienvenido de nuevo</h2>
				<p>Si aun no tienes una cuenta por favor registrese aqui</p>
				<button class="sign-in-btn" id="signInBtn">Registrarse</button>
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/jsp/scripts/login.js"></script>
</body>
</html>
