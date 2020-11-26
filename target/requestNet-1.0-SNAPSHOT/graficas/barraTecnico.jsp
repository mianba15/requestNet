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
            DefaultCategoryDataset data = new DefaultCategoryDataset();
            
            while(rs.next()){
                data.setValue(rs.getInt(2), rs.getString("nombres"), rs.getString("nombres")+"="+ rs.getInt(2) );
            }
            
            JFreeChart grafico = ChartFactory.createBarChart("Casos por técnico", "", "Número de casos", data, PlotOrientation.VERTICAL, true, true, true);
            response.setContentType("image/JPEG");
            OutputStream sa = response.getOutputStream();
            
            ChartUtilities.writeChartAsJPEG(sa, grafico, 438, 420);
            
        }catch (Exception ex){
            out.print(ex);
        }
        %>
    </body>
</html>
