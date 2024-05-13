public class BPlusTree
{
    private Node root;

    public BPlusTree()
    {
        root = null;
    }

    private Node goToLeaf(int info)
    {
        Node atual = root;
        int pos;
        while (atual.getvLig(0)!=null)
        {
            pos = atual.getPosition(info);
            atual = atual.getvLig(pos);
        }
        return atual;
    }

    private Node locateParent(Node leaf, int info)
    {
        Node atual = root;
        Node parent = atual;
        int pos;
        while(atual != leaf)
        {
            parent = atual;
            pos = atual.getPosition(info);
            atual = atual.getvLig(pos);
        }
        return parent;
    }

    public void inOrdem()
    {
        in_ordem(root);
    }

    private void in_ordem(Node no)
    {
        if(no!=null)
        {
            for(int i=0; i<no.getTl(); i++)
            {
                in_ordem(no.getvLig(i));
                System.out.println(no.getvInfo(i));
            }
            in_ordem(no.getvLig(no.getTl()));
        }
    }
    private void split(Node leaf, Node parent)
    {
        if(leaf.getvLig(0)==null){
            Node cx1 = new Node();
            Node cx2 = new Node();
            int max = (int)Math.ceil((double)(Node.m-1)/2);
            for(int i = 0; i<max; i++)
            {
                cx1.setvInfo(i, leaf.getvInfo(i));
                cx1.setvPos(i, leaf.getvPos(i));
                cx1.setvLig(i, leaf.getvLig(i));
            }
            cx1.setvLig(max, leaf.getvLig(max));
            cx1.setTl(max);

            for(int i = max; i<leaf.getTl() ; i++)
            {
                cx2.setvInfo(i-max, leaf.getvInfo(i));
                cx2.setvPos(i-max, leaf.getvPos(i));
                cx2.setvLig(i-max, leaf.getvLig(i));
                cx2.setTl(cx2.getTl()+1);
            }
            cx2.setvLig(cx2.getTl(), leaf.getvLig(leaf.getTl()));

            cx1.setNext(cx2);
            cx2.setPrev(cx1);
            int posParent = parent.getPosition(leaf.getvInfo(0));
            Node leftS = null, rightS = null;
            if (parent!=leaf && posParent-1 >= 0)
                leftS = parent.getvLig(posParent-1);
            else{
                Node grandParent = locateParent(parent, parent.getvInfo(0));
                int posGrandParent = grandParent.getPosition(parent.getvInfo(0));
                if (grandParent!=parent && posGrandParent-1 >=0)
                    leftS = locateSubL(grandParent, posGrandParent-1);
            }
            if (parent!=leaf && posParent+1 <= parent.getTl())
                rightS = parent.getvLig(posParent+1);
            else{
                Node grandParent = locateParent(parent, parent.getvInfo(0));
                int posGrandParent = grandParent.getPosition(parent.getvInfo(0));
                if (grandParent!=parent && posGrandParent+1<grandParent.getTl())
                    rightS = locateSubR(grandParent, posGrandParent+1);
            }
            if(leftS!=null){
                leftS.setNext(cx1);
                cx1.setPrev(leftS);
            }
            if(rightS!=null){
                rightS.setPrev(cx2);
                cx2.setNext(rightS);
            }
            if(parent==leaf)
            {
                leaf.setvInfo(0, cx2.getvInfo(0));
                leaf.setvPos(0, cx2.getvPos(0));
                leaf.setTl(1);
                leaf.setvLig(0, cx1);
                leaf.setvLig(1, cx2);
            }
            else
            {
                int pos = parent.getPosition(cx2.getvInfo(0));
                parent.rearrange(pos);
                parent.setvInfo(pos, cx2.getvInfo(0));
                parent.setvPos(pos, cx2.getvPos(0));
                parent.setTl(parent.getTl()+1);
                parent.setvLig(pos, cx1);
                parent.setvLig(pos+1, cx2);
                if(parent.getTl()>Node.m-1)
                {
                    leaf=parent;
                    parent= locateParent(leaf, leaf.getvInfo(0));
                    split(leaf, parent);
                }
            }
        }
        else{
            Node cx1 = new Node();
            Node cx2 = new Node();
            int max = (int)Math.ceil((double)(Node.m-1)/2);
            for(int i = 0; i<max; i++)
            {
                cx1.setvInfo(i, leaf.getvInfo(i));
                cx1.setvPos(i, leaf.getvPos(i));
                cx1.setvLig(i, leaf.getvLig(i));
            }
            cx1.setvLig(max, leaf.getvLig(max));
            cx1.setTl(max);

            for(int i = max+1; i<leaf.getTl() ; i++)
            {
                cx2.setvInfo(max+1-i, leaf.getvInfo(i));
                cx2.setvPos(max+1-i, leaf.getvPos(i));
                cx2.setvLig(max+1-i, leaf.getvLig(i));
                cx2.setTl(cx2.getTl()+1);
            }
            cx2.setvLig(cx2.getTl(), leaf.getvLig(leaf.getTl()));

            if(parent==leaf)
            {
                leaf.setvInfo(0, leaf.getvInfo(max));
                leaf.setvPos(0, leaf.getvPos(max));
                leaf.setTl(1);
                leaf.setvLig(0, cx1);
                leaf.setvLig(1, cx2);
            }
            else
            {
                int pos = parent.getPosition(leaf.getvInfo(max));
                parent.rearrange(pos);
                parent.setvInfo(pos, leaf.getvInfo(max));
                parent.setvPos(pos, leaf.getvPos(max));
                parent.setTl(parent.getTl()+1);
                parent.setvLig(pos, cx1);
                parent.setvLig(pos+1, cx2);
                if(parent.getTl()>Node.m-1)
                {
                    leaf=parent;
                    parent= locateParent(leaf, leaf.getvInfo(0));
                    split(leaf, parent);
                }
            }
        }
    }

    public void insert(int info, int posArq)
    {
        Node leaf, parent;
        int pos;
        if(root == null)
            root = new Node(info,posArq);
        else
        {
            leaf = goToLeaf(info);
            pos = leaf.getPosition(info);
            leaf.rearrange(pos);
            leaf.setvInfo(pos, info);
            leaf.setvPos(pos, posArq);
            leaf.setTl(leaf.getTl() + 1);
            if(leaf.getTl() > Node.m-1)
            {
                parent = locateParent(leaf, info);
                split(leaf, parent);
            }
        }
    }

    public void ascending()
    {
        Node node = root;
        while(node.getvLig(0)!=null){
            node=node.getvLig(0);
        }
        for (int i = 0; i < node.getTl(); i++) {
            System.out.print(node.getvInfo(i)+ " ");
        }
        while (node.getNext()!=null) {
            node=node.getNext();
            for (int i = 0; i < node.getTl(); i++) {
                System.out.print(node.getvInfo(i)+ " ");
            }
        }
    }

    public void descending()
    {
        Node node = root;
        while(node.getvLig(node.getTl())!=null){
            node=node.getvLig(node.getTl());
        }
        for (int i = node.getTl()-1; i >=0; i--) {
            System.out.print(node.getvInfo(i)+ " ");
        }
        while (node.getPrev()!=null) {
            node=node.getPrev();
            for (int i = node.getTl()-1; i >=0; i--) {
                System.out.print(node.getvInfo(i)+ " ");
            }
        }
    }

    private Node locateNode(int info)
    {
        Node node = root;
        int pos = node.getPosition(info);
        while(node.getvLig(0)!=null && node.getvInfo(pos)!=info){
            node = node.getvLig(pos);
            pos = node.getPosition(info);
        }
        if(node.getvLig(0)==null && node.getvInfo(pos)!=info)
            return null;
        return node;
    }

    private Node locateSubL(Node node, int pos)
    {
        node = node.getvLig(pos);
        while(node.getvLig(0)!=null)
            node = node.getvLig(node.getTl());
        return node;
    }

    private Node locateSubR(Node node, int pos)
    {
        node = node.getvLig(pos);
        while(node.getvLig(0)!=null)
            node = node.getvLig(0);
        return node;
    }

    private void redistributeConcatenate(Node leaf)
    {
        Node parent = locateParent(leaf, leaf.getvInfo(0));
        int posParent = parent.getPosition(leaf.getvInfo(0));
        Node leftS = null, rightS = null;
        if (posParent-1 >= 0)
            leftS = parent.getvLig(posParent-1);
        if (posParent+1 <= parent.getTl())
            rightS = parent.getvLig(posParent+1);

        if(leftS!=null && leftS.getTl()> Node.m)
        {
            leaf.rearrange(0);
            leaf.setvInfo(0, parent.getvInfo(posParent-1));
            leaf.setvPos(0, parent.getvPos(posParent-1));
            leaf.setTl(leaf.getTl()+1);
            parent.setvInfo(posParent-1, leftS.getvInfo(leftS.getTl()-1));
            parent.setvPos(posParent-1, leftS.getvPos(leftS.getTl()-1));
            leaf.setvLig(0, leftS.getvLig(leftS.getTl()));
            leftS.setTl(leftS.getTl()-1);
        }
        else
        if(rightS!=null && rightS.getTl()> Node.m)
        {
            leaf.setvInfo(leaf.getTl(), parent.getvInfo(posParent));
            leaf.setvPos(leaf.getTl(), parent.getvPos(posParent));
            leaf.setTl(leaf.getTl()+1);
            parent.setvInfo(posParent, rightS.getvInfo(0));
            parent.setvPos(posParent, rightS.getvPos(0));
            leaf.setvLig(leaf.getTl(), rightS.getvLig(0));
            rightS.rearrangeExclusion(0);
            rightS.setTl(rightS.getTl()-1);
        }
        else
        {
            if(leftS!=null)
            {
                leftS.setvInfo(leftS.getTl(), parent.getvInfo(posParent-1));
                leftS.setvPos(leftS.getTl(), parent.getvPos(posParent-1));
                leftS.setTl(leftS.getTl()+1);
                parent.rearrangeExclusion(posParent-1);
                parent.setTl(parent.getTl()-1);
                parent.setvLig(posParent-1, leftS);
                for(int i=0; i<leaf.getTl(); i++)
                {
                    leftS.setvInfo(leftS.getTl(), leaf.getvInfo(i));
                    leftS.setvPos(leftS.getTl(), leaf.getvPos(i));
                    leftS.setvLig(leftS.getTl(), leaf.getvLig(i));
                    leftS.setTl(leftS.getTl()+1);
                }
                leftS.setvLig(leftS.getTl(), leaf.getvLig(leaf.getTl()));
            }
            else
            {
                rightS.rearrange(0);
                rightS.setvInfo(0, parent.getvInfo(posParent));
                rightS.setvPos(rightS.getTl(), parent.getvPos(posParent));
                rightS.setTl(rightS.getTl()+1);
                rightS.setvLig(0, leaf.getvLig(leaf.getTl()));
                parent.rearrangeExclusion(posParent);
                parent.setTl(parent.getTl()-1);
                for (int i = leaf.getTl(); i > 0 ; i--) {
                    rightS.rearrange(0);
                    rightS.setvInfo(0, leaf.getvInfo(leaf.getTl()-1));
                    rightS.setvPos(0, leaf.getvPos(leaf.getTl()-1));
                    rightS.setvLig(0, leaf.getvLig(leaf.getTl()-1));
                    rightS.setTl(rightS.getTl()+1);
                }
            }

            if(root==parent && parent.getTl()==0)
            {
                if(leftS!=null)
                    root=leftS;
                else
                    root=rightS;
            }
            else
            if(root!=parent && parent.getTl()< Node.m)
            {
                leaf=parent;
                redistributeConcatenate(leaf);
            }
        }
    }

    public void remove(int info)
    {
        Node subL, subR;
        Node leaf = locateNode(info);
        if (leaf!=null)
        {
            int pos = leaf.getPosition(info);

            leaf.rearrangeExclusion(pos);
            leaf.setTl(leaf.getTl()-1);

            if(leaf == root && leaf.getTl()==0)
                root = null;
            else
            if(leaf != root && leaf.getTl()< (int)Math.ceil((double)(Node.m)/2)-1)
                redistributeConcatenate(leaf);
        }
    }
}
