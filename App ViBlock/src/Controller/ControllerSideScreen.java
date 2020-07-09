package Controller;

import Data.Manager;
import Model.*;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;

public class ControllerSideScreen {

    public void fillLabel(ArrayList<Manager> managers, Label user1Label, Label user2Label, Label dateLabel, Label saldoAperturaLabel, Label saldoGiorLabel, Label totCassaLabel, Label PersEntrateLabel, Label prelievoLabel )
    {
        /*Managers label*/
        if (managers.size() > 1)
        {
            user1Label.setText(managers.get(0).getUsername());
            user2Label.setText(managers.get(1).getUsername());
        }
        else
            user1Label.setText(managers.get(0).getUsername());

        /*datelabel*/

        // Find the day from the local date
        Date d = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.ITALY);
        String finaleDate = dateFormat.format(d);
        dateLabel.setText(finaleDate);

        /*opening balance*/
        ModelRiepilogoGiorn DBriepilogoGior = new ModelDBRiepilogoGiorn();
        BigDecimal openiningBalance = DBriepilogoGior.getOpeningBalance();
        saldoAperturaLabel.setText(openiningBalance.toString() + "€");

        /*day Balance*/
        ModelRiepilogoGiorn dayBalanceDB = new ModelDBRiepilogoGiorn();
        BigDecimal dayBalance = dayBalanceDB.getDayBalance();
        saldoGiorLabel.setText(dayBalance.toString() + "€");

        /*Cash withdrawal*/
        ModelPrelievo modelPrelievoDB = new ModelDBPrelievo();
        BigDecimal withdrawal = modelPrelievoDB.getWithdrawal();
        prelievoLabel.setText(withdrawal.toString() + "€");

        /*total balance*/
        BigDecimal tot = (openiningBalance.add(dayBalance)).subtract(withdrawal);
        totCassaLabel.setText(tot.toString() + "€");

        /*people entered*/
        ModelEntrata modelDBentrata = new ModelDBEntrata();
        PersEntrateLabel.setText(Integer.toString(modelDBentrata.getPeopleEntered()));



    }



}
