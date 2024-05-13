public class Main {
    public static void main(String[] args) {
        BPlusTree bTree = new BPlusTree();

//        for (int i = 1; i < 41; i++)
//            bTree.insert(i,i);
        for (int i = 11; i > 0; i--)
            bTree.insert(i,i);
        bTree.ascending();
        System.out.println();

        bTree.descending();
        System.out.println();

//        bTree.inOrdem();
    }
}