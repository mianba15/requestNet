package com.requestnet.conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
        
    /* Parametros de Conexión */
    private static Connection conn;
    private static String driver;
    private static String urlDB; 
    private static String loginUser;
    private static String password;
    
    public Conexion(){
        driver = "com.mysql.jdbc.Driver";
        urlDB = "jdbc:mysql://localhost:8889/requestnetweb";
        loginUser = "root";
        password = "root";
    }
    /* Parametros de Conexión */
    
    /* Conexión a la base de datos para el CRUD */
    public static Connection getInstanceConnection() {
        if (!(conn instanceof Connection)) {
            System.out.println("Conectando a la BD.");
            try {
                conn = DriverManager.getConnection(urlDB, loginUser, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(conn);
        return conn;
    }
    /* Conexión a la base de datos para el CRUD */
    
    /* Logueo de usuarios según Rol */
    public int logueo (String us, String pass){
        Connection conn;
        PreparedStatement pst;
        ResultSet rs;
        int rol=0;
        String sql="Select id_rol from usuarios where correo='" + us + "' and contraseña='" + pass +"'";
        try {
            Class.forName(this.driver);
            conn = DriverManager.getConnection(
            this.urlDB,
            this.loginUser,
            this.password
            );
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while(rs.next()){
                rol = rs.getInt(1);
            }
            conn.close();
        } catch(ClassNotFoundException | SQLException e){
        }
        return rol;
    }
    /* Logueo de usuarios según Rol */
    
    /* Procesos almacenados */    
    public static void main(String []args) throws SQLException {

        try {  
            Connection Conexion=DriverManager.getConnection(urlDB, loginUser, password);  
            CallableStatement procedimiento = Conexion.prepareCall("{call Usuariosinactivos}");
            
            final ResultSet rs = procedimiento.executeQuery();  

            while (rs.next()) { 
                System.out.println(rs.getInt("id_usuario")); 
                System.out.println(rs.getString("tipo_documento"));
                System.out.println(rs.getInt("num_documento"));
                System.out.println(rs.getString("nombres"));
                System.out.println(rs.getString("apellidos"));
                System.out.println(rs.getString("ciudad"));
                System.out.println(rs.getString("cargo"));
                System.out.println(rs.getString("estado"));
            }
            
        }
        catch (SQLException e)  {  
        }  
    }
}