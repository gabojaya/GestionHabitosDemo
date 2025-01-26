console.log("Entro a mostrar");
	const valores = window.location.search;
	const urlParams = new URLSearchParams(valores);
	const ca= urlParams.get('data-ca');
	console.log(ca);
	const cf= urlParams.get('data-cf');
	console.log(cf);
	const ta= urlParams.get('data-ta');
	console.log(ta);
	const tf= urlParams.get('data-tf');
	console.log(tf);
	const ctx = document.getElementById('myChart');
	const data = {
	  labels: ['cantidad acumulada','cantidad esperada','tiempo acumulado','tiempo esperado'],
	  datasets: [{
	    label: 'Estadisticas',
	    data: [ca, cf, ta, tf],
	    backgroundColor: [
	      'rgba(255, 99, 132, 0.2)',
	      'rgba(255, 159, 64, 0.2)',
	      'rgba(255, 205, 86, 0.2)',
	      'rgba(75, 192, 192, 0.2)'
	    ],
	    borderColor: [
	      'rgb(255, 99, 132)',
	      'rgb(255, 159, 64)',
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
	new chart(ctx,config);