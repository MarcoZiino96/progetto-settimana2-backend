package progettoSettimanaleS2;

import java.time.LocalDate;
import java.util.Random;

public class Libro extends  ProdottoBibliotecario{

    private static  int decremento = 1000000000;
    private String autore;
    private String genere;

    public Libro(String titolo, LocalDate annoPubblicazione, int numeroPagine, String autore, String genere) {
        super(titolo, annoPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
        setCodiceIBNS(decremento--);
    }

    public String getAutore() {
        return autore;
    }

    public int getDecremento() {
        return decremento;
    }

    public String getGenere() {
        return genere;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "IBNS='" + getCodiceIBNS() + '\'' +
                "titolo='" + getTitolo() + '\'' +
                "annoPubblicazione='" + getAnnoPubblicazione() + '\'' +
                "numeroPagine='" + getNumeroPagine() + '\'' +
                "autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                '}' +"\n";
    }

}
