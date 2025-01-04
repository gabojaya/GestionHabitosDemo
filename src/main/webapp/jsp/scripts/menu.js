document.addEventListener('DOMContentLoaded', function () {
	const par = new URLSearchParams(window.location.search);
	if(par.get('ruta')=='iniciarSesion'){
		showPage(1); // Mostrar la primera página al cargar la página
		setupTabs(); 
		setupMetaScreen();
		
	}else{
		showPage(2);
		setupTabs(); 
		setupMetaScreen();
		screenOverlayHabitos.style.display = 'flex';	
	}
});

function setupTabs() {
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.addEventListener('click', function () {
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
    pages.forEach(function (page) {
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
    const cerrarBtn = document.getElementById('cerrar-btn');
    const continuarBtn = document.getElementById('continuar-btn');
    const screenOverlayHabitos = document.getElementById('screenOverlayHabitos');



    addMetaBtn.addEventListener('click', function () {
        screenOverlay.style.display = 'flex'; 
    });

    cerrarBtn.addEventListener('click', function () {
        screenOverlay.style.display = 'none'; 
    });

	editarMetaBtns.forEach(function (btn) {
	        btn.addEventListener('click', function () {
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

		continuarBtn.addEventListener('click', function () {
		        if (selectedMetaId) {
		            // Redirige a la URL con el idMeta seleccionado
		            window.location.href = `HabitoController?ruta=listar&idmeta=${selectedMetaId}`;
		        } else {
		            alert('Seleccione una meta antes de continuar.');
		        }
		    });


	/* Seccion 2: Registrar Habito*/
	
    const addHabitoBtn = document.getElementById('agregar-habito-btn');
    const editarHabitoBtn = document.querySelectorAll('.editar-habito');
    const screenOverlayRegistroHabitos = document.getElementById('screenOverlayRegistroHabitos');
    const guardarRegistroHabitoBtn = document.getElementById('guardar-habito-btn');
    const cerrarHabitoBtn = document.getElementById('cerrar-habito-btn');
    const volverAMetasBtn = document.getElementById('volver-a-metas-btn');

    addHabitoBtn.addEventListener('click', function () {
        screenOverlayRegistroHabitos.style.display = 'flex';
    });

    guardarRegistroHabitoBtn.addEventListener('click', function () {
        screenOverlayRegistroHabitos.style.display = 'none';
    });

    cerrarHabitoBtn.addEventListener('click', function () {
        screenOverlayRegistroHabitos.style.display = 'none';
    });

    volverAMetasBtn.addEventListener('click', function () {
        screenOverlayHabitos.style.display = 'none';
        screenOverlay.style.display = 'none';
    });


    editarHabitoBtn.forEach(function (btn) {
        btn.addEventListener('click', function () {
            screenOverlayRegistroHabitos.style.display = 'flex';

        });
    });



    /* Seccion 3: Registrar Ejecucion*/
    const registrarEjecucionBtn = document.querySelectorAll('.registrar-ejecucion');
    const guardarEjecucionBtn = document.getElementById('guardar-ejecucion-btn');
    const cerrarEjecucionBtn = document.getElementById('cerrar-ejecucion-btn');
    const screenOverlayEjecucion = document.getElementById('screenOverlayEjecucion');



    registrarEjecucionBtn.forEach(function (btn) {
        btn.addEventListener('click', function () {
            screenOverlayEjecucion.style.display = 'flex'; 

        });
    });


    cerrarEjecucionBtn.addEventListener('click', function () {
        screenOverlayEjecucion.style.display = 'none'; 
    });
    guardarEjecucionBtn.addEventListener('click', function () {
        screenOverlayEjecucion.style.display = 'none'; 
    });





}
