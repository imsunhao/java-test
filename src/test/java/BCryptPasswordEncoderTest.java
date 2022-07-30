import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptPasswordEncoderTest {

  public static void main(String[] args) {

    PasswordEncoder pe = new BCryptPasswordEncoder();
    String encode = pe.encode("123456");
    System.out.println(encode);
    boolean matches = pe.matches("123456", encode);
    System.out.println("===================================");
    System.out.println(matches);
  }
}