document.addEventListener('DOMContentLoaded', function() {
	const par = new URLSearchParams(window.location.search);
	const ruta = par.get('ruta');
	if (ruta === 'iniciarSesion' || ruta === 'registrarUsuario') {
		showPage(1); // Mostrar la primera página al cargar la página
		setupTabs();


	} else if (ruta === 'listarEjecuciones') {
		showPage(3);
		setupTabs();
		setUpEjecuciones();
	} else if (ruta === 'listarRecordatorios') {
		showPage(5);
		setupTabs();
		notifi();

	} else {
		showPage(2);
		setupTabs();
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

			// Verifica si la pestaña seleccionada es la de Metas (página 2)
			if (pageNumber === 2) {
				fetchMetas();
			}
			if (pageNumber === 3) {
				fetchEjecuciones();
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

function fetchNotificaciones() {
	window.location.href = `NotificacionController?ruta=listarRecordatorios`;
}
function fetchEjecuciones() {
	window.location.href = `EjecucionController?ruta=listarEjecuciones`;
}
function fetchMetas() {
	const idUsuario = document.getElementById('idUsuario').value;

	// Realizar una petición al servidor
	fetch('MetaController?ruta=solicitarMetas&idUsuario=' + idUsuario)
		.then(response => {
			if (!response.ok) {
				throw new Error('Error al obtener las metas');
			}
			return response.json(); // Parsear la respuesta como JSON
		})
		.then(metas => {
			// Obtener la referencia a la tabla en el HTML
			const tbody = document.querySelector('#page2 .tabla-metas tbody');
			tbody.innerHTML = ''; // Limpiar cualquier contenido previo

			// Recorrer las metas y agregar las filas a la tabla
			metas.forEach(meta => {
				const row = document.createElement('tr');
				row.innerHTML = `
                    <td>${meta.idMeta}</td>
                    <td>${meta.nombre}</td>
                    <td>${meta.descripcion}</td>
                    <td>${meta.fechaInicio}</td>
                    <td>${meta.fechaFin}</td>
                    <td>${meta.progreso}%</td>
                    <td>
                        <button class="editar-meta" data-id="${meta.idMeta}" data-nombre="${meta.nombre}" data-descripcion="${meta.descripcion}" data-fecha-inicio="${meta.fechaInicio}" data-fecha-fin="${meta.fechaFin}">Editar meta</button>
                        <button class="eliminar-meta" data-id="${meta.idMeta}" onclick="eliminarMeta(${meta.idMeta}, ${idUsuario})">Eliminar meta</button>
                    </td>
                `;
				tbody.appendChild(row);
			});
			setupMetaScreen();
		})
		.catch(error => {
			console.error('Error:', error);
		});
}

function eliminarMeta(idMeta, idUsuario) {
	fetch(`MetaController?ruta=eliminarMeta&idMeta=${idMeta}&idUsuario=${idUsuario}`, {
		method: 'POST'
	})
		.then(response => response.json())
		.then(data => {
			if (data.status === "success") {
				// Eliminar la fila de la tabla
				const row = document.querySelector(`button[data-id="${idMeta}"]`).closest('tr');
				row.remove();
			} else {
				console.error('Error al eliminar la meta');
			}
		})
		.catch(error => {
			console.error('Error:', error);
		});
}



function setupMetaScreen() {

	const addMetaBtn = document.getElementById('add-meta-btn');
	const editarMetaBtns = document.querySelectorAll('.editar-meta');
	const screenOverlay = document.getElementById('screenOverlay');
	const screenOverlayAgregarMeta = document.getElementById('screenOverlayAgregarMeta');
	const cerrarBtn = document.getElementById('cerrar-btn');
	const cerrarBtnAgregar = document.getElementById('cerrar-btn-agregar');
	const continuarBtn = document.getElementById('continuar-btn');
	//const continuarBtnAgregar = document.getElementById('continuar-btn-agregar');
	const screenOverlayHabitos = document.getElementById('screenOverlayHabitos');



	addMetaBtn.addEventListener('click', function() {
		screenOverlayAgregarMeta.style.display = 'flex';
	});

	cerrarBtn.addEventListener('click', function() {
		screenOverlay.style.display = 'none';
	});
	cerrarBtnAgregar.addEventListener('click', function() {
		screenOverlayAgregarMeta.style.display = 'none';
	});

	editarMetaBtns.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const metaId = btn.getAttribute('data-id');
			const metaNombre = btn.getAttribute('data-nombre');
			const metaDescripcion = btn.getAttribute('data-descripcion');
			const metaFechaInicio = btn.getAttribute('data-fecha-inicio');
			const metaFechaFin = btn.getAttribute('data-fecha-fin');

			// Llenar el formulario con los datos de la meta seleccionada
			document.getElementById('idMeta').value = metaId || '';
			document.getElementById('nombre-meta').value = metaNombre || '';
			document.getElementById('descripcion-meta').value = metaDescripcion || '';
			document.getElementById('fecha-inicio').value = metaFechaInicio || '';
			document.getElementById('fecha-fin').value = metaFechaFin || '';

			selectedMetaId = metaId; // Guardar el ID de la meta seleccionada

			screenOverlay.style.display = 'flex';
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
			const hnom = btn.getAttribute('hab-nom');
			const hcat = btn.getAttribute('hab-cat');
			const hf = btn.getAttribute('hab-f');
			const hmed = btn.getAttribute('hab-med');
			const hcan = btn.getAttribute('hab-can');
			const htime = btn.getAttribute('hab-time');
			//const hho = btn.getAttribute('hab-ho');
			document.getElementById('idhab').value = hid;
			document.getElementById('nombre-habito').value = hnom || '';
			document.getElementById('categoria-habito').value = hcat || '';
			document.getElementById('tipo-medicion').value = hmed || '';
			document.getElementById('frecuencia-habito').value = hf || '';
			document.getElementById('cantidad-habito').value = hcan || '';
			document.getElementById('tiempo-habito').value = htime || '';
			//document.getElementById('horario-habito').value = hho || '';
			editar = true;
			screenOverlayRegistroHabitos.style.display = 'flex';
			if(document.getElementById("tipo-medicion").value=="cantidad"){
			    document.getElementById("divcan").style.display = 'flex';
			    document.getElementById("divtime").style.display = 'none';
			}
			if(document.getElementById("tipo-medicion").value=="tiempo"){
				document.getElementById("divtime").style.display = 'flex';
				document.getElementById("divcan").style.display = 'none';
			}

		});
	});
	eliminarHabitoBtn.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const hid = btn.getAttribute('hab-id');
			const mid = btn.getAttribute('meta-id');
			window.location.href = `HabitoController?ruta=eliminarHabito&idmeta=${mid}&idhab=${hid}`;
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





}
function notifi(){
	var records = document.querySelectorAll('.noti');
	var time = new Date();
	console.log("Se entro en la funcionnde notificaciones");
	// Verificar que el navegador soporta notificaciones
	if (!("Notification" in window)) {
	  
	  alert("Tu navegador no soporta notificaciones");

	}else if(Notification.permission === "granted"){
	  // Lanzar notificacion si ya esta autorizado el servicio
	  records.forEach((item)=>{
		console.log(time.toLocaleTimeString());
		console.log(item.dataset.idre);
		console.log(item.dataset.mes);
		console.log(item.dataset.hor);
		console.log(time.toLocaleTimeString().substring(0,5));
		console.log(item.dataset.hor.substring(0,5));
		if(time.toLocaleTimeString().substring(0,5)==item.dataset.hor.substring(0,5)){
			var notification = new Notification(item.dataset.mes);
		}
	  });

	}else if(Notification.permission !== "denied"){
	  Notification.requestPermission(function(permission){

	    if(Notification.permission === "granted"){
			// Lanzar notificacion si ya esta autorizado el servicio
			records.forEach((item)=>{
			console.log(time.toLocaleTimeString());
			console.log(item.dataset.idre);
			console.log(item.dataset.mes);
			console.log(item.dataset.hor);
			console.log(time.toLocaleTimeString().substring(0,5));
			console.log(item.dataset.hor.substring(0,5));
			if(time.toLocaleTimeString().substring(0,5)==item.dataset.hor.substring(0,5)){
				var notification = new Notification(item.dataset.mes);
			}
			});

	    }

	  });

	}
}
