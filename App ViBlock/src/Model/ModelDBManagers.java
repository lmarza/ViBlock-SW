package Model;

import Data.Manager;
import Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDBManagers implements ModelManager
{
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public ArrayList<Manager> getManagers()
    {
        ArrayList<Manager> managers = new ArrayList<>();

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT * " +
                            "FROM socioresponsabile ");

        managers = resultSetToManagers(db.getResultSet());


        return managers;
    }


    private ArrayList<Manager> resultSetToManagers(ResultSet rs)
    {
        ArrayList<Manager> managers = new ArrayList<>();
        Manager manager = null;

        try
        {
            while (rs.next())
            {
                manager = new Manager();
                manager.setUsername(db.getSQLString(rs, "username"));
                manager.setPsw(db.getSQLString(rs, "psw"));
                manager.setName(db.getSQLString(rs, "nome"));
                manager.setSurname(db.getSQLString(rs, "cognome"));
                managers.add(manager);
            }

            return managers;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public Manager getManagerInformation(String username) {
       Manager manager;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT * " +
                "FROM socioresponsabile " +
                "WHERE username = ?",List.of(username));

        manager = resultSetToManager(db.getResultSet());


        return manager;
    }

    private Manager resultSetToManager(ResultSet rs)
    {

        Manager manager = null;

        try
        {
            while (rs.next())
            {
                manager = new Manager();
                manager.setUsername(db.getSQLString(rs, "username"));
                manager.setPsw(db.getSQLString(rs, "psw"));
                manager.setName(db.getSQLString(rs, "nome"));
                manager.setSurname(db.getSQLString(rs, "cognome"));
            }

            return manager;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}