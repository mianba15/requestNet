<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="conexion.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%
        try{
            DefaultPieDataset data3 = new DefaultPieDataset();
            
            while(rs3.next()){
                data3.setValue(rs3.getString("nombre_rol"), rs3.getInt(2));
            }
            
            JFreeChart grafico3 = ChartFactory.createPieChart("Usuarios por Rol", data3, true, true, true);
            
            response.setContentType("image/JPEG");
            OutputStream sa3 = response.getOutputStream();
            
            ChartUtilities.writeChartAsJPEG(sa3, grafico3, 438, 420);
            
        }catch (Exception ex){
            out.print(ex);
        }
        %>
    </body>
</html>
