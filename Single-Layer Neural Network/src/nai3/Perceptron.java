package nai3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Perceptron {
    String nazwaJezyka;
    Double[] Vector;
    Double progT;
    Double sredniokwadratowyBlad;
    Integer[] VectorPrzedZnormalizowaniem;
    Map<String, ArrayList<Double[]>> languageMap;

    public Perceptron(String nazwaJezyka, LinkedHashMap<String, ArrayList<Double[]>> languageMap) {
        this.nazwaJezyka = nazwaJezyka;
        VectorPrzedZnormalizowaniem = new Integer[26];
        for (int i = 0; i < VectorPrzedZnormalizowaniem.length; i++) {
            VectorPrzedZnormalizowaniem[i] = (int) (Math.random() * 10) - 5;
        }
        this.languageMap=languageMap;
        this.Vector = Main.normalize(VectorPrzedZnormalizowaniem);
        this.progT = Math.random() * 10 - 5;
        this.sredniokwadratowyBlad = 10d;
        this.teach();
    }

    public static int check(Double[] vector, Double[] p, double T){
        double iloczynSkalarny = 0;
        for(int i = 0; i < vector.length; i++){
            iloczynSkalarny += vector[i]*p[i];
        }
        return iloczynSkalarny > T ? 1 : 0;
    }

    public void teach() {
        while (sredniokwadratowyBlad > Main.progBleduE) {
            sredniokwadratowyBlad = 0d;
            for (String s : languageMap.keySet()) {
                for (Double[] text : languageMap.get(s)) {
                    int y = check(this.Vector, text, progT);
                    if (y == 1 && !s.equals(nazwaJezyka)) {
                        for (int i = 0; i < this.Vector.length; i++) {
                            this.Vector[i] += -1 * Main.A * text[i];
                        }
                        progT += -1 * Main.A * -1;
                        sredniokwadratowyBlad += 0.5 * Math.pow(-1,2);
                    } else if (y == 0 && s.equals(nazwaJezyka)) {
                        for (int i = 0; i < this.Vector.length; i++) {
                            this.Vector[i] += 1 * Main.A * text[i];
                        }
                        progT += 1 * Main.A * -1;
                        sredniokwadratowyBlad += 0.5 * Math.pow(1,2);
                    }
                }
            }
            for(String s : languageMap.keySet()){
                Collections.shuffle(languageMap.get(s));
            }
        }
    }
}