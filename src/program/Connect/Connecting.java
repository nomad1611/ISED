package program.Connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connecting {
    public  static Connection Connect (String database) {
        Connection connect;

        try {
            String url="jdbc:mysql://localhost:3307/"+database;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, "root", "max123456!");
            System.out.println("Connected to database");
            return connect;
        } catch (Exception ex) {
            System.out.println(ex);
            return  null;


        }

    }

}
