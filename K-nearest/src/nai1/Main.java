package nai1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int k;
        int nr =1;
        int number=0;
        String resultat;
        double accurate=0;
        int numberOfVectors=0;
        List<String> results = new ArrayList<>();
        List<Double> results1 = new ArrayList<>();
        String test = System.getProperty("user.home") + "/test-set.csv";
        String training = System.getProperty("user.home") + "/train-set.csv";
        System.out.println("Podaj wartosc k");
        Scanner sc = new Scanner(System.in);
        k = sc.nextInt();
        Map<String,List<List<Double>>> trainingMap = new HashMap<>();
        Map<String,List<List<Double>>> testMap = new HashMap<>();
        number=getData(training,trainingMap);

        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(test));
            List<Double> all = new ArrayList<>();
            Map<String, List<Double>> map = new HashMap<>();
            while((line=br.readLine())!=null) {
                String s[] = line.split(";");
                List<Double> list = new ArrayList();
                for (int i = 0; i < s.length - 1; i++) {
                    list.add(Double.parseDouble(s[i]));
                }
                System.out.println("\nWektor nr " +nr++ + " " + list.toString());

                resultat =policzWektor(trainingMap,list,all,k,results,results1);
                System.out.println(resultat);
                if(resultat.equals(s[s.length-1])){
                    accurate++;
                    numberOfVectors++;
                }else{
                    numberOfVectors++;
                }
            }
            System.out.println("Accuracy: "+ accurate/numberOfVectors);
            accurate=0;
            numberOfVectors=0;
        }catch(IOException io){
            System.out.println("Blad");
        }

        List<Double> all = new ArrayList<>();
        List<Double> wektor = new ArrayList<>();
        Map<String, List<Double>> map = new HashMap<>();
        int licznik=0;
        while(true){

            do
            {
                wektor.add(sc.nextDouble());
                licznik++;
            }while(licznik!=number);
            licznik=0;
            System.out.println("\n");
            System.out.println(policzWektor(trainingMap,wektor,all,k,results,results1));
        }
    }

    public static String policzWektor(Map<String,List<List<Double>>> map,List<Double> list, List<Double> all,int k,List<String> results, List<Double> results1 ){
        Map<String, List<Double>> mapka = new HashMap<>();
        double distance;
        double power;
        double sum=0;
        int d=0;
        int c=0;
        int f=0;
        String resultat;
        for (Map.Entry<String, List<List<Double>>> m : map.entrySet()) {
            for (List<Double> arrayList : m.getValue()) {
                for (int b=0; b<list.size(); b++) {
                    power = Math.pow(arrayList.get(b)-list.get(b), 2);
                    sum+=power;
                }
                distance=Math.sqrt(sum);
                if (!mapka.containsKey(m.getKey())) {
                    mapka.put(m.getKey(),new ArrayList<>());
                }
                all.add(distance);
                mapka.get(m.getKey()).add(distance);
                Collections.sort(mapka.get(m.getKey()));

                sum=0;
            }
        }

        Collections.sort(all);
        int i=0;
        int indeks=0;
        while(i<k){
            for (Map.Entry<String,List<Double>> lista : mapka.entrySet()) {
                for (int a = 0; a < lista.getValue().size(); a++) {
                    if(lista.getValue().get(a).equals(all.get(indeks)) && !results1.contains(all.get(indeks)))
                    {
                        results.add(lista.getKey());
                        results1.add(lista.getValue().get(a));
                        i++;
                    }
                }
            }
            indeks++;
        }
        for(String result : results){
            for (Map.Entry<String,List<Double>> lista : mapka.entrySet()) {
                if ("Iris-virginica".equals(result)) {
                    d++;
                }
                if ("Iris-versicolor".equals(result)) {
                    f++;
                }
                if ("Iris-setosa".equals(result)) {
                    c++;
                }
            }
        }
        if(d>c){
            if(d>f){
                resultat="Iris-virginica";
            }else{
                resultat="Iris-versicolor";
            }
        }
        else{
            if(c>f){
                resultat="Iris-setosa";
            }else{
                resultat="Iris-versicolor";
            }
        }
        c=0;
        d=0;
        f=0;
        results1.clear();
        all.clear();
        results.clear();
        list.clear();
        return resultat;
    }

    public static int getData(String path,Map<String,List<List<Double>>> map){
        String line;
        int number=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((line=br.readLine())!=null) {
                String s[] = line.split(";");
                List<Double> list=new ArrayList();
                number=s.length-1;

                for(int i=0; i<s.length-1; i++){
                    list.add(Double.parseDouble(s[i]));
                }
                if(map.containsKey(s[s.length-1])){
                    map.get(s[s.length-1]).add(list);
                }else{
                    map.put(s[s.length-1],new ArrayList<>());
                    map.get(s[s.length-1]).add(list);
                }
            }
        }catch(IOException io){
            System.out.println("Zła ścieżka");
        }
        return number;
    }

}
