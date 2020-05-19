package nai5;
import java.util.ArrayList;

public class Klaster {
    static int counter = 1;
    int id;
    double[] centroid;
    ArrayList<double[]> punkty =new ArrayList<>();;
    boolean zmiana=true;

    public Klaster(int liczbaAtrybutow, double[] min, double[] max){
        id = counter++;
        centroid = new double[liczbaAtrybutow];
        for(int i = 0; i < min.length; i++){
            centroid[i] =  (Math.random() * (max[i] - min[i])) + min[i];
        }
    }

    public double sumaKwadratow(){
        double sumaKwadratówOdległościWewnątrzKlastru = 0;
        for(int i = 0; i < punkty.size(); i++) {
            for(int j = 0; j < punkty.get(i).length; j++){
                sumaKwadratówOdległościWewnątrzKlastru += Math.pow(centroid[j] - punkty.get(i)[j],2);
            }
        }
        return sumaKwadratówOdległościWewnątrzKlastru;
    }

    public String toString(){
        StringBuilder klasterLog = new StringBuilder();
        klasterLog.append("Klaster: ");
        klasterLog.append(id+"\n");
        klasterLog.append("Centroid: ");
        for (double d : centroid) {
            klasterLog.append(d).append("   ");
        }
        klasterLog.append("\n");
        klasterLog.append("Suma kwadratów: ").append(sumaKwadratow()+"\n");
        klasterLog.append("Liczba elementow: ").append(punkty.size()+"\n");
        klasterLog.append("Punkty: \n");

        for(int i = 0; i < punkty.size(); i++){
            for(int j = 0; j < punkty.get(i).length; j++){
                klasterLog.append(punkty.get(i)[j]).append("  ");
            }
            klasterLog.append("\n");
        }
        return klasterLog.toString();
    }
}
