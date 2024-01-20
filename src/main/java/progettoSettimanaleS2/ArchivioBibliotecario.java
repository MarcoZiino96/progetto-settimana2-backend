package progettoSettimanaleS2;

import java.io.File;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
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


        addProduct(l1);
        System.out.println(elencoProdottiBibliotecari);

        System.out.println("RICERCA PER CODICE INBS");
        searchForIBNS(499999999);
        searchForIBNS(999999998);
        searchForIBNS(1);
        System.out.println();
        System.out.println("RICERCA PER ANNO");
        searchForAnno("2019");
        searchForAnno("2024");
        System.out.println();
        System.out.println("RICERCA PER AUTORE");
        serchForAutore("Mondadori");
        serchForAutore("makengo");
        System.out.println("ELIMINIAMO UN PRDOTTO");
        deleteForIBNS(499999999);
        deleteForIBNS(2);
        System.out.println(elencoProdottiBibliotecari);

        try{
            salvaSulDisco();
            infoLogger.info("Salvataggio archivio su file");
        }catch (IOException e){
            errorLogger.error(String.valueOf(e));
        }

        try{
            ArrayList<ProdottoBibliotecario> a1 =leggiDalDisco();
            System.out.println(a1);
            infoLogger.info("Lettura corretta dal disco");
        }catch (IOException e){
            errorLogger.error(String.valueOf(e));
        }


    }

    public static void addProduct(ProdottoBibliotecario prodotto){
        if (elencoProdottiBibliotecari.stream().map(el->el.getCodiceIBNS()).anyMatch(el->el == prodotto.getCodiceIBNS())){
            errorLogger.error("Il prodotto con IBNS: "+prodotto.getCodiceIBNS()+" già esiste in questo archivio");
        }else {
            elencoProdottiBibliotecari.add(prodotto);
            infoLogger.info("Il prodotto è stato aggiunto correttamente"+ prodotto);
        }

    }

    public static void deleteForIBNS(int n){
        try{
            if (!elencoProdottiBibliotecari.isEmpty() && elencoProdottiBibliotecari.stream().anyMatch(el->el.getCodiceIBNS() == n)){
                elencoProdottiBibliotecari.removeIf(el -> el.getCodiceIBNS() == n);
                infoLogger.info("Il prodotto con IBNS:"+ n +" è stato eliminato corettamente");
            }else{
                throw  new RuntimeException();

            }
        }catch(RuntimeException e){
            errorLogger.error("Non corrisponde nessun prodotto con questo IBNS: "+ n);
        }
    }

    public static ProdottoBibliotecario searchForIBNS(int n){
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
                infoLogger.info("Risultato della tua ricerca del prodotto per anno :"+annoPublicazione+" "+list);
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
                List<ProdottoBibliotecario> listByAutore =
                elencoProdottiBibliotecari.stream().filter(el->
                el instanceof Libro && ((Libro) el).getAutore().toLowerCase().equals(autore.toLowerCase()) ).toList();
                infoLogger.info("Risultato della tua ricerca del prodotto per autore:"+listByAutore);
                return listByAutore;
            }else{
                throw  new RuntimeException();
            }
        }catch (RuntimeException e){
            errorLogger.error("Non è stato trovato nessun prodotto dell'autore: "+autore);
            return null;
        }
        }

        public static void salvaSulDisco()throws IOException {
        File fil = new File("biblioteca/archivio.txt");
        String strg = elencoProdottiBibliotecari.stream().
                map(s->{
                    if(s instanceof  Libro){
                        return "LIBRO : "+s.getCodiceIBNS()+"@"+s.getAnnoPubblicazione()+"@"+((Libro) s).getAutore()+"@"+((Libro) s).getGenere()+
                    "@"+s.getTitolo()+"@"+s.getNumeroPagine();
                    } else if (s instanceof  Rivista){
                        return  "Rivista : "+ "@" +  s.getCodiceIBNS() + "@" + s.getTitolo() + "@" + s.getAnnoPubblicazione() + "@" + s.getNumeroPagine() + "@" + ((Rivista) s).getPeriodicita();
                    }
                    return null;
                })
                .collect(Collectors.joining("# \n"));
            FileUtils.writeStringToFile(fil, strg, Charset.defaultCharset());
        }

        public static ArrayList<ProdottoBibliotecario> leggiDalDisco() throws IOException{
        File file = new File("biblioteca/archivio.txt");
        String stringFile = FileUtils.readFileToString(file,Charset.defaultCharset());
        String[] arrString = stringFile.split("#");
        ArrayList<ProdottoBibliotecario> elenco = Arrays.stream(arrString)
                .map(s->{
                    String[] stringDetails = s.split("@");
                    if(stringDetails[0].equals("LIBRO : ")){
                        Libro l = new Libro(
                                stringDetails[5] ,
                                LocalDate.parse(stringDetails[2]),
                                Integer.parseInt(stringDetails[6]),
                                stringDetails[3],
                                stringDetails[4]
                                );
                                return l;
                    } else if (stringDetails[0].equals("Rivista : ")){
                        Rivista r = new Rivista(
                                stringDetails[2] ,
                                LocalDate.parse(stringDetails[3]),
                                Integer.parseInt(stringDetails[6]),
                                Periodicita.valueOf(stringDetails[5]
                                ));
                        return r;
                    }else{
                        return null;
                    }
                }).collect(Collectors.toCollection(ArrayList::new));
        return elenco;
    }
}





