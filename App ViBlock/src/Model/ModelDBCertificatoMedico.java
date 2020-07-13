package Model;

import Data.MedicalCertificate;
import Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelDBCertificatoMedico implements ModelCertificatoMedico {
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public void insertNewMedicalCertificate(MedicalCertificate medicalCertificate) {
        db.DBOpenConnection();
        db.executeSQLUpdate("INSERT INTO public.certificatomedico(datarilascio, datascadenza) " +
                "VALUES (?::DATE, ?::DATE); ", List.of(medicalCertificate.getDataRilascio(), medicalCertificate.getDataScadenza()));
    }

    @Override
    public int getLastMedicalCertificateInsert() {
        int id;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT id " +
                "FROM certificatomedico " +
                "ORDER BY id DESC " +
                "LIMIT 1");

        id = resultSetToCountRecord(db.getResultSet());

        return id;
    }

    private int resultSetToCountRecord(ResultSet rs) {
        int id = 0;

        try
        {
            while (rs.next())
            {
                id = db.getSQLInt(rs, "id");
            }

            return id;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return Integer.parseInt(null);
    }

}