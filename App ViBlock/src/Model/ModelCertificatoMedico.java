package Model;

import Data.MedicalCertificate;

public interface ModelCertificatoMedico {
    public void insertNewMedicalCertificate(MedicalCertificate medicalCertificate);
    public int getLastMedicalCertificateInsert();
    public String getExpiryDateMedicalCertificate(String id);
}
