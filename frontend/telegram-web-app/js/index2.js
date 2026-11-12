// Configuración de endpoints de los microservicios
const API_CONFIG = {
    MENU_SERVICE_URL: 'http://localhost:7070',
    PEDIDO_SERVICE_URL: 'http://localhost:7075'
};

$(document).ready(function() {
    traerMenu();
    let currentValue = parseInt($('.quantityInput'));
    arrayPlatos = []; //variable que tiene los ids de los platos, para poder realizar el pedido
    console.log(currentValue);
});

function traerMenu()
{
    fetch(`${API_CONFIG.MENU_SERVICE_URL}/menu/platos/1`, {
        method:'GET',
        mode:'cors',
        headers:{
            'content-type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data =>{
            var filacontenedora = $('#fila_contenedor')

            $.each(data, function( index, plato ) {

                var imagen = plato.urlFoto;
                var descripcion = plato.descripcion;
                var nombre = plato.nombre;
                var precio = plato.precio;
                var identificador = plato.id;

                var textHtml = `<div class="col-6 col-md-4 col-xl-3 " style="margin-top:15px; margin-bottom:15px">
                                    <div class="card" id=${identificador}>
                                        <div class="card-body">
                                            <img src=${imagen} class="card-img-top" alt="...">
                                            <h5 class="card-title">${nombre}</h5>
                                            <p class="lead">${descripcion}</p>
                                            <h6> Precio:$${precio}</h6>

                                            
                                            <div class="btn-añadir" id=${identificador}>
                                                <button type="button" class="btn btn-success btn-sm decreaseBtn"  id="decreaseBtn">-</button>
                                                    <input type="text" class="quantityInput" id="${identificador}" value="0">
                                                <button type="button" class="btn btn-success btn-sm increaseBtn" id="increaseBtn">+</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;

                filacontenedora.append(textHtml);
                
              });
              $(document).on('click','#decreaseBtn', decreaseQuantity);
              $(document).on('click','#increaseBtn', increaseQuantity);
        });
}

function decreaseQuantity(){
    let quantityInput = $(this).siblings('.quantityInput');
    let currentValue = parseInt(quantityInput.val());
    console.log(currentValue);
    if(currentValue > 0){
        quantityInput.val(currentValue - 1);
        arrayPlatos.pop(parseInt(quantityInput.attr("id")));
        console.log(arrayPlatos);
    }

}

function increaseQuantity(){
    let quantityInput = $(this).siblings('.quantityInput');
    let currentValue = parseInt(quantityInput.val());
    console.log("id plato : " + quantityInput.attr('id'));
    console.log(currentValue);
    arrayPlatos.push(parseInt(quantityInput.attr("id")));
    console.log(arrayPlatos);
    quantityInput.val(currentValue + 1);
}

$(document).on('click', '#finalizar', function (){
    if(arrayPlatos.length == 0){
        window.alert("agregue productos a su pedido.");
    }
    else{

        //se arma el pedido
        let pedido = {
            "estado": "En curso",
            "platos": arrayPlatos
        };

        fetch(`${API_CONFIG.PEDIDO_SERVICE_URL}/pedido`, {
        method:'POST',
        mode:'cors',
        body: JSON.stringify(pedido),
        headers:{
            'content-type': 'application/json',
        }})
        .then(response => response.json())
        .then(data =>{
            localStorage.setItem('id_pedido', data.id);
            var platos = data.platos;
            var htmlModal = $('#contenido_modal');

            $.each(platos, function( index, plato ){

                var textHtml = `<p class="text-center"> ${plato.nombre} ----- precio: $${plato.precio}</p>`;

                htmlModal.append(textHtml);
            });

            var totalHtml = `<h5 class="text-center">Total: $${data.total}</h5>`;
            htmlModal.append(totalHtml);
        });
    }
});

$(document).on('click', '#eliminarPedido', function(){

    let deleteDTO = {
        "id": parseInt(localStorage.getItem('id_pedido'))
    };

    fetch(`${API_CONFIG.PEDIDO_SERVICE_URL}/pedido`, {
    method:'DELETE',
    mode:'cors',
    body: JSON.stringify(deleteDTO),
    headers:{
        'content-type': 'application/json',
    }})
    .then(response => {
        if(response.status == 200){
            console.log("El pedido se elimino correctamente.");
        }
    })

    $('#contenido_modal').empty();
});

$(document).on('click', '#pagar_pedido', function(){
    window.alert("en progreso");
});




