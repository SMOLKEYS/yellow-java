package yellow.struct;

import arc.struct.*;

/** A {@link Queue} that allows temporarily storing objects removed through {@link #removeFirst()} or {@link #clear()} in a backing queue. */
public class ReversibleQueue<T> extends Queue<T>{
    public Queue<T> removedValues;

    public ReversibleQueue(){
        super();
        removedValues = new Queue<>();
    }

    public ReversibleQueue(int initialSize){
        super(initialSize);
        removedValues = new Queue<>(initialSize);
    }

    public ReversibleQueue(int initialSize, Class<T> type){
        super(initialSize, type);
        removedValues = new Queue<>(initialSize, type);
    }

    @Override
    public T removeFirst(){
        T elem = super.removeFirst();
        removedValues.add(elem);
        return elem;
    }

    @Override
    public void clear(){
        while(size > 0){
            removeFirst();
        }
    }

    /** Moves the last removed object from the backing queue back to the head of this queue.
     * @return last removed object or the first object of this queue if the backing queue is empty */
    @SuppressWarnings("UnusedReturnValue")
    public T revert(){
        if(removedValues.isEmpty()) return first();
        T elem = removedValues.removeLast();
        addFirst(elem);
        return elem;
    }

    /** Moves all removed objects from the backing queue (starting from the tail) back to the head of this queue. Does nothing if the backing queue is empty. */
    public void revertAll(){
        while(!removedValues.isEmpty()) revert();
    }

    /** Flushes the removed objects from the backing queue, shrinks it to free memory, then resizes it to accommodate all the current items in this queue. */
    public void flushBackingQueue(){
        if(removedValues.isEmpty()) return;
        removedValues.clear();
        removedValues.shrink();
        removedValues.ensureCapacity(size);
    }

    public void clearAll(){
        if(!isEmpty()) super.clear();
        if(!removedValues.isEmpty()) removedValues.clear();
    }
}
