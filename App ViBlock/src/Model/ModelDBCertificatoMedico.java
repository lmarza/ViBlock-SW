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
        String dataRilascio = medicalCertificate.getDataRilascio();
        String dataScadenza = medicalCertificate.getDataScadenza();

        String[] dataR = dataRilascio.split("/");
        String[] dataS = dataScadenza.split("/");

        db.DBOpenConnection();
        db.executeSQLUpdate("INSERT INTO public.certificatomedico(datarilascio, datascadenza) " +
                "VALUES (?::DATE, ?::DATE);", List.of(dataR[1]+"/"+ dataR[0] + "/" + dataR[2], dataS[1]+"/"+ dataS[0] + "/" + dataS[2]));
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

    @Override
    public String getExpiryDateMedicalCertificate(String id) {
        String date;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT datascadenza " +
                "FROM certificatomedico " +
                "WHERE id = ?::INTEGER; ", List.of(id));

        date = resultSetToExpiryDate(db.getResultSet());

        return date;
    }

    private String resultSetToExpiryDate(ResultSet rs) {
        String date = null;
        try
        {
            while (rs.next())
            {
                date = db.getSQLDate(rs, "datascadenza");
            }

            return date;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;

    }


}
