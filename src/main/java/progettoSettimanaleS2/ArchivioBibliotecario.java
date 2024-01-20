package progettoSettimanaleS2;

import java.nio.channels.ScatteringByteChannel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ArchivioBibliotecario{

    final static Logger infoLogger =  LoggerFactory.getLogger("archivio_info");
    final static Logger errorLogger =  LoggerFactory.getLogger("archivio_error");

   private static ArrayList<ProdottoBibliotecario> elencoProdottiBibliotecari= new ArrayList<>();


    public static void main(String[] args) {
        Libro l1 = new Libro("Alla ricerca di nemo", LocalDate.parse("2021-02-01"),150,"Mondadori","Cartoon");
        Libro l2 = new Libro("Facile a dirsi", LocalDate.parse("2024-02-01"),150,"Allen","Commedia");
        Libro l3 = new Libro("Una giornata uggiosa", LocalDate.parse("2020-02-01"),150,"Morfino Gabriele","Drammatico");
        Libro l4 = new Libro("Romanzo Criminale", LocalDate.parse("2021-01-01"),150,"Mondadori","Mafia");

        Rivista r1 = new Rivista("Siamo quello che mangiamo",LocalDate.parse("2021-02-01"), 55, Periodicita.MENSILE);
        Rivista r2 = new Rivista("Top ten programming languages of the month",LocalDate.parse("2021-01-22"), 55, Periodicita.SETTIMANALE);
        Rivista r3 = new Rivista("La storia del campione:Alessandro Del Piero",LocalDate.parse("2020-05-22"), 55, Periodicita.SEMESTRALE);
        Rivista r4 = new Rivista("Siamo quello che mangiamo",LocalDate.parse("2021-01-11"), 55, Periodicita.MENSILE);

        addProduct(l1);
        addProduct(r1);
        addProduct(l2);
        addProduct(r2);
        addProduct(l3);
        addProduct(r3);
        addProduct(l4);
        addProduct(r4);
        System.out.println(elencoProdottiBibliotecari);


       ;





    }

    public static void addProduct(ProdottoBibliotecario prodotto){
        if (elencoProdottiBibliotecari.stream().map(el->el.getCodiceIBNS()).anyMatch(el->el == prodotto.getCodiceIBNS())){
            infoLogger.error("Il prodotto con IBNS: "+prodotto.getCodiceIBNS()+" già esiste in questo archivio");
        }else {
            elencoProdottiBibliotecari.add(prodotto);
            infoLogger.info("Il prodotto è stato aggiunto correttamente"+ prodotto);
        }

    }

    public static void deleteForIBNS(long n){
        try{
            if (!elencoProdottiBibliotecari.isEmpty() && elencoProdottiBibliotecari.stream().anyMatch(el->el.getCodiceIBNS() == n)){
                elencoProdottiBibliotecari.removeIf(el -> el.getCodiceIBNS() == n);
                errorLogger.error("Il prodotto con IBNS:"+ n +" è stato eliminato corettmente");
            }else{
                throw  new RuntimeException();

            }
        }catch(RuntimeException e){
            errorLogger.error("Non corrisponde nessun prodotto con questo IBNS: "+ n);
        }
    }

    public static ProdottoBibliotecario searchForIBNS(long n){
        try{
            if(!elencoProdottiBibliotecari.isEmpty()){
                ProdottoBibliotecario prodotto = elencoProdottiBibliotecari.stream().filter(el->el.getCodiceIBNS() == n).findAny().get();
                infoLogger.info(("il prodotto con IBNS: "+n+" è stato trovato"));
                return  prodotto;
            }else{
                throw new RuntimeException();
            }
        }catch (RuntimeException e){
            errorLogger.error("Non corrisponde nessun prodotto con questo IBNS: "+ n);
            return null;
        }
    }

    public static List<ProdottoBibliotecario> searchForAnno(String annoPublicazione){
        try{
            if(!elencoProdottiBibliotecari.isEmpty()){
                LocalDate date = LocalDate.of(Integer.parseInt(annoPublicazione),01,01);
                List<ProdottoBibliotecario> list = elencoProdottiBibliotecari.stream().
                        filter(el->el.getAnnoPubblicazione().getYear() == date.getYear()).toList();
                return list;
            }else {
                throw new RuntimeException();
            }
        }catch(RuntimeException e){
            errorLogger.error("Non corrisponde nessuno prodotto a questo anno di pubblicazione: "+ annoPublicazione);
            return  null;
        }
    }
    public static List<ProdottoBibliotecario> serchForAutore(String autore){
        try{
            if (!elencoProdottiBibliotecari.isEmpty()){
                List<ProdottoBibliotecario> listByAutore = elencoProdottiBibliotecari.stream().
                        filter(el->el instanceof Libro && ((Libro) el).getAutore().toLowerCase().equals(autore.toLowerCase()) ).toList();
                return listByAutore;
            }else{
                throw  new RuntimeException();
            }

        }catch (RuntimeException e){
            errorLogger.error("Non è stato trovato nessun prodotto dell'autore: "+autore);
            return null;
        }
        }
        }


