import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        BPlusTree bTree = new BPlusTree();
        insertRandomNumbers(bTree, 100); // Insert 100 unique random numbers
//        for (int i = 111; i > 0; i--)
//            bTree.insert(i,i);
//        for (int i = 1; i < 112; i++)
//            bTree.insert(i,i);
        bTree.ascending();
        System.out.println();
        bTree.descending();
        System.out.println();
        bTree.inOrdem();
        System.out.println();
    }

    public static void insertRandomNumbers(BPlusTree bTree, int count) {
        Random random = new Random();
        Set<Integer> generated = new HashSet<>();
        while (generated.size() < count) {
            int randomNumber = random.nextInt(300); // Generate a random number between 0 and 999
            // Add to set. If number is unique, it will be added successfully
            if (generated.add(randomNumber)) {
                bTree.insert(randomNumber, randomNumber);
            }
        }
    }
}