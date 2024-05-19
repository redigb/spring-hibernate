
// Call the dataTables jQuery plugin
$(document).ready(function () {

});


async function registrarUsuarios() {
    let datos = {};
    datos.nombre = document.getElementById("txtNombre").value;
    datos.apellido = document.getElementById("txtApellido").value;
    datos.email = document.getElementById("txtEmail").value;
    datos.password = document.getElementById("txtPassword").value;
    
    let repeatPassword = document.getElementById("txtRepeatPassword").value
    if (repeatPassword != datos.password){
        alert("La contraseña debe ser igual");
        return;
    }


    // Conexion fecth
    await fetch('api/usuarios', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(datos)
    });
    alert("Cuenta registrada correctamente")
}


