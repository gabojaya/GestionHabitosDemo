document.addEventListener('DOMContentLoaded', function() {
	const par = new URLSearchParams(window.location.search);
	const ruta = par.get('ruta');
	if (ruta === 'iniciarSesion' || ruta === 'registrarUsuario' || ruta === 'modificarUsuario') {
		showPage(1); // Mostrar la primera página al cargar la página
		setUpPerfil();
		setupTabs();


	} else if (ruta === 'listarEjecuciones') {
		showPage(3);
		setupTabs();
		setUpPerfil();
		setUpEjecuciones();
	} else if (ruta === 'listarRecordatorios') {
		showPage(5);
		setupTabs();
		setUpPerfil();
		setUpRecordatorios();
		notifi();

	} else if (ruta === 'listarHabitosUsuario') {
		showPage(4);
		setupTabs();
		setUpPerfil();
		setUpEstadisticas();
		mostrarEstadisticas();

	} else if (ruta === 'solicitarMetas') {
		showPage(2);
		setupTabs();
		setUpPerfil();
		setupMetaScreen();

	} else if (ruta === 'modificarMeta') {
		showPage(2);
		setupTabs();
		setUpPerfil();
		setupMetaScreen();
		screenOverlayHabitos.style.display = 'flex';
	} else if (ruta === 'solicitarModificarUsuario') {
		showPage(1);
		setUpPerfil();
		setupTabs();
		screenOverlayPerfil.style.display = 'flex';

	} else if (ruta === 'solicitarUsuario') {
		showPage(1);
		setUpPerfil();
		setupTabs();

	} else {
		showPage(2);
		setupTabs();
		setUpPerfil();
		setupMetaScreen();

		screenOverlayHabitos.style.display = 'flex';

	}
});

function setupTabs() {
	const tabs = document.querySelectorAll('.tab');
	tabs.forEach(tab => {
		tab.addEventListener('click', function() {
			const pageNumber = parseInt(tab.dataset.page);
			showPage(pageNumber);

			if (pageNumber === 1) {
				fetchUsuario();
			}
			if (pageNumber === 2) {
				fetchMetas();
			}
			if (pageNumber === 3) {
				fetchEjecuciones();
			}
			if (pageNumber === 4) {
				fetchEstadisticas();
			}
			if (pageNumber === 5) {
				fetchNotificaciones();
			}

		});
	});
}

function showPage(pageNumber) {
	const pages = document.querySelectorAll('.page');
	pages.forEach(function(page) {
		page.style.display = 'none';
	});

	const pageToShow = document.getElementById('page' + pageNumber);
	if (pageToShow) {
		pageToShow.style.display = 'flex';
	}
}

function fetchUsuario() {
	window.location.href = `UsuarioController?ruta=solicitarUsuario`;
}
function fetchEstadisticas() {
	window.location.href = `HabitoController?ruta=listarHabitosUsuario`;
}
function fetchNotificaciones() {
	window.location.href = `NotificacionController?ruta=listarRecordatorios`;
}
function fetchEjecuciones() {
	window.location.href = `EjecucionController?ruta=listarEjecuciones`;
}
function fetchMetas() {
	window.location.href = `MetaController?ruta=solicitarMetas`;
}
function eliminarMeta(idMeta, idUsuario) {
	// Mostrar la alerta de confirmación antes de eliminar
	Swal.fire({
		title: "¿Eliminar Meta?",
		text: "¿Estás seguro de que deseas eliminar esta meta? Esta acción no se puede deshacer.",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#3085d6",
		cancelButtonColor: "#d33",
		confirmButtonText: "Sí, eliminar",
		cancelButtonText: "Cancelar"
	}).then((result) => {
		// Si el usuario confirma, proceder con la eliminación
		if (result.isConfirmed) {
			// Hacer la solicitud fetch para eliminar la meta
			fetch(`MetaController?ruta=eliminarMeta&idMeta=${idMeta}&idUsuario=${idUsuario}`, {
				method: 'POST'
			})
				.then(response => response.json())
				.then(data => {
					if (data.status === "success") {
						// Eliminar la fila de la tabla
						const row = document.querySelector(`button[data-id="${idMeta}"]`).closest('tr');
						row.remove();
						// Mostrar alerta con SweetAlert2
						Swal.fire({
							icon: 'success',
							title: 'Meta eliminada',
							text: 'La meta ha sido eliminada correctamente',
							confirmButtonColor: '#3085d6',
							confirmButtonText: 'OK'
						});
					} else {
						Swal.fire({
							icon: 'error',
							title: 'Error',
							text: 'No se pudo eliminar la meta',
							confirmButtonColor: '#d33'
						});
					}
				})
				.catch(error => {
					console.error('Error:', error);
				});
		}
	});
}

