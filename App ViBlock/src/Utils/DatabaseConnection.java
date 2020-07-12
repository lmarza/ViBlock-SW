package Utils;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class DatabaseConnection
{
    private static DatabaseConnection uniqueInstance;

    private Connection connection;
    private ResultSet rs;
    private int updateRow;

    private DatabaseConnection()
    {}

    public static DatabaseConnection getInstance()
    {
        if(uniqueInstance == null)
            uniqueInstance = new DatabaseConnection();

        return uniqueInstance;
    }

    public void DBOpenConnection() {
        try {

            File loginf = new File("C:\\Users\\Luca Marzari\\Desktop\\logDB.txt");
            Scanner read = new Scanner(loginf);
            read.useDelimiter(",");
            String user = null;
            String psw = null;

            while(read.hasNext()){
                user = read.next();
                psw = read.next();
            }

            if (connection == null) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ViBlock", user, psw);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public void DBCloseConnection()
    {
        try
        {
            if(rs != null) // rs is null when an executeSQLUpdate is performed
                rs.close();

            if(connection != null)
                connection.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void executeSQLQuery(String query)
    {
        executeSQLQuery(query, List.of());
    }

    public void executeSQLQuery(String query, List<String> args)
    {
        try
        {
            final PreparedStatement preparedStmt = prepareStatement(query, args);
            rs = preparedStmt.executeQuery();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }



    public void executeSQLUpdate(String query, List<String> args)
    {
        try
        {
            final PreparedStatement preparedStmt = prepareStatement(query, args);
            updateRow = preparedStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private PreparedStatement prepareStatement(String query, List<String> args)
    {
        Object objectClass;

        try
        {
            final PreparedStatement preparedStmt = connection.prepareStatement(query);

            for (int i = 0; i < args.size(); i++) {
                objectClass = args.get(i);

                if (objectClass instanceof String)
                    preparedStmt.setString(i + 1, (String) objectClass);
                else if (objectClass instanceof BigDecimal)
                    preparedStmt.setBigDecimal(i + 1, (BigDecimal) objectClass);
                else if (objectClass instanceof Boolean)
                    preparedStmt.setBoolean(i + 1, (Boolean) objectClass);
                else if (objectClass instanceof Date)
                    preparedStmt.setDate(i + 1, (Date) objectClass);
                else if(objectClass instanceof Timestamp)
                    preparedStmt.setTimestamp(i + 1, (Timestamp) objectClass);
                else if (objectClass instanceof Double)
                    preparedStmt.setDouble(i + 1, (Double) objectClass);
                else if (objectClass instanceof Float)
                    preparedStmt.setFloat(i + 1, (Float) objectClass);
                else if (objectClass instanceof Integer)
                    preparedStmt.setInt(i + 1, (Integer) objectClass);
                else if (objectClass instanceof Long)
                    preparedStmt.setLong(i + 1, (Long) objectClass);
                else if (objectClass == null)
                    preparedStmt.setNull(i + 1, Types.NULL);
            }

            return preparedStmt;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    public ResultSet getResultSet()
    {
        return rs;
    }

    public String getSQLString(ResultSet rs, String name)
    {
        try
        {
            return rs.getString(name);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public boolean getSQLBool(ResultSet rs, String name)
    {
        try
        {
            return rs.getBoolean(name);
        }
        catch (SQLException e)
        {
            return Boolean.parseBoolean(null);
        }
    }

    public Integer getSQLInt(ResultSet rs, String name)
    {
        try
        {
            return rs.getInt(name);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public String getSQLDate(ResultSet rs, String name)
    {
        try
        {
            return rs.getString(name);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public BigDecimal getSQLNumeric(ResultSet rs, String name)
    {
        try
        {
            return rs.getBigDecimal(name);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public List<String> getSQLStringList(ResultSet rs, String name)
    {
        try
        {
            return Arrays.asList(rs.getString(name).split(","));
        }
        catch(SQLException e)
        {
            return null;
        }
    }
}