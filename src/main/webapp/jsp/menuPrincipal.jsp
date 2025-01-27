<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Menu Principal</title>

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jsp/css/menu.css">
<script
	src="https://cdn.jsdelivr.net/npm/chart.js@4.4.7/dist/chart.umd.min.js"></script>
</head>

<body>
	<div class="container-form">
		<!-- Barra de navegación con los nombres de las páginas -->
		<div class="tabs">
			<span class="tab" data-page="1">Perfil</span> <span class="tab"
				data-page="2">Metas</span> <span class="tab" data-page="3">Ejecución</span>
			<span class="tab" data-page="4">Estadísticas</span> <span class="tab"
				data-page="5">Notificaciones</span> <a
				href="LoginController?ruta=cerrarSesion" class="tab cerrar-sesion">Cerrar
				Sesión</a>
		</div>

		<div class="formulario">
			<!-- Sección 1: Perfil-->
			<div class="page" id="page1">
				<div class="perfil">
					<div>
						<i class="fa-regular fa-user fa-5x icono"></i>
						<h2 class="create-account">Usuario</h2>
					</div>
					<div class="user-info">
						<div class="input-group">
							<input type="hidden" name="idUsuario" id="idUsuario"
								value="${usuario.idUsuario}" placeholder="${usuario.idUsuario} ">
							<label for="nombre">Nombre</label> <input type="text" id="nombre"
								placeholder="${usuario.nombre}">
						</div>
						<div class="input-group">
							<label for="apellido">Apellido</label> <input type="apellido"
								id="apellido" placeholder="${usuario.apellido}">
						</div>
						<div class="input-group">
							<label for="nombreUsuario">Nombre Usuario</label> <input
								type="nombreUsuario" id="nombreUsuario"
								placeholder="${usuario.nombreUsuario}">
						</div>
						<div class="input-group">
							<label for="email">Correo</label> <input type="email" id="email"
								placeholder="${usuario.email}">
						</div>
						<div class="input-group">
							<label for="fecha">Fecha de inicio</label> <input type="text"
								id="fecha" placeholder="${usuario.fechaInicio}">
						</div>
					</div>
				</div>
				<div class="botones-perfil">
					<button class="button-user editar-usuario" 
						data-id="${usuario.idUsuario}" data-nombre="${usuario.nombre}"
						data-apellido="${usuario.apellido}" data-nombreUsuario="${usuario.nombreUsuario}"
						data-email="${usuario.email}" data-clave="${usuario.clave}">Editar
						perfil</button>
					<button class="button-user eliminarUsuario">Eliminar
						perfil</button>
				</div>
			</div>


			<div class="screenOverlay" id="screenOverlayPerfil"
				style="display: none;">
				<form method="POST" action="PerfilController?ruta=modificarUsuario">
					<div class="container-form-2">
						<h3>Editar Usuario</h3>
						<!-- Campo oculto para ID del usuario -->
						<input type="hidden" name="idUsuario" id="idUsuario" />

						<!-- Campo para Nombre -->
						<div class="input-group">
							<label for="nombre">Nombre:</label> <input type="text"
								id="nombreM" name="nombreM" required />
						</div>

						<!-- Campo para Apellido -->
						<div class="input-group">
							<label for="apellido">Apellido:</label> <input type="text"
								id="apellidoM" name="apellidoM" required />
						</div>

						<!-- Campo para Nombre de Usuario -->
						<div class="input-group">
							<label for="nombreUsuario">Nombre Usuario:</label> <input
								type="text" id="nombreUsuarioM" name="nombreUsuarioM" required />
						</div>

						<!-- Campo para Email -->
						<div class="input-group">
							<label for="email">Correo:</label> <input type="email" id="emailM"
								name="emailM" required />
						</div>

						<!-- Campo para Clave -->
						<div class="input-group">
							<label for="clave">Clave:</label> <input type="text"
								id="claveM" name="claveM" required />
						</div>

						<!-- Botones de acción -->
						<div class="botones-meta">
							<button class="button-user" id="continuar-btn" type="submit">Guardar
								Cambios</button>
							<button class="button-user" id="cerrar-btn" type="button">Cancelar</button>
						</div>
					</div>
				</form>
			</div>


			<!-- Sección 2: Metas -->
			<div class="page" id="page2" style="display: none;">
				<h2 class="create-account">Metas Registradas</h2>
				<div class="lista-Metas" style="overflow: auto;">
					<table class="tabla-metas">
						<thead>
							<tr>
								<th>Número</th>
								<th>Nombre</th>
								<th>Descripción</th>
								<th>Fecha Inicio</th>
								<th>Fecha Fin</th>
								<th>Progreso</th>
								<th>Acciones</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="meta" items="${metas}">
								<tr>
									<td>${meta.idMeta}</td>
									<td>${meta.nombre}</td>
									<td>${meta.descripcion}</td>
									<td>${meta.fechaInicio}</td>
									<td>${meta.fechaFin}</td>
									<td>${meta.progreso}%</td>
									<td>
										<button class="editar-meta" data-id="${meta.idMeta}"
											data-nombre="${meta.nombre}"
											data-descripcion="${meta.descripcion}"
											data-fecha-inicio="${meta.fechaInicio}"
											data-fecha-fin="${meta.fechaFin}">Editar meta</button>
										<button class="eliminar-meta" data-id="${meta.idMeta}"
											onclick="eliminarMeta(${meta.idMeta}, ${usuario.idUsuario})">Eliminar
											meta</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="botones-perfil">
						<button class="button-user" id="add-meta-btn">Agregar
							Meta</button>
					</div>
				</div>

			</div>

			<!-- Pantalla Oculta para el Formulario de Meta -->
			<div class="screenOverlay" id="screenOverlayModificarMeta" style="display: none;">
				<form method="POST" action="MetaController?ruta=modificarMeta">
					<div class="container-form-2">
						<h3>Registrar Meta</h3>
						<input type="hidden" name="idMeta" id="idMeta">
						<div class="input-group">
							<label for="nombre-meta">Nombre:</label> <input type="text"
								id="nombre-meta" name="nombre-meta" required />
						</div>
						<div class="input-group">
							<label for="descripcion-meta">Descripción:</label> <input
								type="text" id="descripcion-meta" name="descripcion-meta"
								required />
						</div>
						<div class="input-group">
							<label for="fecha-inicio">Fecha de Inicio:</label> <input
								type="date" id="fecha-inicio" name="fecha-inicio" required />
						</div>
						<div class="input-group">
							<label for="fecha-fin">Fecha de Fin:</label> <input type="date"
								id="fecha-fin" name="fecha-fin" required />
						</div>
						<div class="botones-meta">
							<button class="button-user" id="continuar-btn-modificar" type="submit">Continuar</button>
							<button class="button-user" id="cerrar-btn-modificar" type="button">Cancelar</button>
						</div>
					</div>
				</form>
			</div>

			<!-- Pantalla Oculta para el Formulario de Agregar Meta -->
			<div class="screenOverlay" id="screenOverlayAgregarMeta"
				style="display: none;">
				<form method="POST"
					action="MetaController?ruta=agregarMeta&idUsuario=${usuario.idUsuario}">
					<div class="container-form-2">
						<h3>Registrar Meta</h3>
						<div class="input-group">
							<label for="nombre-meta">Nombre:</label> <input type="text"
								name="nombre-meta" id="nombre-meta" required />
						</div>
						<div class="input-group">
							<label for="descripcion-meta">Descripción:</label> <input
								type="text" name="descripcion-meta" id="descripcion-meta"
								required />
						</div>
						<div class="input-group">
							<label for="fecha-inicio">Fecha de Inicio:</label> <input
								type="date" name="fecha-inicio" id="fecha-inicio" required />
						</div>
						<div class="input-group">
							<label for="fecha-fin">Fecha de Fin:</label> <input type="date"
								name="fecha-fin" id="fecha-fin" required />
						</div>
						<div class="botones-meta">
							<button class="button-user" id="continuar-btn-agregar"
								type="submit">Continuar</button>
							<button class="button-user" id="cerrar-btn-agregar" type="button">Cancelar</button>
						</div>
					</div>
				</form>
			</div>

			<!-- Pantalla para Hábitos -->
			<div class="screenOverlay" id="screenOverlayHabitos"
				style="display: none;">
				<div class="container-form-2">
					<h3>Registrar Hábitos</h3>
					<div class="lista-habitos" style="overflow: auto;">
						<!--<input type="hidden" name="idmeta" id="idmeta" value="${idMeta}"> -->
						<table class="tabla-habitos">
							<thead>
								<tr>
									<th>Número</th>
									<th>Nombre del Hábito</th>
									<th>Categoría</th>
									<th>Frecuencia</th>
									<!-- <th>Estado</th> -->
									<th>Acciones</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${habito}" var="habitos" varStatus="status">
									<tr>
										<!-- <td>${habitos.idHabito}</td> -->
										<td>${status.index + 1}</td>
										<td>${habitos.nombre}</td>
										<td>${habitos.categoria}</td>
										<td>${habitos.frecuencia}</td>
										<!-- <td>${habitos.estado}</td> -->
										<td>
											<button class="editar-habito" hab-id="${habitos.idHabito}"
												hab-nom="${habitos.nombre}" hab-cat="${habitos.categoria}"
												hab-f="${habitos.frecuencia}" hab-es="${habitos.estado}"
												hab-med="${habitos.tipoMedicion}"
												meta-id="${habitos.metaAsociada}"
												hab-can="${habitos.cantidadTotal}"
												hab-time="${habitos.tiempoTotal}">Editar</button>
											<button class="eliminar-habito" hab-id="${habitos.idHabito}"
												meta-id="${habitos.metaAsociada}">Eliminar</button>
											<button class="agregar-horarios-habito"
												hab-id="${habitos.idHabito}"
												meta-id="${habitos.metaAsociada}"
												hab-frec="${habitos.frecuencia}">Asginar horarios</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="botones-habitos">
						<button class="button-user" id="agregar-habito-btn">Agregar
							Habito</button>
						<button class="button-user" id="volver-a-metas-btn">Volver
							a la lista de metas</button>
					</div>
				</div>
			</div>

			<!-- Pantalla para Asignar Horarios -->
			<div class="screenOverlay" id="screenOverlayAsignarHorarios"
				style="display: none;">
				<div class="container-form-2">
					<h3>Asignar Horarios</h3>
					<form id="formAsignarHorarios" method="POST"
						action="NotificacionController?ruta=asignarRecordatorio">
						<input type="hidden" name="idHabito" id="idHabito">
						<div id="horariosContainer">
							<!-- Los campos de fecha y hora serán generados dinámicamente aquí -->
						</div>
						<button type="submit" id="btnAsignarHorarios">Asignar</button>
						<button type="button" id="volver-a-habitos-btn">Volver a
							la lista de habitos</button>
					</form>
				</div>
			</div>


			<!-- Pantalla Oculta para el Formulario de Habitos -->
			<div class="screenOverlay" id="screenOverlayRegistroHabitos"
				style="display: none;">
				<div class="container-form-2">

					<form id="habito-form" method="POST"
						action="HabitoController?ruta=ingresarDatosHabito">
						<h3>Registrar Habito</h3>
						<input type="hidden" name="idmeta" id="idmeta" value="${idMeta}">
						<input type="hidden" name="idhab" id="idhab">
						<div class="input-group">
							<label for="nombre-habito">Nombre:</label> <input
								onclick="pruebaf()" type="text" id="nombre-habito" name="nombre"
								placeholder="Nombre del hábito" required />
						</div>

						<div class="input-group">
							<label for="categoria-habito">Categoría:</label> <select
								id="categoria-habito" name="categoria" required>
								<option value="salud">Salud</option>
								<option value="bienestar">Bienestar</option>
								<option value="productividad">Productividad</option>
								<option value="otro">Otro</option>
							</select>
						</div>

						<div class="input-group">
							<label for="tipo-medicion">Tipo de Medición:</label> <select
								id="tipo-medicion" name="tipoMedicion" onchange="showMed()"
								required>
								<option value="cantidad">Cantidad</option>
								<option value="tiempo">Tiempo</option>
							</select>
						</div>

						<div class="input-group">
							<label for="frecuencia-habito">Frecuencia:</label> <input
								type="text" id="frecuencia-habito" name="frecuencia" required>
						</div>
						<div class="input-group" id="divcan" style="display: none;">
							<label for="cantidad-habito">Cantidad Total:</label> <input
								type="text" id="cantidad-habito" name="cantidadTotal">
						</div>
						<div class="input-group" id="divtime" style="display: none;">
							<label for="tiempo-habito">Tiempo Total:</label> <input
								type="time" id="tiempo-habito" name="tiempoTotal">
						</div>
						<!--  
						<div class="input-group">
							<label for="horario-habito">Horario:</label> <input type="time"
								id="horario-habito" name="horario" required>
						</div>
						-->



						<div onmouseover="pruebaf()" class="botones-meta2">
							<button type="submit" class="button-user" id="guardar-habito-btn">Guardar
								Habito</button>
							<button type="button" class="button-user" id="cerrar-habito-btn">Cancelar</button>
						</div>
					</form>


				</div>
			</div>

			<!-- Sección 3: Registrar Ejecución-->
			<div class="page" id="page3" style="display: none;">

				<h2 class="create-account">Registrar Ejecución</h2>
				<div class="lista-Metas" style="overflow: auto;">
					<table class="tabla-habitos">
						<thead>
							<tr>
								<th>Número</th>
								<th>Nombre del Hábito</th>
								<th>Categoría</th>
								<th>Fecha</th>
								<th>Hora</th>
								<th>Estado</th>
								<th>Acciones</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ejecuciones}" var="ejecucion">
								<tr>
									<td>${ejecucion.idEjecucion}</td>
									<td>${ejecucion.habito.nombre}</td>
									<td>${ejecucion.habito.categoria}</td>
									<td>${ejecucion.fecha}</td>
									<td>${ejecucion.hora}</td>
									<td>${ejecucion.estado}</td>
									<td>
										<button class="registrar-ejecucion" id="registrar-ejecucion"
											data-ejec-id="${ejecucion.idEjecucion}"
											data-ejec-habitoID="${ejecucion.habito.idHabito}"
											data-ejec-habitoName="${ejecucion.habito.nombre}"
											data-ejec-habitoTipo="${ejecucion.habito.tipoMedicion}"
											data-ejec-habitoCantidad="${ejecucion.habito.cantidadTotal}"
											data-ejec-habitoTiempo="${ejecucion.habito.tiempoTotal}">Registrar
											Ejecucion</button>
									</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
		</div>

		<!-- Pantalla Oculta Registrar Ejecución -->
		<!--
        <div class="screenOverlay" id="screenOverlayEjecucion" style="display:none;">
            <div class="container-form-2">
                <h3>Registrar Ejecución del Hábito</h3>
				
				<input type="hidden" name="idEjecucion" id="idEjecucion" >
				<input type="hidden" name="idEjecHabito" id="idEjecHabito">
				<input type="hidden" name="nameEjecHabito" id="nameEjecHabito" >
				<input type="hidden" name="tipoEjecHabito" id="tipoEjecHabito" >
				<input type="hidden" name="cantidadEjecHabito" id="cantidadEjecHabito" >
				<input type="hidden" name="tiempoEjecHabito" id="tiempoEjecHabito" >
                <p><strong>Nombre del Hábito:</strong> <span id="nombreHabitoTexto"></span></p>

                <p><strong>Fecha Actual:</strong> <span id="fechaActualTexto">--/--/----</span></p>

                <p><strong>Tiempo Transcurrido:</strong> <span id="tiempoTranscurrido">00:00</span></p>

                <div class="botones-control">
                    <button class="button-user" id="iniciar-temporizador">Iniciar</button>
                    <button class="button-user" id="pausar-temporizador" disabled>Pausar</button>
                </div>

                <div class="botones-meta">
                    <button class="button-user" id="guardar-ejecucion-btn">Guardar Hábito</button>
                    <button class="button-user" id="cerrar-ejecucion-btn">Cancelar</button>
                </div>
            </div>
        </div>  -->

		<div class="screenOverlay" id="screenOverlayEjecucion"
			style="display: none;">
			<div class="container-form-2">
				<h3>Registrar Ejecución del Hábito</h3>

				<form id="formRegistrarEjecucion" method="post"
					action="EjecucionController?ruta=registrarEjecucion">
					<input type="hidden" name="idEjecucion" id="idEjecucion"> <input
						type="hidden" name="idEjecHabito" id="idEjecHabito"> <input
						type="hidden" name="nameEjecHabito" id="nameEjecHabito"> <input
						type="hidden" name="tipoEjecHabito" id="tipoEjecHabito"> <input
						type="hidden" name="cantidadEjecHabito" id="cantidadEjecHabito">
					<input type="hidden" name="tiempoEjecHabito" id="tiempoEjecHabito">

					<p>
						<strong>Nombre del Hábito:</strong> <span id="nombreHabitoTexto"></span>
					</p>
					<p>
						<strong>Fecha Actual:</strong> <span id="fechaActualTexto">--/--/----</span>
					</p>

					<!-- Sección para cantidad -->
					<div id="cantidadSection" style="display: none;">
						<p>
							<strong>Cantidad Realizada:</strong> <input type="number"
								id="cantidadActual" name="cantidadActual" min="0"
								placeholder="Cantidad Realizada" />
						</p>
						<p>
							<strong>Cantidad Total:</strong> <span id="cantidadTotal"></span>
						</p>
					</div>

					<!-- Sección para tiempo -->
					<div id="tiempoSection" style="display: none;">
						<p>
							<strong>Tiempo Realizado:</strong> <span id="tiempoTranscurrido">00:00:00</span>
							<input type="hidden" id="tiempoTranscurridoInput"
								name="tiempoTranscurrido" value="00:00:00">

						</p>
						<p>
							<strong>Tiempo Total:</strong> <span id="tiempoTotal"></span>
						</p>
					</div>

					<div class="botones-control">
						<button type="button" class="button-user"
							id="iniciar-temporizador">Iniciar</button>
						<button type="button" class="button-user" id="pausar-temporizador"
							disabled>Pausar</button>
						<button type="button" class="button-user"
							id="reiniciar-temporizador">Reiniciar</button>
					</div>

					<div class="botones-meta">
						<button type="submit" class="button-user"
							id="guardar-ejecucion-btn">Guardar Ejecución</button>
						<button type="button" class="button-user"
							id="cerrar-ejecucion-btn">Cancelar</button>
					</div>
				</form>
			</div>
		</div>


		<!-- Sección 4: Estadisticas-->
		<div class="page" id="page4" style="display: none;">
			<h2 class="create-account">Estadísticas</h2>
			<div class="lista-Metas" style="display: flex;">
				<div class="Metas" style="width: 50%;">
					<table class="tabla-metas">

						<thead>
							<tr>
								<th>Nombre</th>
								<th>Categoría</th>
								<th>Frecuencia</th>
								<th>Estado</th>
								<th>Acciones</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="habito" items="${habitos}">
								<tr>
									<td>${habito.nombre}</td>
									<td>${habito.categoria}</td>
									<td>${habito.frecuencia}</td>
									<td>${habito.estado ? 'Activo' : 'Inactivo'}</td>
									<td>
										<button data-ver="verbutton" class="editar-habito"
											data-id="${habito.idHabito}">Ver estadistica</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<canvas id="myChart" style="width: 50%;"></canvas>
			</div>
		</div>


		<!-- Sección 5: Notificaciones-->
		<!--
		<div class="page" id="page5" style="display: none;">
			<h2 class="create-account">Notificaciones</h2>
			<div class="lista-Metas">
				<table class="tabla-metas">
					<thead>
						<tr>
							<th>Notificacion</th>
							<th>Marcar como leido</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="color: black;">Hora de correr</td>
							<td>
								<button style="font-size: 24px">
									Check<i class="fa fa-calendar-check-o"></i>
								</button>
							</td>
						</tr>
						<tr>
							<td style="color: black;">Hora de comer</td>
							<td>
								<button style="font-size: 24px">
									Check<i class="fa fa-calendar-check-o"></i>
								</button>
							</td>
						</tr>
						<tr>
							<td style="color: black;">Hora de ir al GYM</td>
							<td>
								<button style="font-size: 24px">
									Check<i class="fa fa-calendar-check-o"></i>
								</button>
							</td>
						</tr>
						<tr>
							<td style="color: black;">Hora de dormir</td>
							<td>
								<button style="font-size: 24px">
									Check<i class="fa fa-calendar-check-o"></i>
								</button>
							</td>
						</tr>
						<tr>
							<td style="color: black;">Hora de tomar agua</td>
							<td>
								<button style="font-size: 24px">
									Check<i class="fa fa-calendar-check-o"></i>
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div> -->


		<!-- Sección 5: Notificaciones-->
		<div class="page" id="page5" style="display: none;">
			<h2 class="create-account">Notificaciones</h2>
			<div class="lista-Metas" style="overflow: auto;">
				<table class="tabla-metas">
					<thead>
						<tr>
							<th>Notificación</th>
							<th>Marcar como leído</th>
						</tr>
					</thead>
					<tbody>
						<!-- Iterar sobre los recordatorios -->
						<c:forEach var="recordatorio" items="${recordatorios}">
							<tr>
								<td data-idre="${recordatorio.idRecordatorio}"
									data-mes="${recordatorio.mensaje}"
									data-hor="${recordatorio.hora}" class="noti"
									style="color: black;">${recordatorio.mensaje}</td>
								<td>
									<button style="font-size: 24px; cursor: pointer;"
										class="marcar-leido-btn"
										data-id="${recordatorio.idRecordatorio}">
										Check<i class="fa fa-calendar-check-o"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>


		<script src="${pageContext.request.contextPath}/jsp/scripts/menu.js"></script>
		<script
			src="https://cdn.jsdelivr.net/npm/chart.js@4.4.7/dist/chart.umd.min.js"></script>
		<script>
		function pruebaf(){
        	//document.getElementById("idmeta").value= window.location.search.get(idmeta);
        	 var id = new URLSearchParams(window.location.search);
        	 console.log(id.get("idmeta"));
        	 document.getElementById("idmeta").value=id.get("idmeta");
        }
		function showMed(){
        	if(document.getElementById("tipo-medicion").value=="cantidad"){
        		document.getElementById("divcan").style.display = 'flex';
        		document.getElementById("divtime").style.display = 'none';
        	}
        	if(document.getElementById("tipo-medicion").value=="tiempo"){
        		document.getElementById("divtime").style.display = 'flex';
        		document.getElementById("divcan").style.display = 'none';
        	}
        	
        }
		
		</script>
</body>

</html>