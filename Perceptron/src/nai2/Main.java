package nai2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static BufferedReader br;

    public static void main(String[] args) {

        String treningPath = "trening.csv";
        String testPath = "test.csv";
        double T =Math.random()*10 - 5;
        double A =Math.random();
        List<Double> Vector = new ArrayList<>();
        int setosaCounter =0;
        int versicolorCounter =0;

        System.out.println("Stała uczenia się: "+A);
        System.out.println("Stała T: "+T);

        List<List<Double>> trainingVectors = new ArrayList<>();
        List<String> trainingResults = new ArrayList<>();
        List<List<Double>> testVectors = new ArrayList<>();
        List<String> testResults = new ArrayList<>();

        try{
            File trainingFile = new File(treningPath);
            File testFile = new File(testPath);
            read(trainingVectors, trainingResults, trainingFile);
            read(testVectors, testResults, testFile);
            br.close();
        }catch(IOException io){
            io.printStackTrace();
        }

        for(int i = 0; i < trainingVectors.get(0).size(); i++){
            Vector.add( Math.random()*10 - 5);
        }
        System.out.println("Wektor wag: "+Vector.toString());

        int counter = 0;
        for(List<Double> vector : trainingVectors){
            int y;
            List<Double> temporaryVector = new ArrayList<>();
            y = check(Vector,vector,T);
            if((y == 1 && trainingResults.get(counter).equals("Iris-setosa"))){
                changeVector(A, Vector, vector, temporaryVector,true);
                T += -1*A*-1;
            }
            if((y == 0 && trainingResults.get(counter).equals("Iris-versicolor"))){
                changeVector(A, Vector, vector, temporaryVector,false);
                T+= 1*A*-1;
            }
            counter++;
        }

        counter = 0;
        for(List<Double> vector : testVectors){
            int y;
            y = check(Vector,vector,T);
            if(y == 0&& testResults.get(counter).equals("Iris-setosa")){
                setosaCounter++;
                System.out.println("Poprawnie rozpoznano: "+testResults.get(counter));
            }else if(y == 1&&testResults.get(counter).equals("Iris-versicolor")){
                versicolorCounter++;
                System.out.println("Poprawnie rozpoznano: "+testResults.get(counter));
            }else{
                System.out.println("Nie rozpoznano: "+testResults.get(counter));
            }
            counter++;
        }
        double setosa=(setosaCounter/15)*100;
        double versi =(versicolorCounter/15)*100;
        double suma=(setosa+versi)/2;
        System.out.println("Setosa: " + setosa + "%" );
        System.out.println("Versicolor: " + versi + "%");
        System.out.println("Rozpoznanych kwiatow: "+ suma + "%");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter vector");
        String line = "";

        while(true){
            line = sc.nextLine();
            String[] elements = line.split(",");
            if(elements.length < trainingVectors.get(0).size()){
                System.out.println("Error");
            } else {
                List<Double> testVector = new ArrayList<>();
                for(int i=0; i<elements.length;i++){
                    testVector.add(Double.parseDouble(elements[i]));
                }
                int y;
                y = check(Vector, testVector,T);
                if(y == 0){
                    System.out.println("Iris-setosa");
                }
                if(y == 1){
                    System.out.println("Iris-versicolor");
                }
            }
        }
    }

    public static void changeVector(double a, List<Double> vector, List<Double> p, List<Double> temporaryVector,boolean isSetosa) {

        if(isSetosa){
            for(int i = 0; i < vector.size(); i++){
                temporaryVector.add(vector.get(i)-1* a * p.get(i));
            }
        }
        if(!isSetosa){
            for(int i = 0; i < vector.size(); i++){
                temporaryVector.add(vector.get(i)+1* a * p.get(i));
            }
        }
        vector.clear();
        vector.addAll(temporaryVector);
        temporaryVector.clear();
    }

    public static void read(List<List<Double>> Vectors, List<String> Results, File file) throws IOException {
        String line;
        br = new BufferedReader(new FileReader(file));
        while((line = br.readLine()) != null){
            String[] elements = line.split(",");
            List<Double> vector = new ArrayList<>();
            for (int i = 0; i < elements.length-1; i++){
                vector.add(Double.parseDouble(elements[i]));
            }
            Vectors.add(vector);
            Results.add(elements[elements.length-1]);
        }
    }

    public static int check(List<Double> vector, List<Double> p, double T){
        double iloczynSkalarny = 0;
        for(int i = 0; i < vector.size(); i++){
            iloczynSkalarny += vector.get(i)*p.get(i);
        }
        return iloczynSkalarny > T ? 1 : 0;
    }
}
