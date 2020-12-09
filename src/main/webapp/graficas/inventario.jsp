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
            DefaultCategoryDataset data1 = new DefaultCategoryDataset();
            
            while(rs1.next()){
                data1.setValue(rs1.getInt(2), rs1.getString("nom_estadoeq"), rs1.getString("nom_estadoeq")+"="+ rs1.getInt(2) );
            }
            
            JFreeChart grafico1 = ChartFactory.createBarChart("Equipos", "", "Cantidad de equipos", data1, PlotOrientation.VERTICAL, true, true, true);
            response.setContentType("image/JPEG");
            OutputStream sa1 = response.getOutputStream();
            
            ChartUtilities.writeChartAsJPEG(sa1, grafico1, 438, 420);
            
        }catch (Exception ex){
            out.print(ex);
        }
        %>
    </body>
</html>
