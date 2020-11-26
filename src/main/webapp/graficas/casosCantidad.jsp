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
            DefaultCategoryDataset data2 = new DefaultCategoryDataset();
            
            while(rs2.next()){
                data2.setValue(rs2.getInt(2), rs2.getString("nom_tipo_caso"), rs2.getString("nom_tipo_caso")+"="+ rs2.getInt(2) );
            }
            
            JFreeChart grafico2 = ChartFactory.createBarChart("Número de casos", "", "", data2, PlotOrientation.VERTICAL, true, true, true);
            response.setContentType("image/JPEG");
            OutputStream sa2 = response.getOutputStream();
            
            ChartUtilities.writeChartAsJPEG(sa2, grafico2, 438, 420);
            
        }catch (Exception ex){
            out.print(ex);
        }
        %>
    </body>
</html>
