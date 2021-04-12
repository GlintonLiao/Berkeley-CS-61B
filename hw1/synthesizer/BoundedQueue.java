package synthesizer;

public interface BoundedQueue<T> {
    int capacity();    // return the size of the buffer
    int fillCount();   // return number of items currently in the buffer
    void enqueue(T x) throws Exception; // add item x to the end
    T dequeue() throws Exception;       // delete and return item from the front
    T peek();          // return(but not delete) item from the front

    default boolean isEmpty() {
        return fillCount() == 0;
    }

    default boolean isFull() {
        return fillCount() == capacity();
    }
}
