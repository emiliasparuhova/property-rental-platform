package individual.individualprojectbackend.configuration.security.token;


public interface AccessToken {
    String getSubject();

    Long getUserId();

    String getRole();

    boolean hasRole(String roleName);
}
