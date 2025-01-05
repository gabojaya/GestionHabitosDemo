document.addEventListener('DOMContentLoaded', function() {
	const par = new URLSearchParams(window.location.search);
	if (par.get('ruta') == 'iniciarSesion') {
		showPage(1); // Mostrar la primera página al cargar la página
		setupTabs();
		setupMetaScreen();

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
			document.getElementById('nombre-meta').value = metaNombre || '';
			document.getElementById('descripcion-meta').value = metaDescripcion || '';
			document.getElementById('fecha-inicio').value = metaFechaInicio || '';
			document.getElementById('fecha-fin').value = metaFechaFin || '';

			selectedMetaId = metaId; // Guardar el ID de la meta seleccionada

			screenOverlay.style.display = 'flex';
		});
	});

	continuarBtn.addEventListener('click', function() {
		if (selectedMetaId) {
			// Redirige a la URL con el idMeta seleccionado
			window.location.href = `HabitoController?ruta=listar&idmeta=${selectedMetaId}`;
		} else {
			alert('Seleccione una meta antes de continuar.');
		}
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

	addHabitoBtn.addEventListener('click', function() {

		const urlParams = new URLSearchParams(window.location.search);
		const metaid = urlParams.get("idmeta"); // Obtén el idMeta de la URL
		document.getElementById('idmeta').value = metaid; // Asigna el valor al input oculto


		document.getElementById('nombre-habito').value = '';
		document.getElementById('categoria-habito').value = '';
		document.getElementById('tipo-medicion').value = '';
		document.getElementById('frecuencia-habito').value = '';
		editar = false;
		screenOverlayRegistroHabitos.style.display = 'flex';
	});

	guardarRegistroHabitoBtn.addEventListener('click', function() {
		var id = new URLSearchParams(window.location.search);
		let metaid = id.get("idmeta");
		if (editar) {
			document.getElementById('habito-form').action = `HabitoController?ruta=ingresarDatosModificacionHabito&idmeta=${metaid}`;
			document.getElementById('habito-form').submit();
		} else {
			document.getElementById('habito-form').action = `HabitoController?ruta=ingresarDatosHabito&idmeta=${metaid}`
			document.getElementById('habito-form').submit();
		}
		screenOverlayRegistroHabitos.style.display = 'none';

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
			const hho = btn.getAttribute('hab-ho');
			document.getElementById('idhab').value = hid;
			document.getElementById('nombre-habito').value = hnom || '';
			document.getElementById('categoria-habito').value = hcat || '';
			document.getElementById('tipo-medicion').value = hmed || '';
			document.getElementById('frecuencia-habito').value = hf || '';
			document.getElementById('cantidad-habito').value = hcan || '';
			document.getElementById('tiempo-habito').value = htime || '';
			document.getElementById('horario-habito').value = hho || '';
			editar = true;
			screenOverlayRegistroHabitos.style.display = 'flex';

		});
	});
	eliminarHabitoBtn.forEach(function(btn) {
		btn.addEventListener('click', function() {
			const hid = btn.getAttribute('hab-id');
			const mid = btn.getAttribute('meta-id');
			window.location.href = `HabitoController?ruta=eliminarHabito&idmeta=${mid}&idhab=${hid}`;
		});
	});



	/* Seccion 3: Registrar Ejecucion*/
	const registrarEjecucionBtn = document.querySelectorAll('.registrar-ejecucion');
	const guardarEjecucionBtn = document.getElementById('guardar-ejecucion-btn');
	const cerrarEjecucionBtn = document.getElementById('cerrar-ejecucion-btn');
	const screenOverlayEjecucion = document.getElementById('screenOverlayEjecucion');



	registrarEjecucionBtn.forEach(function(btn) {
		btn.addEventListener('click', function() {
			screenOverlayEjecucion.style.display = 'flex';

		});
	});


	cerrarEjecucionBtn.addEventListener('click', function() {
		screenOverlayEjecucion.style.display = 'none';
	});
	guardarEjecucionBtn.addEventListener('click', function() {
		screenOverlayEjecucion.style.display = 'none';
	});





}