function setUpPerfil() {
	const editarUsuarioBtns = document.querySelectorAll('.editar-usuario');
	const screenOverlayPerfil = document.getElementById('screenOverlayPerfil');
	const cerrarBtn = document.getElementById('cerrar-btn');
	const eliminarUsuario = document.querySelectorAll('.eliminarUsuario');

	editarUsuarioBtns.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const usuarioId = btn.getAttribute('data-id');

			// Hacer una solicitud fetch para obtener los datos del usuario
			fetch(`UsuarioController?ruta=obtenerUsuario&idUsuario=${usuarioId}`)
				.then(response => {
					const idUsuario = response.headers.get('idUsuario');
					const nombre = response.headers.get('nombre');
					const apellido = response.headers.get('apellido');
					const nombreUsuario = response.headers.get('nombreUsuario');
					const email = response.headers.get('email');
					const clave = response.headers.get('clave');

					// Llenar el formulario con los datos del usuario
					document.getElementById('idUsuario').value = idUsuario || '';
					document.getElementById('nombreM').value = nombre || '';
					document.getElementById('apellidoM').value = apellido || '';
					document.getElementById('nombreUsuarioM').value = nombreUsuario || '';
					document.getElementById('emailM').value = email || '';
					document.getElementById('claveM').value = clave || '';

					// Mostrar el formulario
					screenOverlayPerfil.style.display = 'flex';
				});
		});
	});

	// Cerrar el formulario
	cerrarBtn.addEventListener('click', function() {
		screenOverlayPerfil.style.display = 'none';
	});

	eliminarUsuario.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const usuarioId = btn.getAttribute('data-id');
			Swal.fire({
				title: "Eliminar Cuenta",
				text: "Los datos del usuario serán eliminados.",
				icon: "warning",
				showCancelButton: true,
				confirmButtonColor: "#3085d6",
				cancelButtonColor: "#d33",
				confirmButtonText: "Sí, eliminar",
				cancelButtonText: "Cancelar"
			}).then((result) => {
				if (result.isConfirmed) {
					// Redirigir a la URL de eliminación
					window.location.href = `UsuarioController?ruta=eliminarUsuario&idUsuario=${usuarioId}`;
				}
			});
		});
	});


	document.getElementById("formEditarUsuario").addEventListener("submit", function(event) {
		event.preventDefault(); // Evita el envío automático del formulario

		Swal.fire({
			title: "¿Guardar cambios?",
			text: "Los datos del usuario serán actualizados.",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#3085d6",
			cancelButtonColor: "#d33",
			confirmButtonText: "Sí, guardar",
			cancelButtonText: "Cancelar"
		}).then((result) => {
			if (result.isConfirmed) {
				// Enviar el formulario manualmente si el usuario confirma
				event.target.submit();
			}
		});
	});

}


