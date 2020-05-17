package knapsack_problem;

import java.util.ArrayList;
import java.util.List;

public class Knapsack {
    int capacity;
    int maxQuantity;
    List<Integer> bestVector;

    public Knapsack() {
        this.bestVector = new ArrayList<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public List<Integer> getBestVector() {
        return bestVector;
    }

    public void setBestVector(List<Integer> bestVector) {
        this.bestVector = bestVector;
    }
}
