<%@page import="com.mysql.jdbc.PreparedStatement"%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:f="http://xmlns.jcp.org/jsf/core"
      xmlns:p="http://primefaces.org/ui">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>requestNet - Consultar datos</title>
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
                <a href=""><img src="../resources/img/logoColorRN.png" alt="RequestNet" class="img-responsive logo "></a>
            </div>
            <div class="container-fluid">

                <div id="navbar-menu">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="../resources/img/userWomen4.jpg" class="img-circle" alt="Avatar"><span>Leidy Alfonso</span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="#"><i class="lnr lnr-user"></i> <span>Jefe Infraestructura</span></a></li>
                                <li><a href="../email/index.xhtml"><i class="lnr lnr-envelope"></i> <span>Enviar mensaje</span></a></li>
                                <!--<li><a href="#"><i class="lnr lnr-envelope"></i> <span>Message</span></a></li>
                                <li><a href="#"><i class="lnr lnr-cog"></i> <span>Settings</span></a></li>-->
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
                        <li><a href="dashboard.jsp" class=""><i class="lnr lnr-home"></i> <span>Escritorio</span></a></li>
                        <li><a href="inventario.xhtml" class=""><i class="lnr lnr-file-empty"></i> <span>Inventario</span></a></li>
                        <li><a href="novedades.xhtml" class=""><i class="lnr lnr lnr-layers"></i> <span>Novedades</span></a></li>
                        <li><a href="proveedores.xhtml" class=""><i class="lnr lnr-cog"></i> <span>Proveedores</span></a></li>
                        <li><a href="usuarios.xhtml" class=""><i class="lnr lnr-users"></i> <span>Usuarios</span></a></li>
                        <li><a href="procesos-almacenados.jsp" class="active"><i class="lnr lnr-license"></i> <span>Cons. Datos</span></a></li>
                    </ul>
                </nav>
            </div>
        </div>
            
        <div class="main">
            <div class="main-content">
                <div class="container-fluid">
                    <div class="panel panel-headline">
                        <div class="panel-heading">
                            <h3 class="panel-title">Consultar datos</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <h3>Usuarios inactivos</h3>
                                    <div>
                                        <table>
                                            <tr>
                                                <th>ID</th>
                                                <th>Tipo Documento</th>
                                                <th>Número Documento</th>
                                                <th>Nombres</th>
                                                <th>Apellidos</th>
                                                <th>Ciudad</th>
                                                <th>Cargo</th>
                                                <th>Estado</th>
                                            </tr>
                                            <%           
                                            try {
                                                Connection Conexion=DriverManager.getConnection("jdbc:mysql://localhost:8889/requestnetweb", "root", "root");  
                                                CallableStatement procedimiento = Conexion.prepareCall("{call Usuariosinactivos}");    

                                                final ResultSet rs = procedimiento.executeQuery();  

                                                while (rs.next()) {  
                                            %>

                                            <tr>
                                                <td> <%=rs.getInt("id_usuario")%> </td>
                                                <td> <%=rs.getString("nombre_tipdoc")%> </td>
                                                <td> <%=rs.getInt("num_documento")%> </td>
                                                <td> <%=rs.getString("nombres")%> </td>
                                                <td> <%=rs.getString("apellidos")%> </td>
                                                <td> <%=rs.getString("nombre_ciu")%> </td>
                                                <td> <%=rs.getString("cargo")%> </td>
                                                <td> <%=rs.getString("nom_estadoru")%> </td>
                                            </tr>

                                            <%}    

                                            }catch (SQLException e)  {  
                                                }  
                                            %>

                                        </table>
                                    </div>
                                </div>
                                            
                                <div class="col-md-12">
                                    <h3>Equipos Asignados</h3>
                                    <div>
                                        <table>
                                            <tr>
                                                <th>ID Equipo</th>
                                                <th>Estado Equipo</th>
                                                <th>Tipo Componente</th>
                                                <th>Marca</th>
                                                <th>ID Usuario</th>
                                                <th>Nombres</th>
                                                <th>Apellidos</th>
                                            </tr>
                                            <%           
                                            try{  
                                                Connection Conexion=DriverManager.getConnection("jdbc:mysql://localhost:8889/requestnetweb", "root", "root");  
                                                CallableStatement procedimiento = Conexion.prepareCall("{call equiposAsignados}");    
                                                final ResultSet rs = procedimiento.executeQuery();
                                                while (rs.next()) {  
                                            %>
                                            <tr>
                                                <td> <%=rs.getInt("id_equipo")%> </td>
                                                <td> <%=rs.getString("nom_estadoeq")%> </td>
                                                <td> <%=rs.getString("nom_tipo_componente")%> </td>
                                                <td> <%=rs.getString("nom_marca")%> </td>
                                                <td> <%=rs.getInt("id_cliente")%> </td>
                                                <td> <%=rs.getString("nombres_cliente")%> </td>
                                                <td> <%=rs.getString("apellidos_cliente")%> </td>
                                            </tr>
                                            <%  }    
                                            } catch (SQLException e)  { }  
                                            %>
                                        </table>
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
    <script src="../resources/assets/vendor/chartist/js/chartist.min.js"></script>
    <script src="../resources/assets/vendor/chartist/js/chartist_datos.js"></script>
    <!--<script src="../resources/assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
    <script src="../resources/assets/scripts/klorofil-common.js"></script>-->
    <script src="../resources/js/navmenu.js" type="text/javascript"></script>
    <script src="../resources/js/menu.js" type="text/javascript"></script>
    </body>
</html>