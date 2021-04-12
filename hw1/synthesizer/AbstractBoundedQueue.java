package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue{

    protected int fillCount;
    protected int capacity;
    public int capacity() {
        return capacity;
    }
    public int fillCount() {
        return fillCount;
    }

    public boolean isEmpty() {
        return fillCount == 0;
    }

    public boolean isFull() {
        return fillCount == capacity;
    }
    public abstract T peek();
    public abstract T dequeue() throws Exception;
    public abstract void enqueue(Object x) throws Exception;
    abstract void moveTo(double deltaX, double deltaY);
}
