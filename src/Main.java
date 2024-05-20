import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        BPlusTree bTree = new BPlusTree();
//        insertRandomNumbers(bTree, 100);
//        for (int i = 111; i > 0; i--)
//            bTree.insert(i,i);
        for (int i = 1; i < 20; i++)
            bTree.insert(i,i);
        bTree.ascending();
        System.out.println();
        bTree.descending();
        System.out.println();
        bTree.inOrdem();
        System.out.println();
        bTree.remove(12);
        bTree.ascending();
        System.out.println();
        bTree.descending();
        System.out.println();
        bTree.inOrdem();
        System.out.println();
        bTree.remove(13);
        bTree.ascending();
        System.out.println();
        bTree.descending();
        System.out.println();
        bTree.inOrdem();
        System.out.println();
        bTree.remove(6);
        bTree.ascending();
        System.out.println();
        bTree.descending();
        System.out.println();
        bTree.inOrdem();
        System.out.println();
        bTree.remove(1);
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
            int randomNumber = random.nextInt(300);
            if (generated.add(randomNumber)) {
                bTree.insert(randomNumber, randomNumber);
            }
        }
    }
}