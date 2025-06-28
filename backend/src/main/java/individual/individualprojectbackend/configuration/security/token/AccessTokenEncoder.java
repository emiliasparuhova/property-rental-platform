package individual.individualprojectbackend.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
