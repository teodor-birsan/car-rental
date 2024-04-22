package Code.Domain;

public interface EntityConverter<T extends Entity> {
    String toString(T object);

    T fromString(String line);
}
