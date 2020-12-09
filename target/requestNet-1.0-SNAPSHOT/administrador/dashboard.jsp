<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:f="http://xmlns.jcp.org/jsf/core"
      xmlns:p="http://primefaces.org/ui">
    <head>
        <title>requestNet - Dashboard</title>
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
            <div class="hamburger">
                <div class="layerBurger topBurger"></div>
                <div class="layerBurger midBurger"></div>
                <div class="layerBurger bottomBurger"></div>
            </div>
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
                                <li><a href="../email/index.xhtml"><i class="lnr lnr-envelope"></i> <span>Enviar mensaje</span></a></li>
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
                        <li><a href="dashboard.jsp"     class="active"><i class="lnr lnr-home"></i> <span>Escritorio</span></a></li>
                        <li><a href="inventario.xhtml"  class=""><i class="lnr lnr-file-empty"></i> <span>Inventario</span></a></li>
                        <li><a href="novedades.xhtml"   class=""><i class="lnr lnr lnr-layers"></i> <span>Novedades</span></a></li>
                        <li><a href="proveedores.xhtml" class=""><i class="lnr lnr-cog"></i> <span>Proveedores</span></a></li>
                        <li><a href="usuarios.xhtml"    class=""><i class="lnr lnr-users"></i> <span>Usuarios</span></a></li>
                        <li><a href="consultar-datos.jsp" class=""><i class="lnr lnr-license"></i> <span>Cons. Datos</span></a></li>
                    </ul>
                </nav>
            </div>
        </div>
            
        <div class="main">
            <div class="main-content">
                <div class="container-fluid">
                    
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-headline">
                                <div class="panel-body">
                                    <div class="titleModul">
                                        <h2>Novedades</h2>
                                    </div>
                                    
                                    <div class="row">
                                        <div class="col-md-6">
                                            <iframe style="border:0" width="100%" height="440" src="http://localhost:8080/requestNet/graficas/casosPorcetaje.jsp"></iframe>
                                        </div>
                                        
                                        <div class="col-md-6">
                                            <iframe style="border:0" width="100%" height="440" src="http://localhost:8080/requestNet/graficas/barraTecnico.jsp"></iframe>
                                        </div>
                                    </div>
                                    
                                    <div class="">
                                        <h3><a class="buttonLineRed" href="novedades.xhtml">Ver novedades</a></h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-6">
                            <div class="panel panel-headline">
                                <div class="panel-body">
                                    <div class="titleModul">
                                        <h2>Inventario</h2>
                                    </div>
                                    
                                    <iframe style="border:0" width="100%" height="440" src="http://localhost:8080/requestNet/graficas/inventario.jsp"></iframe>
                                    
                                    <div class="">
                                        <h3><a class="buttonLineRed" href="inventario.xhtml">Ver inventario</a></h3>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-6">
                            <div class="panel panel-headline">
                                <div class="panel-body">
                                    <div class="titleModul">
                                        <h2>Usuarios</h2>
                                    </div>
                                    
                                    <iframe style="border:0" width="100%" height="440" src="http://localhost:8080/requestNet/graficas/usuarios.jsp"></iframe>
                                    
                                    <div class="">
                                        <h3><a class="buttonLineRed" href="usuarios.xhtml">Ver usuarios</a></h3>
                                    </div>
                                    
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
    <script src="../resources/assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
    <script src="../resources/js/navmenu.js" type="text/javascript"></script>
    <script src="../resources/js/menu.js" type="text/javascript"></script>
    </body>
</html>