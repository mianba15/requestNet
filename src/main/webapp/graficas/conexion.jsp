<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>
<%@page import="org.jfree.chart.*"%>
<%@page import="org.jfree.chart.plot.*"%>
<%@page import="org.jfree.data.general.*"%>
<%@page import="org.jfree.data.category.DefaultCategoryDataset"%>
<%@page import="org.jfree.data.category.DefaultCategoryDataset.*"%>
<!DOCTYPE html> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:8889/WebRequestNet?user=WebRequestNet&password=pass12345");
                
            Statement cmd = cn.createStatement();
            String sql = "SELECT usuarios.nombres, COUNT(*) AS Total FROM casos INNER JOIN tecnico ON tecnico.id_tecnico = casos.id_tecnico INNER JOIN usuarios ON tecnico.id_usuario = usuarios.id_usuario GROUP BY usuarios.nombres";
            ResultSet rs = cmd.executeQuery(sql);

            Statement cmd1 = cn.createStatement();
            String sql1 = "SELECT inventario_equipos.estado_equipo, COUNT(*) AS TotalEquipos FROM inventario_equipos GROUP BY estado_equipo";
            ResultSet rs1 = cmd1.executeQuery(sql1);
            
            Statement cmd2 = cn.createStatement();
            String sql2="SELECT tipo_caso.nom_tipo_caso, COUNT(*) AS Total FROM casos INNER JOIN tipo_caso on tipo_caso.id_tipo_caso = casos.id_tipo_caso GROUP BY nom_tipo_caso";
            ResultSet rs2 = cmd2.executeQuery(sql2);
            
            Statement cmd3 = cn.createStatement();
            String sql3 = "SELECT roles.nombre_rol, COUNT(*) AS Total FROM usuarios INNER JOIN roles ON roles.id_rol = usuarios.id_rol GROUP BY roles.nombre_rol;";
            ResultSet rs3 = cmd3.executeQuery(sql3);
        %>
    </body>
</html>