function setupMetaScreen() {

	const addMetaBtn = document.getElementById('add-meta-btn');
	const editarMetaBtns = document.querySelectorAll('.editar-meta');
	const screenOverlayModificarMeta = document.getElementById('screenOverlayModificarMeta');
	const screenOverlayAgregarMeta = document.getElementById('screenOverlayAgregarMeta');
	const cerrarModificarBtn = document.getElementById('cerrar-btn-modificar');
	const continuaModificarrBtn = document.getElementById('continuar-btn-modificar');
	const cerrarBtnAgregar = document.getElementById('cerrar-btn-agregar');
	//const continuarBtnAgregar = document.getElementById('continuar-btn-agregar');
	const screenOverlayHabitos = document.getElementById('screenOverlayHabitos');



	addMetaBtn.addEventListener('click', function() {
		screenOverlayAgregarMeta.style.display = 'flex';
	});



	cerrarModificarBtn.addEventListener('click', function() {
		screenOverlayModificarMeta.style.display = 'none';
	});

	cerrarBtnAgregar.addEventListener('click', function() {
		screenOverlayAgregarMeta.style.display = 'none';
	});



	editarMetaBtns.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const metaId = btn.getAttribute('data-id');

			// Realizar la solicitud al servidor para obtener los datos de la meta
			fetch('MetaController?ruta=obtenerMeta&idmeta=' + metaId)
				.then(response => {
					// Obtener los encabezados de la respuesta
					const idMeta = response.headers.get('idmeta');
					const nombreMeta = response.headers.get('nombre');
					const descripcionMeta = response.headers.get('descripcion');
					const fechaInicioMeta = response.headers.get('fechaInicio');
					const fechaFinMeta = response.headers.get('fechaFin');

					// Llenar el formulario con los datos de la meta
					document.getElementById('idMeta').value = idMeta || '';
					document.getElementById('nombre-meta').value = nombreMeta || '';
					document.getElementById('descripcion-meta').value = descripcionMeta || '';
					document.getElementById('fecha-inicio').value = fechaInicioMeta || '';
					document.getElementById('fecha-fin').value = fechaFinMeta || '';

					// Mostrar el formulario de edición de meta
					screenOverlayModificarMeta.style.display = 'flex';
				})
				.catch(error => {
					console.error('Error al obtener los datos de la meta:', error);
				});
		});
	});


	document.getElementById("formModificarMeta").addEventListener("submit", function(event) {
		event.preventDefault(); // Evita el envío automático del formulario

		Swal.fire({
			title: "¿Guardar cambios?",
			text: "Los datos de la meta serán actualizados.",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#3085d6",
			cancelButtonColor: "#d33",
			confirmButtonText: "Sí, guardar",
			cancelButtonText: "Cancelar"
		}).then((result) => {
			if (result.isConfirmed) {
				// Enviar el formulario manualmente si el usuario confirma
				event.target.submit();
			}
		});
	});

	document.getElementById("formAgregarMeta").addEventListener("submit", function(event) {
		event.preventDefault(); // Evita el envío automático del formulario

		Swal.fire({
			title: "¿Agregar esta meta?",
			text: "Se añadirá una nueva meta con los datos proporcionados.",
			icon: "question",
			showCancelButton: true,
			confirmButtonColor: "#3085d6",
			cancelButtonColor: "#d33",
			confirmButtonText: "Sí, agregar",
			cancelButtonText: "Cancelar"
		}).then((result) => {
			if (result.isConfirmed) {
				// Enviar el formulario manualmente si el usuario confirma
				event.target.submit();
			}
		});
	});




	//	continuarBtnAgregar.addEventListener('click', function() {
	//		if (selectedMetaId) {
	//					// Redirige a la URL con el idMeta seleccionado
	//					window.location.href = `HabitoController?ruta=listar&idmeta=${selectedMetaId}`;
	//				} else {
	//					alert('Seleccione una meta antes de continuar.');
	//				}
	//
	//	});


	/* Seccion 2: Registrar Habito*/

	const addHabitoBtn = document.getElementById('agregar-habito-btn');
	const editarHabitoBtn = document.querySelectorAll('.editar-habito');
	const eliminarHabitoBtn = document.querySelectorAll('.eliminar-habito');
	const screenOverlayRegistroHabitos = document.getElementById('screenOverlayRegistroHabitos');
	const guardarRegistroHabitoBtn = document.getElementById('guardar-habito-btn');
	const cerrarHabitoBtn = document.getElementById('cerrar-habito-btn');
	const volverAMetasBtn = document.getElementById('volver-a-metas-btn');
	let editar = false;

	const asginarHorariosBtn = document.querySelectorAll('.agregar-horarios-habito');
	const screenOverlayAsignarHorarios = document.getElementById('screenOverlayAsignarHorarios');
	const volverAHabitosBtn = document.getElementById('volver-a-habitos-btn');


	addHabitoBtn.addEventListener('click', function() {

		const urlParams = new URLSearchParams(window.location.search);
		const metaid = urlParams.get("idmeta"); // Obtén el idMeta de la URL
		document.getElementById('idmeta').value = metaid; // Asigna el valor al input oculto


		document.getElementById('nombre-habito').value = '';
		document.getElementById('categoria-habito').value = '';
		document.getElementById('tipo-medicion').value = '';
		document.getElementById('frecuencia-habito').value = '';
		document.getElementById('cantidad-habito').value = '';
		document.getElementById('tiempo-habito').value = '';
		editar = false;
		screenOverlayRegistroHabitos.style.display = 'flex';
	});

	guardarRegistroHabitoBtn.addEventListener('click', function() {
		var id = new URLSearchParams(window.location.search);
		let metaid = id.get("idmeta");

		// Obtiene el formulario
		const form = document.getElementById('habito-form');

		// Verifica si el formulario es válido
		if (form.checkValidity()) {
			// Si es válido, se asigna la acción del formulario y se envía
			if (editar) {
				document.getElementById('habito-form').action = `HabitoController?ruta=ingresarDatosModificacionHabito&idmeta=${metaid}`;
				document.getElementById('habito-form').submit();
			} else {
				document.getElementById('habito-form').action = `HabitoController?ruta=ingresarDatosHabito&idmeta=${metaid}`;
				document.getElementById('habito-form').submit();
			}
			// Oculta la pantalla de overlay después de enviar el formulario
			screenOverlayRegistroHabitos.style.display = 'none';
		} else {
			// Si el formulario no es válido, muestra un mensaje de alerta
			alert("Por favor, completa todos los campos requeridos.");
		}
	});


	cerrarHabitoBtn.addEventListener('click', function() {
		screenOverlayRegistroHabitos.style.display = 'none';
	});

	volverAMetasBtn.addEventListener('click', function() {
		screenOverlayHabitos.style.display = 'none';
		// Obtén el idUsuario del campo oculto
		const idUsuario = document.getElementById('idUsuario').value;

		fetchMetas(idUsuario);
	});


	editarHabitoBtn.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const hid = btn.getAttribute('hab-id');
			fetch('HabitoController?ruta=obtenerHabito&idhabito=' + hid)
				.then(response => {
					const hid = response.headers.get('idhabito');
					const hnom = response.headers.get('nombre');
					const hcat = response.headers.get('categoria');
					const hf = response.headers.get('frecuencia');
					const hmed = response.headers.get('tipoMedicion');
					const hcan = response.headers.get('cantidadTotal');
					const htime = response.headers.get('tiempoTotal');
					document.getElementById('idhab').value = hid;
					document.getElementById('nombre-habito').value = hnom || '';
					document.getElementById('categoria-habito').value = hcat || '';
					document.getElementById('tipo-medicion').value = hmed || '';
					document.getElementById('frecuencia-habito').value = hf || '';
					document.getElementById('cantidad-habito').value = hcan || '';
					document.getElementById('tiempo-habito').value = htime || '';
					if (hmed == "cantidad") {
						console.log(hmed);
						document.getElementById("divcan").style.display = 'flex';
						document.getElementById("divtime").style.display = 'none';
					} else {
						document.getElementById("divtime").style.display = 'flex';
						document.getElementById("divcan").style.display = 'none';
					}
				});
			//.catch(error => console.error('Error:', error));
			editar = true;
			console.log(document.getElementById("tipo-medicion").value);
			if (document.getElementById("tipo-medicion").value == "cantidad") {
				document.getElementById("divcan").style.display = 'flex';
				document.getElementById("divtime").style.display = 'none';
			} else if (document.getElementById("tipo-medicion").value == "tiempo") {
				document.getElementById("divtime").style.display = 'flex';
				document.getElementById("divcan").style.display = 'none';
			}
			screenOverlayRegistroHabitos.style.display = 'flex';
		});
	});

	eliminarHabitoBtn.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const hid = btn.getAttribute('hab-id');
			const mid = btn.getAttribute('meta-id');

			// Mostrar la alerta de confirmación antes de eliminar
			Swal.fire({
				title: "¿Eliminar Hábito?",
				text: "¿Estás seguro de que deseas eliminar este hábito? Esta acción no se puede deshacer.",
				icon: "warning",
				showCancelButton: true,
				confirmButtonColor: "#3085d6",
				cancelButtonColor: "#d33",
				confirmButtonText: "Sí, eliminar",
				cancelButtonText: "Cancelar"
			}).then((result) => {
				// Si el usuario confirma, proceder con la eliminación
				if (result.isConfirmed) {
					// Hacer la solicitud fetch para eliminar el hábito
					window.location.href = `HabitoController?ruta=eliminarHabito&idmeta=${mid}&idhab=${hid}`;
				}
			});
		});
	});


	asginarHorariosBtn.forEach(function(btn) {
		btn.addEventListener('click', function() {
			// Mostrar el div al hacer clic en el botón
			screenOverlayAsignarHorarios.style.display = 'flex';

			const idHabito = this.getAttribute("hab-id");
			const frecuencia = parseInt(this.getAttribute("hab-frec"));

			document.getElementById("idHabito").value = idHabito;

			// Generar campos dinámicos
			const horariosContainer = document.getElementById("horariosContainer");
			horariosContainer.innerHTML = ""; // Limpiar campos previos
			for (let i = 1; i <= frecuencia; i++) {
				const horarioDiv = document.createElement("div");
				horarioDiv.className = "horario-item";
				horarioDiv.innerHTML = `
			                    <label for="horaHorario${i}">Hora ${i}:</label>
			                    <input type="time" name="horaHorario${i}" id="horaHorario${i}" required>
			                `;
				horariosContainer.appendChild(horarioDiv);
			}


		});
	});

	volverAHabitosBtn.addEventListener('click', function() {
		screenOverlayAsignarHorarios.style.display = 'none';
	});


}

