package nai6;

import java.io.*;
import java.util.*;

public class Main {

    public static BufferedReader br;
    public static void main(String[] args) {
        String trainingPath = "trainingset.csv";
        String testPath = "testset.csv";
        double counter=0;

        List<HashSet<String>> mozliweWartosci=new ArrayList<>();
        try{
            File trainingFile = new File(trainingPath);
            File testFile = new File(testPath);
            List<Przypadek> przypadki = new ArrayList<>();
            read(trainingFile,przypadki,mozliweWartosci);
            read(testFile,przypadki,mozliweWartosci);
            System.out.println("Możliwe wartości kolejnych atrybutów:");
            for(HashSet h:mozliweWartosci){
                System.out.println(h.toString());
            }

            Map<String,Integer> liczbaWystapienDanegoAtrybutuDecyzyjnego = new HashMap<>();
            Map<String,int[]> licznikWystapienDlaDanegoAtrybutuDecyzyjnego = new HashMap<>();
            System.out.println();
            for(Przypadek m:przypadki){
                counter=sprawdz(m,przypadki,licznikWystapienDlaDanegoAtrybutuDecyzyjnego,liczbaWystapienDanegoAtrybutuDecyzyjnego,mozliweWartosci,counter);
            }
            System.out.println("\nŁącznie wszystkich wystąpień w treningu: "+liczbaWystapienDanegoAtrybutuDecyzyjnego);

            Scanner sc = new Scanner(System.in);
            String line;
            System.out.println("Proszę wpisac dane recznie w celu klasyfikacji: (np. zachmurzenie,tak,niska,wysoka)");
            while(true){
                line = sc.nextLine();
                String[] elements = line.split(",");
                if(elements.length !=przypadki.get(0).atrybuty.size()){
                    System.out.println("Error");
                } else {
                    Przypadek przypadek = new Przypadek();
                    for(int i=0; i<elements.length;i++){
                        przypadek.atrybuty.add(elements[i]);
                    }
                    sprawdz(przypadek,przypadki,licznikWystapienDlaDanegoAtrybutuDecyzyjnego,liczbaWystapienDanegoAtrybutuDecyzyjnego,mozliweWartosci,counter);
                }
            }
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    public static double sprawdz(Przypadek m,List<Przypadek> przypadki,Map<String,int[]> licznikWystapienDlaDanegoAtrybutuDecyzyjnego,Map<String,Integer> liczbaWystapienDanegoAtrybutuDecyzyjnego,List<HashSet<String>> mozliweWartosci,double counter){

        if(m.nazwaAtrybutuDecyzyjnego.equals("")){
            for(Przypadek training:przypadki){
                if(training.nazwaAtrybutuDecyzyjnego!="") {
                    for (Map.Entry<String, int[]> entry : licznikWystapienDlaDanegoAtrybutuDecyzyjnego.entrySet()) {
                        if (entry.getKey().equals(training.nazwaAtrybutuDecyzyjnego)) {
                            for (int i = 0; i < m.atrybuty.size(); i++) {
                                if (m.atrybuty.get(i).equals(training.atrybuty.get(i))) {
                                    licznikWystapienDlaDanegoAtrybutuDecyzyjnego.get(entry.getKey())[i]++;
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("\nTest");
            Map<String,Double> wartosciP= new HashMap<>();
            for(Map.Entry<String,int[]> entry : licznikWystapienDlaDanegoAtrybutuDecyzyjnego.entrySet()) {
                double p =liczbaWystapienDanegoAtrybutuDecyzyjnego.get(entry.getKey())/counter;
                System.out.println(entry.getKey());
                for(int i=0; i<entry.getValue().length;i++){
                    System.out.print(entry.getValue()[i]+" ");
                    p*=(entry.getValue()[i]+1)/((double)liczbaWystapienDanegoAtrybutuDecyzyjnego.get(entry.getKey())+mozliweWartosci.get(i).size());
                }
                wartosciP.put(entry.getKey(),p);
                System.out.println();
            }

            System.out.print("\n"+m.toString());
            String wybor="";
            double najwieksza=0;
            for(Map.Entry<String, Double> entry :wartosciP.entrySet()){
                if(entry.getValue()>najwieksza){
                    najwieksza=entry.getValue();
                    wybor=entry.getKey();
                }
            }
            System.out.println(wybor+"\n");

            wartosciP.entrySet().forEach(System.out::println);
            System.out.println();
        }else{
            System.out.println("Trening\n"+m.toString());
            System.out.println();
            boolean dodac=true;

            for(Map.Entry<String,Integer> entry : liczbaWystapienDanegoAtrybutuDecyzyjnego.entrySet()){
                if(entry.getKey().equals(m.nazwaAtrybutuDecyzyjnego)){
                    dodac=false;
                    entry.setValue(entry.getValue()+1);
                }
            }
            if(dodac){
                liczbaWystapienDanegoAtrybutuDecyzyjnego.put(m.nazwaAtrybutuDecyzyjnego,1);
                licznikWystapienDlaDanegoAtrybutuDecyzyjnego.put(m.nazwaAtrybutuDecyzyjnego,new int[m.atrybuty.size()]);
            }
            counter++;
        }
        for(Map.Entry<String,int[]> entry : licznikWystapienDlaDanegoAtrybutuDecyzyjnego.entrySet()) {
            entry.setValue(new int[m.atrybuty.size()]);
        }
        return counter;
    }

    public static void read(File file,List<Przypadek> list, List<HashSet<String>> mozliweWartosci) throws IOException {
        String line;
        br = new BufferedReader(new FileReader(file));
        while((line = br.readLine()) != null){
            String[] elements = line.split(",");
            Przypadek przypadek= new Przypadek();
            if(file.getName().equals("trainingset.csv")){
                przypadek.nazwaAtrybutuDecyzyjnego=elements[elements.length-1];
                for(int i =0;i<elements.length;i++){
                    if(i<elements.length-1){
                        przypadek.atrybuty.add(elements[i]);
                    }
                    if(mozliweWartosci.size()!=elements.length){
                        mozliweWartosci.add(new HashSet<>());
                    }
                    mozliweWartosci.get(i).add(elements[i]);
                }
            }else{
                for(int i =0;i<elements.length;i++){
                    przypadek.atrybuty.add(elements[i]);
                }
            }
            list.add(przypadek);
            }
        br.close();
    }
}
