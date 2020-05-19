package nai6;

import java.util.ArrayList;
import java.util.List;

public class Przypadek {

    String nazwaAtrybutuDecyzyjnego="";
    List<String> atrybuty= new ArrayList<>();

    public Przypadek() {
    }

    public Przypadek(String nazwaAtrybutuDecyzyjnego, List<String> atrybuty) {
        this.nazwaAtrybutuDecyzyjnego = nazwaAtrybutuDecyzyjnego;
        this.atrybuty = atrybuty;
    }

    public String getNazwaAtrybutuDecyzyjnego() {
        return nazwaAtrybutuDecyzyjnego;
    }

    public void setNazwaAtrybutuDecyzyjnego(String nazwaAtrybutuDecyzyjnego) {
        this.nazwaAtrybutuDecyzyjnego = nazwaAtrybutuDecyzyjnego;
    }

    public List<String> getAtrybuty() {
        return atrybuty;
    }

    public void setAtrybuty(List<String> atrybuty) {
        this.atrybuty = atrybuty;
    }

    @Override
    public String toString() {
        return "Zachmurzenie/Opady: " + atrybuty.get(0) + "\nWiatr: "+atrybuty.get(1)+"\nWilgotność: "+atrybuty.get(2)+"\nTemperatura: "+atrybuty.get(3)+
                "\nAtrybut decyzyjny: " + nazwaAtrybutuDecyzyjnego;
    }
}
