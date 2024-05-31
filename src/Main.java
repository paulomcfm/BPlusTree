import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        BPlusTree bTree = new BPlusTree();
        for (int i = 1; i < 20; i++)
            bTree.insert(i,i);
        bTree.ascending();
        System.out.println();
        bTree.descending();
        System.out.println();
        bTree.inOrdem();
        System.out.println();
//        for (int i = 2; i < 19; i=i+2) {
//            bTree.remove(i);
//            bTree.ascending();
//            System.out.println();
//            bTree.descending();
//            System.out.println();
//            bTree.inOrdem();
//            System.out.println();
//        }
        for (int i = 19; i >0; i--){
            bTree.remove(i);
            bTree.ascending();
            System.out.println();
            bTree.descending();
            System.out.println();
            bTree.inOrdem();
            System.out.println();
        }
    }
}