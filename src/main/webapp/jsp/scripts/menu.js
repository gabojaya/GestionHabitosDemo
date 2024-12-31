document.addEventListener('DOMContentLoaded', function () {
    showPage(1); // Mostrar la primera página al cargar la página
    setupTabs(); 
    setupMetaScreen();
});

function setupTabs() {
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.addEventListener('click', function () {
            const pageNumber = parseInt(tab.dataset.page);
            showPage(pageNumber);
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