function setUpEjecuciones() {

	/* Seccion 3: Registrar Ejecucion*/
	const registrarEjecucionBtn = document.querySelectorAll('.registrar-ejecucion');
	const guardarEjecucionBtn = document.getElementById('guardar-ejecucion-btn');
	const cerrarEjecucionBtn = document.getElementById('cerrar-ejecucion-btn');
	const screenOverlayEjecucion = document.getElementById('screenOverlayEjecucion');



	registrarEjecucionBtn.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const dataEjecId = btn.getAttribute('data-ejec-id');
			const dataEjecHabitoId = btn.getAttribute('data-ejec-habitoID');
			const dataEjecHabtioName = btn.getAttribute('data-ejec-habitoName');
			const dataEjecHabtioTipo = btn.getAttribute('data-ejec-habitoTipo');
			const dataEjecHabtioCantidad = btn.getAttribute('data-ejec-habitoCantidad');
			const dataEjecHabtioTiempo = btn.getAttribute('data-ejec-habitoTiempo');

			// Llenar el formulario con los datos de la meta seleccionada
			document.getElementById('idEjecucion').value = dataEjecId;
			document.getElementById('idEjecHabito').value = dataEjecHabitoId;
			document.getElementById('nameEjecHabito').value = dataEjecHabtioName;
			document.getElementById('tipoEjecHabito').value = dataEjecHabtioTipo;
			document.getElementById('cantidadEjecHabito').value = dataEjecHabtioCantidad;
			document.getElementById('tiempoEjecHabito').value = dataEjecHabtioTiempo;

			// Limpiar los campos de cantidad y tiempo antes de llenar con nuevos valores
			document.getElementById('cantidadActual').value = '';
			document.getElementById('tiempoTranscurrido').value = '';
			document.getElementById('tiempoTranscurridoInput').value = '';

			// Mostrar los datos en pantalla
			document.getElementById('nombreHabitoTexto').textContent = dataEjecHabtioName;
			document.getElementById('fechaActualTexto').textContent = new Date().toLocaleDateString(); // Fecha actual

			// Dependiendo del tipo, mostrar la sección correcta
			if (dataEjecHabtioTipo === "cantidad") {
				document.getElementById('cantidadSection').style.display = 'block';
				document.getElementById('tiempoSection').style.display = 'none';
				document.getElementById('cantidadTotal').textContent = dataEjecHabtioCantidad;

				// Establecer el máximo de cantidad
				// Establecer el máximo de cantidad
				const cantidadTotal = parseInt(dataEjecHabtioCantidad);
				const cantidadInput = document.getElementById('cantidadActual');
				cantidadInput.max = cantidadTotal;

				// Validar si el valor ingresado es mayor al máximo
				// Bloquear la entrada de números mayores que el máximo
				cantidadInput.addEventListener('input', function() {
					const cantidadIngresada = parseInt(cantidadInput.value);
					if (cantidadIngresada > cantidadTotal) {
						cantidadInput.value = cantidadTotal; // Si se excede, ajustamos al máximo
					}
				});

				// Ocultar botones de tiempo
				document.getElementById('iniciar-temporizador').style.display = 'none';
				document.getElementById('pausar-temporizador').style.display = 'none';
				document.getElementById('reiniciar-temporizador').style.display = 'none';

			} else if (dataEjecHabtioTipo === "tiempo") {
				document.getElementById('tiempoSection').style.display = 'block';
				document.getElementById('cantidadSection').style.display = 'none';
				document.getElementById('tiempoTotal').textContent = dataEjecHabtioTiempo;

				document.getElementById('iniciar-temporizador').style.display = 'inline-block';
				document.getElementById('pausar-temporizador').style.display = 'inline-block';
				document.getElementById('reiniciar-temporizador').style.display = 'inline-block';
			}

			screenOverlayEjecucion.style.display = 'flex';

		});
	});

	//	document.getElementById('formRegistrarEjecucion').addEventListener('submit', function(e) {
	//		const cantidadInput = document.getElementById('cantidadActual');
	//		const cantidadTotal = parseInt(document.getElementById('cantidadEjecHabito').value);
	//
	//		// Verificamos antes de enviar el formulario si el valor excede el máximo
	//		if (parseInt(cantidadInput.value) > cantidadTotal) {
	//			e.preventDefault(); // Evitar que el formulario se envíe si el valor es mayor
	//			alert(`El valor de la cantidad no puede ser mayor a ${cantidadTotal}.`);
	//		}
	//	});

	cerrarEjecucionBtn.addEventListener('click', function() {
		screenOverlayEjecucion.style.display = 'none';
	});


	// Obtener referencias a los elementos del DOM
	const tiempoSection = document.getElementById('tiempoSection');
	const tiempoTranscurrido = document.getElementById('tiempoTranscurrido');
	const tiempoTotal = document.getElementById('tiempoTotal');
	const iniciarBtn = document.getElementById('iniciar-temporizador');
	const pausarBtn = document.getElementById('pausar-temporizador');
	const reiniciarBtn = document.getElementById('reiniciar-temporizador');


	let intervalo = null;

	let tiempoEnSegundos = 0;
	let tiempoTotalEnSegundos = 0;


	// Función para iniciar el temporizador
	iniciarBtn.addEventListener('click', () => {
		if (!intervalo) {
			// Obtener el tiempo total desde el input oculto
			const tiempoTotalTexto = document.getElementById('tiempoEjecHabito').value;

			// Soporte para formato hh:mm:ss
			const [horasTotales, minutosTotales, segundosTotales] = tiempoTotalTexto.split(':').map(Number);
			tiempoTotalEnSegundos = (horasTotales * 3600) + (minutosTotales * 60) + segundosTotales;

			intervalo = setInterval(() => {
				if (tiempoEnSegundos < tiempoTotalEnSegundos) {
					tiempoEnSegundos++;
					const horas = String(Math.floor(tiempoEnSegundos / 3600)).padStart(2, '0');
					const minutos = String(Math.floor((tiempoEnSegundos % 3600) / 60)).padStart(2, '0');
					const segundos = String(tiempoEnSegundos % 60).padStart(2, '0');
					tiempoTranscurrido.textContent = `${horas}:${minutos}:${segundos}`;

					// Actualizar el campo oculto con el tiempo transcurrido
					document.getElementById('tiempoTranscurridoInput').value = `${horas}:${minutos}:${segundos}`;
				} else {
					// Detener el temporizador automáticamente al alcanzar el límite
					clearInterval(intervalo);
					intervalo = null;
					iniciarBtn.disabled = true;
					pausarBtn.disabled = true;
					alert("Has alcanzado el tiempo total asignado.");
				}
			}, 1000);

			iniciarBtn.disabled = true;
			pausarBtn.disabled = false;
		}
	});


	// Función para pausar el temporizador
	pausarBtn.addEventListener('click', () => {
		clearInterval(intervalo);
		intervalo = null;
		iniciarBtn.disabled = false;
		pausarBtn.disabled = true;
	});

	// Función para reiniciar el temporizador
	reiniciarBtn.addEventListener('click', () => {
		clearInterval(intervalo);
		intervalo = null;
		tiempoEnSegundos = 0;
		tiempoTranscurrido.textContent = '00:00:00';
		iniciarBtn.disabled = false;
		pausarBtn.disabled = true;
	});

	// Función para reiniciar el temporizador al cerrar
	cerrarEjecucionBtn.addEventListener('click', () => {
		clearInterval(intervalo);
		intervalo = null;
		tiempoEnSegundos = 0;
		tiempoTranscurrido.textContent = '00:00:00';
		iniciarBtn.disabled = false;
		pausarBtn.disabled = true;
		tiempoSection.style.display = 'none';
		screenOverlayEjecucion.style.display = 'none';
	});

	guardarEjecucionBtn.addEventListener('click', function() {
		// Detener el temporizador si está corriendo
		if (intervalo) {
			clearInterval(intervalo);
			intervalo = null;
		}
		// Asignar el valor del cronómetro al campo de formulario
		document.getElementById('tiempoTranscurrido').value = tiempoTranscurrido.textContent;

		// Proceder con el resto del código para guardar la ejecución
		screenOverlayEjecucion.style.display = 'none';
	});


	document.getElementById("formRegistrarEjecucion").addEventListener("submit", function(event) {
	        event.preventDefault(); // Evita el envío automático del formulario

	        Swal.fire({
	            title: "¿Registrar Ejecución?",
	            text: "¿Estás seguro de que deseas registrar esta ejecución del hábito?",
	            icon: "warning",
	            showCancelButton: true,
	            confirmButtonColor: "#3085d6",
	            cancelButtonColor: "#d33",
	            confirmButtonText: "Sí, registrar",
	            cancelButtonText: "Cancelar"
	        }).then((result) => {
	            if (result.isConfirmed) {
	                // Enviar el formulario manualmente si el usuario confirma
	                event.target.submit();
	            }
	        });
	    });



}

