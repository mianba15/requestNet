$(document).ready(function(){
    $('#btnSend').click(function(){

        var validacion = '';
        
        // Validado remite ==============================
        if( $('#remite').val() == '' ){
            validacion += '<p>Escriba un correo.</p>';
            $('#remite').css("border-bottom-color", "#FE7E7E");
        } else{
            $('#remite').css("border-bottom-color", "#D1D1D1");
        }

        // Validado destino ==============================
        if( $('#destino').val() == '' ){
            validacion += '<p>Escriba el correo a quien va dirigido.</p>';
            $('#destino').css("border-bottom-color", "#FE7E7E");
        } else{
            $('#destino').css("border-bottom-color", "#D1D1D1");
        }
        
        // Validado destino ==============================
        if( $('#asunto').val() == '' ){
            validacion += '<p>Escriba un asunto</p>';
            $('#asunto').css("border-bottom-color", "#FE7E7E");
        } else{
            $('#asunto').css("border-bottom-color", "#D1D1D1");
        }

        // Validando Mensaje ===========================
        if( $('#mensaje').val() == '' ){
            validacion += '<p>Escriba un mensaje</p>';
            $('#mensaje').css("border-bottom-color", "#FE7E7E");
        } else{
            $('#mensaje').css("border-bottom-color", "#D1D1D1");
        }

        // ENVIANDO MENSAJE ============================
        if( validacion == '' == false){
            var mensajeModal =
                '<div class="modal_wrap">'+
                    '<div class="mensaje_modal">'+
                        '<h3>Errores encontrados</h3>'+
                        validacion+
                        '<span class="btn_close" id="btnClose">Cerrar</span>'
                    '</div>'+
                '</div>';

            $('body').append(mensajeModal);
        }

        // CERRANDO MODAL ==============================
        $('#btnClose').click(function() {
            $('.modal_wrap').remove();
        });

    });
});