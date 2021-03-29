public class LinkedListDeque<Type> {
    public class StuffNode {
        public Type first;
        public StuffNode next;
        private StuffNode prev;

        public StuffNode(Type x, StuffNode p, StuffNode n) {
            first = x;
            prev = p;
            next = n;
        }

    }

    private StuffNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new StuffNode(null, null,null); //先做个全空的，再给指针，不然会有type的问题，以及空指针的问题
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    //一定要以多个项的情况来设想
    public void addFirst(Type x) {
        sentinel.next = new StuffNode(x, sentinel, sentinel.next); //新node的prev指向sentinel，next指向原来的sentinel.next
        sentinel.next.next.prev = sentinel.next; //原有node的prev指向新加的这个node
        size += 1;
    }

    public void addLast(Type x) {
        sentinel.prev.next = new StuffNode(x, sentinel.prev, sentinel); //原有node的next指向新node，新node的prev指向原node，next指向sentinel
        sentinel.prev = sentinel.prev.next; //sentinel的prev原来指向原有的node，改成指向新node
        size += 1;
    }

    //两种方式，一种先弄后一项的prev指针，一种先弄next指针
    public Type removeFirst() {
        if (size == 0) {
            return null;
        }
        Type item = sentinel.next.first;
        sentinel.next = sentinel.next.next; //指向第一个的指针指向下一个
        sentinel.next.prev = sentinel; //此使sentinel.next已经变成原来的下一个了，直接prev指回sentinel就好
        size -= 1;
        return item;
    }

    public Type removeLast() {
        if (size == 0) {
            return null;
        }
        Type item = sentinel.prev.first;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return item;
    }

    public Type getIterative(int index) {
        StuffNode p = sentinel.next;
        if (index > size) {
            return null;
        }
        while (index != 0) {
            p = p.next;
            index -= 1;
        }
        return p.first;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0){
            return true;
        } else {
            return false;
        }
    }

    public Type getRecursive(int index) {
        int num = size;
        if (index > num - 1) {
            return null;
        } else {
            return helper(sentinel.next, index);
        }
    }

    public Type helper(StuffNode p, int i){
        if (i == 0) {
            return p.first;
        } else {
            return helper(p.next, i - 1);
        }
    }

    public void printDeque() {
        int num = size;
        StuffNode p = sentinel.next;
        while (num != 0) {
            System.out.print(p.first + " ");
            p = p.next;
            num -= 1;
        }
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> Dllist = new LinkedListDeque<>();
        Dllist.addFirst(666);
        Dllist.addLast(6666);
        Dllist.addLast(66666);
        Dllist.printDeque();                        // expected (666 6666 66666)
        System.out.println("Test getIterative #1");
        System.out.println(Dllist.getIterative(0)); // expected 666
        System.out.println(Dllist.getIterative(1)); // expected 6666
        System.out.println(Dllist.getIterative(5)); // expected null
        System.out.println("Test getIterative #1");
        System.out.println(Dllist.getRecursive(0)); // expected 666
        System.out.println(Dllist.getRecursive(1)); // expected 6666
        System.out.println("Test done!");

        Dllist.removeFirst();
        Dllist.printDeque();                        // expected (6666 66666)
        System.out.println("Test getIterative #2 removeFirst");
        System.out.println(Dllist.getIterative(0)); // expected 6666
        System.out.println(Dllist.getIterative(1)); // expected 66666
        System.out.println("Test getRecursive #2 removeFirst");
        System.out.println(Dllist.getRecursive(0)); // expected 6666
        System.out.println(Dllist.getRecursive(1)); // expected 66666

        Dllist.removeLast();
        Dllist.printDeque();                        // expected 6666
        System.out.println("Test getIterative #3 removeLast");
        System.out.println(Dllist.getIterative(0)); // expected 6666
        System.out.println(Dllist.getIterative(1)); // expected null
        System.out.println("Test getRecursive #3 removeFirst");
        System.out.println(Dllist.getRecursive(0)); // expected 6666
        System.out.println(Dllist.getRecursive(1)); // expected null
    }

}