package progettoSettimanaleS2;

import java.time.LocalDate;
import java.util.Random;

public class Rivista extends ProdottoBibliotecario{

        private Periodicita periodicita;

    public Rivista(String titolo, LocalDate annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
        this.setCodiceIBNS(new Random().nextLong());
    }

    public Periodicita getPeriodicita() {
            return periodicita;};





    @Override
        public String toString() {
            return "Articolo{" +
                    "IBNS='" + getCodiceIBNS() + '\'' +
                    "titolo='" + getTitolo() + '\'' +
                    "annoPubblicazione='" + getAnnoPubblicazione() + '\'' +
                    "numeroPagine='" + getNumeroPagine() + '\'' +
                    "periodicita=" + periodicita +
                    '}' +"\n";
        }


 }