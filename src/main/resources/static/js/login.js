
// Call the dataTables jQuery plugin
$(document).ready(function () {

});


async function iniciarSesion() {
    let datos = {};
    datos.email = document.getElementById("txtEmail").value;
    datos.password = document.getElementById("txtPassword").value;


    const rawResponse = await fetch('api/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(datos)
    });

    const response = await rawResponse.text();
    console.log(response);
    if (response != "Fallo") {
        localStorage.token = response;
        localStorage.email = datos.email;
        window.location.href = "tablesUsers.html";
    }else{
        alert("Credenciales incorrectas, Intente nuevamente")
    }
}


