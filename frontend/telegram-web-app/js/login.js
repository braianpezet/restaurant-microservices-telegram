$(document).ready(function() {
    
});

$(document).on('click','#login',function()
{
    fetch("/json/pruebaLogin.json")
        .then(response => {
            if(response.status == 401){
                //codigo de acceso denegado
                window.alert("Registrese por telegram con el comando /login");
            }
            return response.json();
        })
        .then(data =>{
            var user = data.user;
            localStorage.setItem('firt_name', data.user.firt_name);
            localStorage.setItem('id', data.user.id);
            window.location.href = "/pages/index2.html";
        });
})