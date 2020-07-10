package View;

import Data.RiepilogoGiornaliero;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class ViewRiepilogoMensileJFX implements ViewRiepilogoMensile {
    @Override
    public void buildMonthSummary(ArrayList<RiepilogoGiornaliero> dailySummaries, TableView recordTableView) {
        TableColumn data = new TableColumn("Data");
        TableColumn saldofinale = new TableColumn("Saldo Finale");
        TableColumn tesseramenti = new TableColumn("N° Tesseramenti");
        TableColumn solditesseramenti = new TableColumn("Soldi Tesseramenti");
        TableColumn entrategiornaliere = new TableColumn("Incasso giornaliero");
        TableColumn prelievi = new TableColumn("Prelievo");


        recordTableView.getColumns().addAll(data, saldofinale, tesseramenti, solditesseramenti, entrategiornaliere, prelievi);

        final ObservableList<RiepilogoGiornaliero> d= FXCollections.observableArrayList();
        d.addAll(dailySummaries);


        data.setCellValueFactory(new PropertyValueFactory<RiepilogoGiornaliero, Date>("data"));
        saldofinale.setCellValueFactory(new PropertyValueFactory<RiepilogoGiornaliero, BigDecimal>("saldoFinale"));
        tesseramenti.setCellValueFactory(new PropertyValueFactory<RiepilogoGiornaliero, Integer>("tesseramenti"));
        solditesseramenti.setCellValueFactory(new PropertyValueFactory<RiepilogoGiornaliero, BigDecimal>("soldiTesseramenti"));
        entrategiornaliere.setCellValueFactory(new PropertyValueFactory<RiepilogoGiornaliero, BigDecimal>("entrateGiornata"));
        prelievi.setCellValueFactory(new PropertyValueFactory<RiepilogoGiornaliero, BigDecimal>("prelievi"));



        recordTableView.setItems(d);
    }
}
