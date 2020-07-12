package Controller;

import com.jfoenix.controls.JFXTextField;

import java.util.Calendar;

public class CotrollerPersonInformation {
    public String fieldsAreValid(JFXTextField name, JFXTextField surname, JFXTextField birthday, JFXTextField birthPlace, String sex) {

        StringBuilder error = new StringBuilder();
        int year = Calendar.getInstance().get(Calendar.YEAR);

        if(name.getText().trim().isEmpty())
            error.append("-Il nome deve essere inserito!\n");
        if(isNumerical(name.getText()))
            error.append("-Il nome non deve avere cifre!\n");
        if(surname.getText().trim().isEmpty())
            error.append("-Il cognome deve essere inserito!\n");
        if(isNumerical(surname.getText()))
            error.append("-Il cognome non deve avere cifre!\n");

        if(birthday.getText().trim().isEmpty())
        {
            error.append("-La data di nascita deve essere inserita!\n");
        }
        else
        {
            String[] split = birthday.getText().split("/");
            if(split.length < 3)
            {
                error.append("-La data di nascita deve avere formato DD/MM/YYYY!\n");
            }
            else
            {
                if(!isNumerical(split[0]) || split[0].length() > 2 || (Integer.parseInt(split[0]) > 31 ||(Integer.parseInt(split[0]) < 1)))
                    error.append("-Giorno nascita non valido!\n");
                if(!isNumerical(split[1]) || split[1].length() > 2 || (Integer.parseInt(split[1]) < 1 ||(Integer.parseInt(split[1]) > 12)))
                    error.append("-Mese nascita non valido!\n");
                if(!isNumerical(split[2]) || split[2].length() != 4 || (Integer.parseInt(split[2]) > year ||(Integer.parseInt(split[2]) < 1900)))
                    error.append("-Anno nascita non valido (YYYY, 4 cifre necessarie)!\n");
            }

        }

        if (isNumerical(birthPlace.getText()))
            error.append("-Luogo nascita non valido!\n");
        if (sex == null || !sex.equalsIgnoreCase("M") && !sex.equalsIgnoreCase("F"))
            error.append("-Sesso non valido!\n");

        return error.toString();
    }

    private boolean isNumerical(String s) {
        return s.matches("[+]?([0-9]*[.])?[0-9]+");
    }

}
