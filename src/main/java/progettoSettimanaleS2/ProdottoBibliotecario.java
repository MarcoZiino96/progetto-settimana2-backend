package progettoSettimanaleS2;

import java.time.LocalDate;

public abstract class  ProdottoBibliotecario {

    private long codiceIBNS;
    private String titolo;
    private LocalDate annoPubblicazione;
    private int numeroPagine;



    public ProdottoBibliotecario(String titolo, LocalDate annoPubblicazione, int numeroPagine){
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;

    }

    public  void setCodiceIBNS(long n){
        codiceIBNS = n;
    };

    public  String getTitolo(){
        return titolo;
    };

    public  LocalDate getAnnoPubblicazione(){
        return annoPubblicazione;
    };

    public  int getNumeroPagine(){
        return numeroPagine;
    }

    public long getCodiceIBNS() {
        return codiceIBNS;
    }
}
