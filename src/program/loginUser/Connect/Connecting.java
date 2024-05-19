package program.loginUser.Connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connecting {
    public  static Connection Connect () {
        Connection connect;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/ISED", "root", "max123456!");
            System.out.println("Connected to database");
            return connect;
        } catch (Exception ex) {
            System.out.println(ex);
            return  null;


        }

    }

}
