package services;

import api.*;
import api.data.SingleProjectData;
import datalayer.datamappers.user.UserMapper;
import domain.Bid;
import domain.Project;
import domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;

import static services.SignupService.generateHash;

public class LoginService {
    private UserMapper userMapper = new UserMapper();
    public static final String SALT = "MilkyMocha";

    public LoginService() throws SQLException {
    }

    public String handleLoginRequest(LoginRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        models.User user = this.userMapper.findByUsername(request.getUsername());
        if (user == null) {
            FailResponse<String> failResponse = new FailResponse<>("username or password is incorrect");
            response.setStatus(403);
            return failResponse.toJSON();
        }

        if(!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            FailResponse<String> failResponse = new FailResponse<>("username or password is incorrect");
            response.setStatus(403);
            return failResponse.toJSON();
        }

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws = Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuer("jobounja")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofDays(100))))
                .signWith(key)
                .compact();

        LoginResponse loginResponse = new LoginResponse(jws);
        return loginResponse.toJSON();
    }

    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
