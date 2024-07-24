//package ar.edu.utn.frc.tup.lciii.Conection;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import lombok.Getter;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Properties;
//import java.util.Scanner;
//
//@Getter
//public class ConnexionDB {
//    private static String DATABASE;
//    private static String URL ;
//    private static String USER ;
//    private static String PASSWORD ;
//    private static Boolean INITIALIZE_DATABASE ;
//    private static String CONFIG_PATH ="src/main/java/ar/edu/utn/frc/tup/lciii/Resources/config.properties";
//    private static String SCRIPT_PATH = "src/main/java/ar/edu/utn/frc/tup/lciii/Resources/data.sql";
//
//    public static String getDATABASE() {
//        return DATABASE;
//    }
//
//    public static String getURL() {
//        return URL;
//    }
//
//    public static String getUSER() {
//        return USER;
//    }
//
//    public static String getPASSWORD() {
//        return PASSWORD;
//    }
//
//    public static Boolean getInitializeDatabase() {
//        return INITIALIZE_DATABASE;
//    }
//
//    public static String getConfigPath() {
//        return CONFIG_PATH;
//    }
//
//    public static String getScriptPath() {
//        return SCRIPT_PATH;
//    }
//
//    public static ConnexionDB instance;
//    public static ConnexionDB getInstance(){
//        if (instance==null){
//            instance= new ConnexionDB();
//        }
//
//        return instance;
//    }
//
//
//
//    private ConnexionDB() {
//       setUpConfigurations();
//
//        if (INITIALIZE_DATABASE){
//            try{
//                initializeDatabase();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    void initializeDatabase() throws IOException, SQLException {
//        connect();
//
//        try (FileReader reader = new FileReader(SCRIPT_PATH);
//
//             BufferedReader bufferedReader
//                     = new BufferedReader(reader);
//
//             Connection connection = getConnection();
//
//             Statement statement
//                     = connection.createStatement();) {
//            System.out.println("Executing commands at : "
//                    + SCRIPT_PATH);
//
//
//            try {
//                statement.executeQuery("DROP DATABASE "+ DATABASE+";");
//            } catch (SQLException e) {
//
//            }
//            try {
//                statement.executeQuery("CREATE DATABASE "+ DATABASE+";");
//            } catch (SQLException e) {
//
//            }
//
//            StringBuilder builder = new StringBuilder();
//
//            String line;
//            int lineNumber = 0;
//            int count = 0;
//
//            // Read lines from the SQL file until the end of the
//            // file is reached.
//            while ((line = bufferedReader.readLine()) != null) {
//                lineNumber += 1;
////                line = line.trim();
//
//                // Skip empty lines and single-line comments.
//                if (line.isEmpty() || line.startsWith("--"))
//                    continue;
//
//                builder.append(line);
//                // If the line ends with a semicolon, it
//                // indicates the end of an SQL command.
//                if (line.endsWith(";"))
//                    try {
//                        // Execute the SQL command
//                        statement.execute(builder.toString());
//                        // Print a success message along with
//                        // the first 15 characters of the
//                        // executed command.
//                        System.out.println(
//                                ++count
//                                        + " Command successfully executed : "
//                                        + builder.substring(
//                                        0,
//                                        Math.min(builder.length(), 30))
//                                        + "...");
//                        builder.setLength(0);
//                    }
//                    catch (SQLException e) {
//                        // If an SQLException occurs during
//                        // execution, print an error message and
//                        // stop further execution.
//                        System.err.println(
//                                "At line " + lineNumber + " : "
//                                        + e.getMessage() + "\n");
//                        return;
//                    }
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    void setUpConfigurations(){
//        try {
//
//            FileInputStream propsInput = new FileInputStream(CONFIG_PATH);
//            Properties prop = new Properties();
//            prop.load(propsInput);
//
//            prop.getProperty("DB.USER");
//            URL= prop.getProperty("database.BASE_URL")+prop.getProperty("database.NAME")+prop.getProperty("database.URL_QUERY");
//            USER = prop.getProperty("database.USER");
//            PASSWORD = prop.getProperty("database.PASSWORD");
//            DATABASE = prop.getProperty("database.NAME");
//            INITIALIZE_DATABASE = Boolean.parseBoolean(prop.getProperty("database.INITIALIZE_DATABASE"));
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Connection connection;
//
//    public Connection connect() throws SQLException {
//
//        if (connection == null || connection.isClosed()) {
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//        }
//        return connection;
//    }
//
//    public void disconnect() throws SQLException {
//
//        if (connection != null && !connection.isClosed()) {
//            connection.close();
//        }
//    }
//}
//

package ar.edu.utn.frc.tup.lciii.Conection;

import java.io.BufferedReader;
import java.io.FileReader;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class ConnexionDB {
    private static String DATABASE;
    private static String URL ;
    private static String USER ;
    private static String PASSWORD ;
    private static Boolean INITIALIZE_DATABASE ;
    private static String CONFIG_PATH ="src/main/java/ar/edu/utn/frc/tup/lciii/Resources/config.properties";
    private static String SCRIPT_PATH = "src/main/java/ar/edu/utn/frc/tup/lciii/Resources/data.sql";
    private static FileInputStream fileInputStream;
    private Properties properties;
    private Connection connection;
    public static ConnexionDB instance;
    public static ConnexionDB getInstance()  {
        if (instance==null){
            try {

                instance= new ConnexionDB(new FileInputStream(CONFIG_PATH), new Properties());
            }catch (Exception ignore){

            }
        }

        return instance;
    }
    public void deleteInstance(){
        instance=null;
    }



    public ConnexionDB(FileInputStream fileInputStream, Properties properties) {
        this.fileInputStream = fileInputStream;
        this.properties = properties;
        setUpConfigurations();

        if (INITIALIZE_DATABASE){
            try{
                initializeDatabase();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void initializeDatabase() throws IOException, SQLException {
        connect();

        try (FileReader reader = new FileReader(SCRIPT_PATH);
             BufferedReader bufferedReader = new BufferedReader(reader);
             Statement statement = connection.createStatement()) {

            System.out.println("Executing commands at : " + SCRIPT_PATH);

            try {
                statement.executeUpdate("DROP DATABASE IF EXISTS " + DATABASE + ";");
                statement.executeUpdate("CREATE DATABASE " + DATABASE + ";");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            StringBuilder builder = new StringBuilder();
            String line;
            int lineNumber = 0;
            int count = 0;

            while ((line = bufferedReader.readLine()) != null) {
                lineNumber += 1;
                if (line.isEmpty() || line.startsWith("--"))
                    continue;

                builder.append(line);
                if (line.endsWith(";")) {
                    try {
                        statement.executeUpdate(builder.toString());
                        System.out.println(++count + " Command successfully executed : " + builder.substring(0, Math.min(builder.length(), 30)) + "...");
                        builder.setLength(0);
                    } catch (SQLException e) {
                        System.err.println("At line " + lineNumber + " : " + e.getMessage() + "\n");
                        return;
                    }
                }
            }
        }
    }

    public void setUpConfigurations(){
        try {
            properties.load(fileInputStream);
            URL= properties.getProperty("database.BASE_URL")+properties.getProperty("database.NAME")+properties.getProperty("database.URL_QUERY");
         USER = properties.getProperty("database.USER");
            PASSWORD = properties.getProperty("database.PASSWORD");
            DATABASE = properties.getProperty("database.NAME");
            INITIALIZE_DATABASE = Boolean.parseBoolean(properties.getProperty("database.INITIALIZE_DATABASE"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = createConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
