package nai5;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File file = new File("iris.csv");
        int k = 3,counterIteracji=0;
        List<double[]> vectors = new ArrayList<>();
        List<Klaster> Klasters = new ArrayList<>();
        boolean zmiana = true;

        read(file,vectors);

        for (int i = 0; i < k; i++) {
            Klasters.add(new Klaster(vectors.get(0).length, min(vectors), max(vectors)));
        }

        while (zmiana) {
           int counterBrakuZmian = 0;
            fillVectorList(vectors,Klasters);

            for (Klaster klaster : Klasters) {
                System.out.println(klaster);
            }

            for (Klaster kl : Klasters) {
                double[] nowyCentroid = obliczCentroid(kl);
                if(obliczOdlegloscKwadratowa(kl.centroid, nowyCentroid) == 0)
                    kl.zmiana = false;
                    kl.centroid=(obliczCentroid(kl));
                    kl.punkty.clear();
            }

            for(Klaster klaster:Klasters){
                if(!klaster.zmiana){
                    counterBrakuZmian++;
                }
            }
            if(counterBrakuZmian == k){
                zmiana = false;
            }
            System.out.println("Koniec iteracji nr "+ ++counterIteracji+"\n");
        }
    }

    public static void read(File file,List<double[]> vectors){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");

                double[] vector = new double[split.length - 1];
                for (int i = 0; i < vector.length; i++) {
                    vector[i] = Double.parseDouble(split[i]);
                }
                vectors.add(vector);
            }
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    public static double obliczOdlegloscKwadratowa(double[] vector, double[] centroid){
        double result = 0;
        for(int i = 0; i < vector.length; i++){
            result += Math.pow(vector[i] - centroid[i], 2);
        }
        return (result);
    }
    public static void fillVectorList(List<double[]> vectors, List<Klaster> Klasters){
        int id=0;
        for (int i = 0; i < vectors.size(); i++) {
            double min = 100000;
            for (int j = 0; j < Klasters.size(); j++) {
                if (obliczOdlegloscKwadratowa(vectors.get(i), Klasters.get(j).centroid) < min) {
                    min = obliczOdlegloscKwadratowa(vectors.get(i), Klasters.get(j).centroid);
                    id = j;
                }
            }
            Klasters.get(id).punkty.add(vectors.get(i));
        }
    }

    public static double[] obliczCentroid(Klaster klaster){
        double[] centroid = new double[klaster.centroid.length];
        Arrays.fill(centroid,0);
        int counter = 0;

        for(int i = 0; i < klaster.punkty.size(); i++){
            for(int j = 0; j < klaster.punkty.get(i).length; j++){
                centroid[j] += klaster.punkty.get(i)[j];
            }
            counter++;
        }

        for(int i = 0; i < centroid.length; i++){
            centroid[i] = centroid[i] / counter;
        }
        return centroid;
    }

    public static double[] min(List<double[]> vectors){
        double[] minVector = new double[vectors.get(0).length];
        System.arraycopy(vectors.get(0), 0, minVector, 0, minVector.length);

        for (double[] doubles : vectors) {
            for (int j = 0; j < doubles.length; j++) {
                minVector[j] = Math.min(minVector[j], doubles[j]);
            }
        }
        return minVector;
    }

    public static double[] max(List<double[]> vectors){
        double[] maxVector = new double[vectors.get(0).length];
        System.arraycopy(vectors.get(0), 0, maxVector, 0, maxVector.length);

        for (double[] doubles : vectors) {
            for (int j = 0; j < doubles.length; j++) {
                maxVector[j] = Math.max(maxVector[j], doubles[j]);
            }
        }
        return maxVector;
    }
}
