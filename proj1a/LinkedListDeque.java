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
        sentinel = new StuffNode(null, null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(Type x) {
        sentinel.next = new StuffNode(x, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(Type x) {
        sentinel.prev.next = new StuffNode(x, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }

    public Type removeFirst() {
        if (size == 0) {
            return null;
        }
        Type item = sentinel.next.first;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return item;
    }

    public Type removeLast() {
        if (size == 0) {
            return null;
        }
        Type item = sentinel.prev.first;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
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