public class ArrayDeque<Type> {
    public int size;
    public int nextFirst;
    public int nextLast;
    public double usage;
    public Type[] items;

    public ArrayDeque() {
        items = (Type[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
        usage = 0;
    }

    public void addFirst(Type x) {
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

    public void addLast(Type x) {
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

    public Type removeFirst() {
        if(usage <= 0.25 && items.length >= 16) {
            becomeSmaller();
        }

        Type removeItem;
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

    public Type removeLast() {
        if(usage <= 0.25 && items.length >= 16) {
            becomeSmaller();
        }

        Type removeItem;
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

    public Type get(int index) {
        return items[index];
    }

    public int size() {
        return size;
    }

    public void becomeLarger() {
        Type[] newArray = (Type[]) new Object[size * 2];
        System.arraycopy(items, 0, newArray, 1, size);
        nextFirst = 0;
        nextLast = items.length + 1;
        items = newArray;
    }

    public void becomeSmaller() {
        Type[] newArray = (Type[]) new Object[items.length / 2];
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

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        for (Type x : items) {
            System.out.print(x + " ");
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> NewList = new ArrayDeque<>();
        NewList.addFirst(1);
        NewList.addFirst(2);
        NewList.addFirst(3);
        NewList.removeFirst();
        NewList.removeFirst();
        NewList.printDeque();
    }
}