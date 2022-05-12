package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ServletComponentScan
public class VebProgramiranjeStandardnaSeminarska193203Application {

    public static void main(String[] args) {
        SpringApplication.run(VebProgramiranjeStandardnaSeminarska193203Application.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
