package todolist.demo.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import todolist.demo.model.UsuarioModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(UsuarioModel usuarioModel){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuarioModel.getNome())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        }
        catch (JWTCreationException exception){
            throw new RuntimeException("Erro gerando token", exception);
        }
    }
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    public String validateToken(String token){
        try{
            Algorithm algorithm= Algorithm.HMAC256(secret);
            return JWT.require((algorithm))
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            System.out.println(exception + " token invalido" + token);
            return "token invalido";
        }
    }
}
