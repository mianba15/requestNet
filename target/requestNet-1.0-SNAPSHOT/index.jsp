<%@page import="com.requestnet.conexion.Conexion" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:p="http://primefaces.org/ui">
    <head>
        <meta content='text/html; charset=UTF-8' http-equiv="Content-Type"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Login - requestNet</title>
        <link rel="icon" type="image/png" href="resources/img/favicon.png">
        <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
	<link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css">
    </head>
    <body class="bgLogin">       
        <div class="container">
            <div class="row animated fadeIn">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <div class="formsRequestNet">
                        <div class="titleForm">
                            <h2>Bienvenidos a</h2>
                            <img src="resources/img/logoWhiteRN.png"></img>
                        </div>
                        <div class="formGeneral">
                            <form action="index.jsp" id="basic-form" method="post">
                                <div class="field-wrap">
                                    <!--<label for="email">Correo electrónico<span class="req">*</span></label>-->
                                    <input type="email" id="email" name="email" value="" class="form-control form-control-user" required autocomplete="off" pattern="[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{1,5}" title="Datos erroneos" placeholder="Correo electrónico">
                                </div>
                                <div class="field-wrap">
                                    <!--<label for="password">Password<span class="req">*</span></label>-->
                                    <input type="password" id="password" name="password" value="" class="form-control form-control-user" required autocomplete="off" placeholder="Contraseña">
                                </div>
                                
                                <%
                                    Conexion conexion = new Conexion();
                                    if (request.getParameter("btnIngreso")!=null){
                                        String usuario=request.getParameter("email");
                                        String contra=request.getParameter("password");

                                        HttpSession sesion=request.getSession();

                                        switch(conexion.logueo(usuario, contra)){
                                            case 1:
                                            sesion.setAttribute("correo", usuario);
                                            sesion.setAttribute("rol", "1");
                                            request.getSession().setAttribute("correo", usuario);
                                            response.sendRedirect("administrador/dashboard.jsp");

                                        break;
                                            case 2:
                                            sesion.setAttribute("correo", usuario);
                                            sesion.setAttribute("rol", "2");
                                            response.sendRedirect("liderInventario/dashboard.jsp");

                                        break;
                                            case 3:
                                            sesion.setAttribute("correo", usuario);
                                            sesion.setAttribute("rol", "3");
                                            response.sendRedirect("tecnicoSenior/dashboard.jsp");
                                            
                                        break;
                                            case 4:
                                            sesion.setAttribute("correo", usuario);
                                            sesion.setAttribute("rol", "4");
                                            response.sendRedirect("proveedor/index.jsp");
                                            
                                        break;
                                            default:
                                            out.write("<div class='mensaje'>Usuario o contraseña inválida</div>");
                                        break;
                                        }
                                    }
                                %>
                                
                                <input type="submit" name="btnIngreso" value="Iniciar sesión" class="button button-block"></input>
                                <div class="forgot"><a class="buttonLinkOrange" href="recuperar-contrasena.html">¿Se te olvidó la contraseña?</a></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
	</div>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    </body>
</html>
