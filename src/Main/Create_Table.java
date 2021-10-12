package Main;

import java.sql.*;

public class Create_Table {

    // These are needed to connect to database.
    static String url = "jdbc:mysql://localhost: 3306/getting_data";
    static final String username = "root";
    static final String password = "peter";

    static Statement st = null;
    static Statement st1 = null;
    static Statement st2 = null;
    static Connection con = null;
    static String sql = null;
    static String show_table = null;
    static ResultSet rs = null;


    public static void main(String[] args) {

        try{

            System.out.print("Connecting to database...");
            System.out.println("Connected");
            con = DriverManager.getConnection(url, username, password);

        } catch(SQLException e) {
            System.out.println("Cannot connect to database.");
            e.printStackTrace();
        }
/*
        try( Statement st = con.createStatement();){
            drop_table_sql = "DROP TABLE IF EXISTS DEPARTMENT";
            st.executeUpdate(drop_table_sql);
            System.out.println("Deleting table...");
            System.out.println("Table deleted.");

        }catch(SQLException e) {
            e.printStackTrace();
        }

 */

        //Using try-with-resources
        try(Statement st1 = con.createStatement()) {
            sql = "CREATE TABLE DEPARTMENT " +
                    "(dept_id INTEGER not NULL, " +
                    " dept_name VARCHAR(255), " +
                    " dept_manager VARCHAR(255), " +
                    " PRIMARY KEY ( dept_id ))";

            System.out.println("Creating table...");

            st1.executeUpdate(sql);
            System.out.println("Table created!");
        } catch(SQLException e1) {
            System.out.println("Table not created.");
            e1.printStackTrace();

        }



        try(Statement st2 = con.createStatement()) {
            show_table = "SELECT * FROM DEPARTMENT;";
            rs = st2.executeQuery(show_table);

            while(rs.next()) {
                int id = rs.getInt("dept_id");
                String name = rs.getString("dept_name");
                String manager = rs.getString("dept_manager");
                //Format the output.
                System.out.format("%s, %s %s\n", id, name, manager);
            }
        }catch(SQLException e1) {
            System.out.println("Error getting data.");
            e1.printStackTrace();

        }finally {
            if (st1 != null) {
                try {
                    st1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(st != null) {
                try {
                    System.out.println("Connection closed!");
                    st.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    System.out.println("Connection closing...");
                    con.close();
                    System.out.println("Connection closed!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}