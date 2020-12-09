<%@page import="com.requestnet.entidades.Email"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:f="http://xmlns.jcp.org/jsf/core"
      xmlns:p="http://primefaces.org/ui">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <title>Envío de correos - requestNet</title>
        <meta content='text/html; charset=UTF-8' http-equiv="Content-Type"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="icon" type="image/png" href="../resources/img/favicon.png"></link>
        <link rel="stylesheet" type="text/css" href="../resources/css/styles.css"></link>
        <link rel="stylesheet" type="text/css" href="../resources/css/bootstrap.css"></link>            
        <link rel="stylesheet" type="text/css" href="../resources/assets/vendor/bootstrap/css/bootstrap.min.css"></link>
        <link rel="stylesheet" type="text/css" href="../resources/assets/vendor/font-awesome/css/font-awesome.min.css"></link>
        <link rel="stylesheet" type="text/css" href="../resources/assets/vendor/linearicons/style.css"></link>
        <link rel="stylesheet" type="text/css" href="../resources/assets/vendor/chartist/css/chartist-custom.css"></link>
        <link rel="stylesheet" type="text/css" href="../resources/assets/css/main.css"></link>
        <link rel="stylesheet" type="text/css" href="../resources/assets/css/demo.css"></link>
    </head>
    <body>
        <div id="wrapper">

        <div class="iconBurger">
            
                <div class="layerBurger topBurger"></div>
                <div class="layerBurger midBurger"></div>
                <div class="layerBurger bottomBurger"></div>
            
        </div>
        <nav class="menuGeneral"></nav>

        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="brand">
                <a href=""><img src="../resources/img/logoColorRN.png" alt="RequestNet" class="img-responsive logo "></img></a>
            </div>
            <div class="container-fluid">

                <div id="navbar-menu">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="../resources/img/userWomen4.jpg" class="img-circle" alt="Avatar"></img><span>Leidy Alfonso</span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="#"><i class="lnr lnr-user"></i> <span>Jefe Infraestructura</span></a></li>
                                <li><a href="index.xhtml"><i class="active lnr lnr-envelope"></i> <span>Enviar correo</span></a></li>
                                <!--<li><a href="#"><i class="lnr lnr-cog"></i> <span>Settings</span></a></li>-->
                                <li><a href="../"><i class="lnr lnr-exit"></i> <span>Salir</span></a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div id="sidebar-nav" class="sidebar">
            <div class="sidebar-scroll">
                <nav>
                    <ul class="nav">
                        <li><a href="../administrador/dashboard.jsp" class=""><i class="lnr lnr-home"></i> <span>Escritorio</span></a></li>
                        <li><a href="../administrador/inventario.xhtml" class=""><i class="lnr lnr-file-empty"></i> <span>Inventario</span></a></li>
                        <li><a href="../administrador/novedades.xhtml" class=""><i class="lnr lnr lnr-layers"></i> <span>Novedades</span></a></li>
                        <li><a href="../administrador/proveedores.xhtml" class=""><i class="lnr lnr-cog"></i> <span>Proveedores</span></a></li>
                        <li><a href="../administrador/usuarios.xhtml" class=""><i class="lnr lnr-users"></i> <span>Usuarios</span></a></li>
                        <li><a href="../administrador/consultar-datos.jsp" class=""><i class="lnr lnr-users"></i> <span>Cons. datos</span></a></li>
                    </ul>
                </nav>
            </div>
        </div>
            
        <div class="main">
            <div class="main-content">
                <div class="container-fluid">
                    <div class="panel panel-headline">
                        <div class="panel-heading">
                            <h3 class="panel-title">Enviar mensaje</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <%
                                        Email email = new Email();
                                        
                                        String para = request.getParameter("para");
                                        String asunto = request.getParameter("asunto");
                                        String mensaje = request.getParameter("mensaje");
                                        
                                        boolean resultado = email.enviarCorreo(para, asunto, mensaje);
                                        
                                        if (resultado) {
                                            out.print(
                                                "<h3>Mensaje enviado correctamente.</h3>"
                                                + "<br>"
                                                + "<img src='../resources/img/mensajeRecibido.jpg' alt='Correo recibido' class='img-responsive image_mails'/>"
                                                + "<br>"
                                                + "<a class='buttonLineRed' href='index.xhtml'>Enviar un mensaje nuevo.</a>"
                                            );
                                        } else {
                                            out.print(
                                                "<h3>Ha ocurrido un problema</h3>"
                                                + "<br>"
                                                + "<img src='../resources/img/mensajeNoRecibido.jpg' alt='Correo no recibido' class='img-responsive image_mails'/>"
                                                + "<br>"
                                                + "<a class='buttonLineRed' href='index.xhtml'>Intentar de nuevo.</a>"
                                            );
                                        }
                                    %>                            
                                </div>
                                            
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="../resources/assets/vendor/jquery/jquery.min.js"></script>
    <script src="../resources/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="../resources/js/navmenu.js" type="text/javascript"></script>
    <script src="../resources/js/menu.js" type="text/javascript"></script>
    <script src="../resources/js/jquery-3.2.1.js"></script>
    <script src="../resources/js/script.js"></script>
    </body>
</html>