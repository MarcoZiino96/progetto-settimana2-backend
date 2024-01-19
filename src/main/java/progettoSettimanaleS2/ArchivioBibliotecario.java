package progettoSettimanaleS2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ArchivioBibliotecario{

   private static ArrayList<ProdottoBibliotecario> elencoProdottiBibliotecari= new ArrayList<>();


    public static void main(String[] args) {
        Libro l1 = new Libro("Alla ricerca di nemo", LocalDate.parse("2021-02-01"),150,"Mondadori","Cartoon");
        Rivista r1 = new Rivista("Siamo quello che mangiamo",LocalDate.parse("2020-05-22"), 55, Periodicita.SETTIMANALE);
        System.out.println(l1);
        System.out.println(r1);

        addProduct(l1);
        addProduct(r1);
        System.out.println(elencoProdottiBibliotecari);

        System.out.println(searchForAnno(l1.getAnnoPubblicazione()));




    }
    public static void addProduct(ProdottoBibliotecario prodotto){
        elencoProdottiBibliotecari.add(prodotto);
    }

    public static void deleteForIBNS(long n){
        if (elencoProdottiBibliotecari.stream().anyMatch(el->el.getCodiceIBNS() == n)){
            elencoProdottiBibliotecari.removeIf(el -> el.getCodiceIBNS() == n);
        }else{
            System.out.println("Non corrisponde nessuno prodotto a questo codice");
        }
    }
    public static List<ProdottoBibliotecario> searchForAnno(String annoPublicazione){
        LocalDate date = LocalDate.of(Integer.parseInt(annoPublicazione),01,01);
      List<ProdottoBibliotecario> list = elencoProdottiBibliotecari.stream().
              filter(el->el.getAnnoPubblicazione().getYear() == date.getYear()).toList();
      return list;
    }
}