function setUpEstadisticas() {
	const verButton = document.querySelectorAll('[data-ver]');
	verButton.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const idhabito = btn.getAttribute('data-id');
			window.location.href = `EstadisticaController?ruta=verEstadistica&habito=${idhabito}`;
		});
	});
}
function mostrarEstadisticas() {
	console.log("Entro a mostrar");
	const valores = window.location.search;
	const urlParams = new URLSearchParams(valores);
	const ca = urlParams.get('data-ca');
	console.log(ca);
	const cf = urlParams.get('data-cf');
	console.log(cf);
	const ta = urlParams.get('data-ta');
	console.log(ta);
	const tf = urlParams.get('data-tf');
	console.log(tf);
	console.log(ta.substring(3, 5));
	var tan = (parseFloat(ta.substring(0, 2))) + ((parseFloat(ta.substring(3, 5))) / 60) + ((parseFloat(ta.substring(6, 8))) / 3600);
	var tfn = (parseFloat(tf.substring(0, 2))) + ((parseFloat(tf.substring(3, 5))) / 60);
	console.log(tan);
	console.log(tfn);
	const ctx = document.getElementById('myChart');
	var data;
	if (isNaN(tan)) {
		console.log("entro en el nan");
		data = {
			labels: ['cantidad acumulada', 'cantidad esperada'],
			datasets: [{
				label: 'Estadisticas',
				data: [ca, cf],
				backgroundColor: [
					'rgba(255, 99, 132, 0.2)',
					'rgba(255, 159, 64, 0.2)'
				],
				borderColor: [
					'rgb(255, 99, 132)',
					'rgb(255, 159, 64)'
				],
				borderWidth: 1
			}]
		};
		const config = {
			type: 'bar',
			data: data,
			options: {
				scales: {
					y: {
						beginAtZero: true
					}
				}
			},
		};
		new Chart(ctx, config);
	} else {
		console.log("entro en el no nan");
		data = {
			labels: ['tiempo acumulado', 'tiempo esperado'],
			datasets: [{
				label: 'Estadisticas',
				data: [tan, tfn],
				backgroundColor: [
					'rgba(255, 205, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)'
				],
				borderColor: [
					'rgb(255, 205, 86)',
					'rgb(75, 192, 192)'
				],
				borderWidth: 1
			}]
		};
		const config = {
			type: 'bar',
			data: data,
			options: {
				scales: {
					y: {
						beginAtZero: true
					}
				}
			},
		};
		new Chart(ctx, config);
	}
}

