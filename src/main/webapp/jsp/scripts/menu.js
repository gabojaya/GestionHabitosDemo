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
                        <button onclick="window.location.href='HabitoController?ruta=listar&idmeta=${meta.idMeta}'" class="editar-meta">Editar meta</button>
                        <button class="eliminar-meta">Eliminar meta</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}




function setupMetaScreen() {
	
    const addMetaBtn = document.getElementById('add-meta-btn');
    const editarMetaBtns = document.querySelectorAll('editar-meta');
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
            screenOverlay.style.display = 'flex';
        });
    });

    continuarBtn.addEventListener('click', function () {
        screenOverlayHabitos.style.display = 'flex'; 
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
