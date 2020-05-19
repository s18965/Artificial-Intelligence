package nai3;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class Main {

    static HashMap<String, ArrayList<Integer[]>> map = new HashMap<>();
    static int counter = 0;
    static List<Perceptron> perceptronList  = new ArrayList<>();
    static List<String> languageNames = new ArrayList<>();
    static double progBleduE;
    static double A;

    public static void main(String[] args) {
        progBleduE =0.5;
        A=0.5;
        processDir("lib", map);
        LinkedHashMap<String, ArrayList<Double[]>> normalisedMap = new LinkedHashMap<>();
        for(String s : map.keySet()){
            ArrayList<Double[]> list = new ArrayList<>();
            for (Integer[] i : map.get(s)){
                Double[] wektor = normalize(i);
                list.add(wektor);
            }
            normalisedMap.put(s,list);
        }

        for(String s : normalisedMap.keySet()){
            Perceptron perceptron = new Perceptron(s,normalisedMap);
            perceptronList.add(perceptron);
            languageNames.add(s);
        }
        whileTrue();
        }

    public static void whileTrue(){
        while(true) {
            System.out.println("Wpisz tekst");
            String linia = "";
            String tekst = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (!linia.equals("q")) {
                try {
                    linia = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tekst += linia;
            }

            Integer[] wejscie = new Integer[26];
            Arrays.fill(wejscie, 0);
            for (char c : tekst.toLowerCase().toCharArray()) {
                if (c >= 'a' && c <= 'z') {
                    wejscie[c - 'a']++;
                }
            }
            Double znormalizowanyText[] = normalize(wejscie);
            int y;
            for (Perceptron p : perceptronList) {
                y = Perceptron.check(p.Vector, znormalizowanyText, p.progT);
                if (y == 1) {
                    System.out.println(p.nazwaJezyka);
                }
            }
        }
    }

    public static void processDir(String dirName, HashMap<String, ArrayList<Integer[]>> mapa) {
        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(counter < 3) {
                    File languageFile = file.toFile();
                    String language = languageFile.getParentFile().getName();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(languageFile)));
                    if (file.toString().toLowerCase().endsWith(".txt")) {
                        String line;
                        Integer[] input = new Integer[26];
                        Arrays.fill(input, 0);
                        while ((line = bufferedReader.readLine()) != null) {
                            for (char c : line.toLowerCase().toCharArray()) {
                                if (c >= 'a' && c <= 'z') {
                                    input[c - 'a']++;
                                }
                            }
                        }
                        if(mapa.containsKey(language)){
                            mapa.get(language).add(input);
                        } else{
                            ArrayList<Integer[]> textList = new ArrayList<>();
                            textList.add(input);
                            mapa.put(language,textList);
                        }
                    }
                }
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                if(!dir.toString().equals("lib")){
                    counter++;
                }
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            Files.walkFileTree(Paths.get(dirName), fileVisitor);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    static public Double[] normalize(Integer[] wektor){
        Double result = 0d;
        for (Integer i : wektor) {
            result += Math.pow(i, 2);
        }
        result = Math.sqrt(result);
        Double[] normalised = new Double[wektor.length];
        for(int i = 0; i < wektor.length; i++){
            normalised[i] = wektor[i]/result;
        }
        return normalised;
    }
}