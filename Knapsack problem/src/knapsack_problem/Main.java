package knapsack_problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
    Knapsack knapsack = new Knapsack();
    Item items[]=readFromFile("knapsack.txt",knapsack);
    int biggestSum= getBiggestSum(knapsack,items);
    System.out.println("\nFinal Vector:" + knapsack.bestVector.toString());
    System.out.println("Biggest sum: " + biggestSum);
    }

    private static Item[] readFromFile(String path,Knapsack knapsack){
        Item[] items ={};
        try{
            BufferedReader bf=new BufferedReader(new FileReader(path));
            String line=bf.readLine();
            String elements[] = line.split(" ");
            knapsack.capacity = Integer.parseInt(elements[0]);
            knapsack.maxQuantity = Integer.parseInt(elements[1]);
            items=new Item[knapsack.maxQuantity];
            for (int i = 0; i < items.length; i++) {
                items[i]=new Item();
            }
            line=bf.readLine();
            elements = line.split(",");
            for (int i = 0; i < items.length; i++)
                items[i].value=Integer.parseInt(elements[i]);
            line=bf.readLine();
            elements= line.split(",");
            for (int i = 0; i < items.length; i++)
                items[i].weight=Integer.parseInt(elements[i]);
            bf.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return items;
    }

    private static int getBiggestSum(Knapsack knapsack,Item items[]){
    int biggestSum=0;
    int biggestWeight = 0;
    List<Integer> currentVector = new ArrayList<>();
    double numberOfPossibilities=Math.pow(2,items.length);
    int bit=0;
    int currentSum=0;
    int currentWeight=0;
    for(int i=0; i<numberOfPossibilities;i++){
        System.out.print(i + ". -> ");
        for(int j=0; j<items.length;j++){
            bit=i>>j &1;
            currentVector.add(bit);
            if(bit==1){
                currentSum+=bit*items[j].value;
                currentWeight+=bit*items[j].weight;
            }
            System.out.print(bit+" ");
        }
        if(currentSum>biggestSum && currentWeight<=knapsack.capacity){
            biggestSum = currentSum;
            biggestWeight = currentWeight;
            knapsack.bestVector.clear();
            knapsack.bestVector.addAll(currentVector);
            System.out.println("\n\nNew Biggest Vector: " +knapsack.getBestVector().toString() + " biggest sum: "+biggestSum  + " weight: " +biggestWeight);
        }
        System.out.println();
        currentVector.clear();
        currentSum=0;
        currentWeight=0;
    }
    return biggestSum;
    }
}