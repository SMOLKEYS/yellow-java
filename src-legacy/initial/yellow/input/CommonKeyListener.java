package yellow.input;

public interface CommonKeyListener<T>{

    default void update(){
        update(null);
    }

    void update(T t);

    boolean canRemove();

    boolean remove();
}
