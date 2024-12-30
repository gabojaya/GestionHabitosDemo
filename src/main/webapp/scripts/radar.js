//selecciona el cambas
const ctx = document.getElementById('myChart');
//se crean los datos para el grafico
const data = {
    labels: ['Comer','Tomar agua','Dormir','Jugar','Trabajar','Gym','Correr'],//aquie se cargaran los habitos de la meta
    datasets: [{
        label:'General',
        data: [65, 59, 90, 81, 56, 55, 40],//aqui las ejecuciones para el grafico
        fill: true,
        backgroundColor: 'rgba(255, 99, 132, 0.2)',//estilo del grafico
        borderColor: 'rgb(255, 99, 132)',
        pointBackgroundColor: 'rgb(255, 99, 132)',
        pointBorderColor: '#fff',
    }]
};
const config = {
    type: 'radar',
    data: data,
    options: {
        elements: {
        line: {
            borderWidth: 3
        }
        }
    },
};
new Chart(ctx,config);//se crea el grafico en el canvas indicado