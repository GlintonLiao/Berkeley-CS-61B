public class ArrayDeque<T> {
    public int size;
    public int nextFirst;
    public int nextLast;
    public double usage;
    public T[] items;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
        usage = 0;
    }

    // New Solution here: improved methods for tracking the nextFirst and nextLast.

    // when nextFirst == 0, call this method, it will move to the last.
    // (0 - 1) % 8 = 7
    // (1 - 1) % 8 = 0
    public int minusOne(int index) {
        return Math.floorMod(index - 1, items.length);
    }

    // method for nextLast
    // (7 + 1) % 8 = 0
    // (6 + 1) % 8 = 7
    public int plusOne(int index) {
        return Math.floorMod(index + 1, items.length);
    }

    public int plusOne(int index, int length) {
        return Math.floorMod(index+1, length);
    }

    public void resizeTrigger() {
        if (usage <= 0.25 && items.length >= 16) {
            resize(items.length / 2);
        } else if (size == items.length) {
            resize(items.length * 2);
        }
    }

    public void resize(int capacity) {
        int start = plusOne(nextFirst); // nextFirst == 0 -> start == 1
        int end = minusOne(nextLast);   // nextLast == 8 -> end == 7
        T[] temp = items;               // 必须有暂存的temp，因为nextLast要基于新的List
        T[] items = (T[]) new Object[capacity];
        nextFirst = 0;
        nextLast = 1;
        for (int i = start; i != end; i = plusOne(i, temp.length)) { //只能用!=，因为end有可能比start小
            items[nextLast] = temp[i];
            nextLast = plusOne(nextLast);
        }
        items[nextLast] = temp[end]; // 当i == end时，手动赋一下值
        nextLast = plusOne(nextLast);
    }

    public void addFirst(T x) {
        resizeTrigger();
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size += 1;
        usage = (double) size / (double) items.length;
    }

    public T getFirst() {
        return items[plusOne(nextFirst)]; // nextFirst will point at current first item
    }

    public void addLast(T x) {
        resizeTrigger();
        items[nextLast] = x;
        nextLast = plusOne(nextLast);
        size += 1;
        usage = (double) size / (double) items.length;
    }

    public T getLast() {
        return items[minusOne(nextLast)];
    }

    public T removeFirst() {
        resizeTrigger();
        T removeItem = getFirst(); //nextFirst is changed
        items[nextFirst] = null;
        size -= 1;
        usage = (double) size / (double) items.length;
        return removeItem;
    }

    public T removeLast(){
        resizeTrigger();
        T removeItem = getLast();
        items[nextLast] = null;
        size -= 1;
        usage = (double) size / (double) items.length;
        return removeItem;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T get(int index) {
        if (index < 0 || index > items.length) {
            return null;
        }
        index = Math.floorMod(plusOne(nextFirst) + index, items.length); // current first item + index
        return items[index];
    }

    public void printDeque() {
        for (T x : items) {
            System.out.print(x + " ");
        }
        System.out.print("\n");
    }


    /*
     * previous solution
     *
    public void addFirst(T x) {
        if (size == items.length) {
            becomeLarger();
        }

        items[nextFirst] = x;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        size += 1;
        usage = (double) size / (double) items.length;
    }

    public void addLast(T x) {
        if (size == items.length) {
            becomeLarger();
        }

        items[nextLast] = x;
        if (nextLast == items.length - 1){
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
        usage = (double) size / (double) items.length;
    }


    public T removeFirst() {
        if(usage <= 0.25 && items.length >= 16) {
            becomeSmaller();
        }

        T removeItem;
        if (nextFirst == items.length - 1) {
            removeItem = items[0];
            items[0] = null;
            nextFirst = 0;
        } else {
            removeItem = items[nextFirst + 1];
            items[nextFirst + 1] = null;
            nextFirst += 1;
        }
        size -= 1;
        usage = (double) size / (double) items.length;
        return removeItem;
    }


    public T removeLast() {
        if(usage <= 0.25 && items.length >= 16) {
            becomeSmaller();
        }

        T removeItem;
        if (nextLast == 0) {
            removeItem = items[items.length - 1];
            items[items.length - 1] = null;
            nextLast = items.length - 1;
        } else {
            removeItem = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast -= 1;
        }
        size -= 1;
        usage = (double) size / (double) items.length;
        return removeItem;
    }

    public T get(int index) {
        return items[index];
    }

    public void becomeLarger() {
        T[] newArray = (T[]) new Object[size * 2];
        System.arraycopy(items, 0, newArray, 1, size);
        nextFirst = 0;
        nextLast = items.length + 1;
        items = newArray;
    }

    public void becomeSmaller() {
        T[] newArray = (T[]) new Object[items.length / 2];
        int startIndex, endIndex;

        if (nextFirst > nextLast) { //指针跑后面去了，先把后面的那部分copy出来
            startIndex = nextFirst + 1;
            endIndex = items.length - 1;
            System.arraycopy(items, startIndex, newArray, 1, endIndex - startIndex + 1);
            System.arraycopy(items, 0, newArray, endIndex - startIndex + 2, size - endIndex + startIndex - 1);
        } else {
            startIndex = nextFirst + 1;
            System.arraycopy(items, startIndex, newArray, 1, size);
        }
        nextFirst = 0;
        nextLast = items.length + 1;
        items = newArray;
    }
    */


    public static void main(String[] args) {
        ArrayDeque<Integer> aq = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            aq.addLast(i);
        }
        aq.printDeque();
        for (int i = 0; i < 98; i++) {
            aq.removeFirst();
        }
        aq.printDeque();
        System.out.println(aq.get(0));
    }
}