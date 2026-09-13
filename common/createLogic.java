package common;

// interface exposed to project creation and task creation
public interface createLogic<T> {
    T create(T entity);
}
