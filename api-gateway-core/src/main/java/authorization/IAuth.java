package authorization;

public interface IAuth {
    boolean validate(String id, String token);
}
