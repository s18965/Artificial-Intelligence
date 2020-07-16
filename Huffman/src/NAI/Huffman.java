package NAI;

public class Huffman {
    String letter;
    int count;

    public Huffman() {
    }

    public Huffman(String s, int count) {
        this.letter = s;
        this.count = count;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "{" +
                "letter='" + letter + '\'' +
                ", count=" + count +
                '}';
    }
}
