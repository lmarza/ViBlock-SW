package View;

import Data.Entrata;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ViewRiepilogoGiornataJFX implements ViewRiepilogoGiornata {

    @Override
    public void buildDaySummary(ArrayList<Entrata> records, TableView recordTableView) {
        TableColumn nome = new TableColumn("Nome");
        TableColumn cognome = new TableColumn("Cognome");
        TableColumn sesso = new TableColumn("Sesso");
        TableColumn under18 = new TableColumn("Under18");
        TableColumn ingresso = new TableColumn("Tipo Ingresso");
        TableColumn tipopagamento = new TableColumn("Tipo Pagamento");
        TableColumn importo = new TableColumn("Importo");
        TableColumn tesseramento = new TableColumn("Tesseramento");
        TableColumn scarpette = new TableColumn("Scarpette");

        recordTableView.getColumns().addAll(nome, cognome, sesso, under18, ingresso, tipopagamento, importo, tesseramento, scarpette);

        final ObservableList<Entrata> r= FXCollections.observableArrayList();
        r.addAll(records);


        nome.setCellValueFactory(new PropertyValueFactory<Entrata, String>("nome"));
        cognome.setCellValueFactory(new PropertyValueFactory<Entrata, String>("cognome"));
        sesso.setCellValueFactory(new PropertyValueFactory<Entrata, String>("sesso"));
        under18.setCellValueFactory(new PropertyValueFactory<Entrata, Boolean>("under18"));
        ingresso.setCellValueFactory(new PropertyValueFactory<Entrata, String>("ingresso"));
        tipopagamento.setCellValueFactory(new PropertyValueFactory<Entrata, String>("tipopagamento"));
        importo.setCellValueFactory(new PropertyValueFactory<Entrata, BigDecimal>("importo"));
        tesseramento.setCellValueFactory(new PropertyValueFactory<Entrata, String>("tesseramento"));
        scarpette.setCellValueFactory(new PropertyValueFactory<Entrata, String>("scarpette"));


        recordTableView.setItems(r);
    }
}