function notifi() {
	var records = document.querySelectorAll('.noti');
	var time = new Date().toLocaleTimeString('es-ES');
	console.log("Se entro en la funcionnde notificaciones");
	// Verificar que el navegador soporta notificaciones
	if (!("Notification" in window)) {

		alert("Tu navegador no soporta notificaciones");

	} else if (Notification.permission === "granted") {
		// Lanzar notificacion si ya esta autorizado el servicio
		records.forEach((item) => {
			;
			if (time.substring(0, 5) == item.dataset.hor.substring(0, 5)) {
				var notification = new Notification(item.dataset.mes);
			}
		});

	} else if (Notification.permission !== "denied") {
		Notification.requestPermission(function(permission) {

			if (Notification.permission === "granted") {
				// Lanzar notificacion si ya esta autorizado el servicio
				records.forEach((item) => {
					if (time.substring(0, 5) == item.dataset.hor.substring(0, 5)) {
						var notification = new Notification(item.dataset.mes);
					}
				});

			}

		});

	}
}
function setUpRecordatorios() {
	document.querySelectorAll('.marcar-leido-btn').forEach(button => {
		button.addEventListener('click', function() {
			const recordatorioId = this.getAttribute('data-id');
			window.location.href = `NotificacionController?ruta=marcarLeido&idRecordatorio=${recordatorioId}`;
		});
	});

}
