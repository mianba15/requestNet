<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="conexion.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%
            try {
              DefaultPieDataset data2 = new DefaultPieDataset();
                while(rs2.next()){
                    data2.setValue(rs2.getString("nom_tipo_caso"), rs2.getInt(2));
                }
                JFreeChart grafico2 = ChartFactory.createPieChart("Porcentaje de casos", data2, true, true, true);
                response.setContentType("image/JPEG");
                OutputStream sa2 = response.getOutputStream();
                
                ChartUtilities.writeChartAsJPEG(sa2, grafico2, 438, 420);
              
            }catch (Exception ex){
                out.print(ex);
            }

            %>
    </body>
</html>
