// Call the dataTables jQuery plugin
$(document).ready(function () {
  cargarUsuarios();
  $('#usuarios').DataTable();
  actualizarEmailUser();
});

function actualizarEmailUser() {
    document.getElementById('txt-email-user').outerHTML = localStorage.email;
}

function getHeaders(){
    return {
       'Accept': 'application/json',
       'Content-Type': 'application/json',
       'Authorization' :  localStorage.token,
    }
}

async function cargarUsuarios() {
  const rawResponse = await fetch('api/usuarios', {
    method: 'GET',
    headers: getHeaders(),
    //PARA CREACION O ACTULIZACION body: JSON.stringify({a: 1, b: 'Textual content'})
  });

  const content = await rawResponse.json();

  console.log(content);

  let usuariosHtml = "";
  
  content.forEach(usuari => {

    let btnEliminar = '<a href="#" onclick="eliminarUsuarios('+ usuari.id +')" class="btn btn-danger btn-circle btn-sm">' +
                        "<i class='fas fa-trash'></i>" +
                      "</a>";
    let telefonotxt = usuari.telefono == null? "-": usuari.telefono;
    let usuario = "<tr>" +
      "<td>" + usuari.id + "</td>" +
      "<td>" + usuari.nombre + " " + usuari.apellido + "</td>" +
      "<td>" + usuari.email + "</td>" +
      "<td>" + telefonotxt  + "</td>" +
      "<td>" + btnEliminar + "</td>" +
      "</tr>";
    usuariosHtml += usuario;
  });


  document.querySelector('#usuarios tbody').outerHTML = usuariosHtml;
}

async function eliminarUsuarios(id) {
  if (!confirm('Desea eliminar a este usuario?')) {
    return;
  }

  await fetch('api/usuario/' + id, {
    method: 'DELETE',
    headers: getHeaders(),
  });

  location.reload();
}


