// Llama a usuarios mediante el plugin jQuery
$(document).ready(function() {
   
});

async function iniciarSesion(){

  let datos = {};
  datos.email = document.getElementById('txtEmail').value;
  datos. password = document.getElementById('txtPassword').value;
  
    const request = await fetch('api/login', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    });

    //Devuelve el resultado convertido en JSON y lo almaceno en la const resultado;
    const respuesta = await request.text();

    if(respuesta != 'FAIL'){
        localStorage.token=respuesta;
        // Guardamos la informacion en este caso el email
        localStorage.email = datos.email;
        window.location.href = 'usuarios.html'
    }else{
        alert("Los campos son incorrectos. Por favor intente nuevamente.");
    }


}