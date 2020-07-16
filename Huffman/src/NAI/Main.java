package NAI;

import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) {
        File file = new File("huffman.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line=br.readLine();
            List<Huffman> list= new ArrayList<>();
            int n= Integer.parseInt(line);
            System.out.println("N = "+n);
            Map<String,String> map = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                Huffman huffman = new Huffman();
                huffman.letter =split[0];
                huffman.count=Integer.parseInt(split[1]);
                list.add(huffman);
                map.put(split[0],"");
            }
            int sum=0;
            String finalString="";
            list.sort(Comparator.comparing(Huffman::getCount).thenComparing(Comparator.comparing(Huffman::getLetter)));
            System.out.println(list.toString());
            while(finalString.length()<n){
                String newString=list.get(0).letter+list.get(1).letter;
                finalString=newString;
                int newCount=list.get(0).count+list.get(1).count;
                for(Map.Entry m: map.entrySet()){
                    if(list.get(0).letter.contains(m.getKey().toString())){
                        m.setValue(m.getValue()+"0");
                    }
                    if(list.get(1).letter.contains(m.getKey().toString())){
                        m.setValue(m.getValue()+"1");
                    }
                }
                list.get(0).setLetter(newString);
                list.get(0).setCount(newCount);
                list.remove(1);
                list.sort(Comparator.comparing(Huffman::getCount).thenComparing(Comparator.comparing(Huffman::getLetter)));
                System.out.println(finalString);
           }
            System.out.println();
            for(Map.Entry m : map.entrySet()){
                m.setValue(new StringBuilder(m.getValue().toString()).reverse().toString());
            }
            map.entrySet().stream().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}