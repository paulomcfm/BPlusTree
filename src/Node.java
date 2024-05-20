public class Node {
    public static final int m=5;
    private int vInfo[];
    private int vPos[];
    private Node vLig[];
    private int TL;
    private Node prev;
    private Node next;
    public Node() {
        vInfo=new int[m+1];
        vPos=new int[m+1];
        vLig=new Node[m+2];
        TL=0;
        prev=null;
        next=null;
    }

    public Node(int info, int posArq){
        this();
        vInfo[0]=info;
        vPos[0]=posArq;
        TL=1;
    }

    public int getPosition(int info){
        int pos = 0;
        while(pos<TL && info>=vInfo[pos])
            pos++;
        return pos;
    }

    public int getPositionExclusion(int info) {
        int pos = 0;
        while(pos<TL && info>vInfo[pos])
            pos++;
        return pos;
    }

    public void rearrange(int pos){
        vLig[TL+1] = vLig[TL];
        for(int j=TL;j>pos;j--){
           vInfo[j]=vInfo[j-1];
           vPos[j]=vPos[j-1];
           vLig[j]=vLig[j-1];
        }
    }
    public void rearrangeExclusion(int pos) {
        for (int i = pos; i < TL; i++) {
            vInfo[i]=vInfo[i+1];
            vPos[i]=vPos[i+1];
            vLig[i]=vLig[i+1];
        }
        vLig[TL]=vLig[TL+1];
    }

    public int getvInfo(int p) {
        return vInfo[p];
    }

    public void setvInfo(int p, int info) {
        vInfo[p]=info;
    }

    public int getvPos(int p) {
        return vPos[p];
    }

    public void setvPos(int p, int posArq) {
        vPos[p] = posArq;
    }

    public Node getvLig(int p) {
        return vLig[p];
    }

    public void setvLig(int p, Node lig) {
        vLig[p] = lig;
    }

    public int getTl() {
        return TL;
    }

    public void setTl(int tl) {
        this.TL = tl;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
