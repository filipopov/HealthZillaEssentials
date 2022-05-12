package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.enumerations.Role;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.InvalidArgumentsException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.PasswordsDoNotMatchException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.UserWithUsernameAlreadyExistsException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.UserRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, String email) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty() || name==null || name.isEmpty() ||
                surname==null || surname.isEmpty())
            throw new InvalidArgumentsException();
        if(!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UserWithUsernameAlreadyExistsException();

        User user = new User(username, passwordEncoder.encode(password), name, surname, email);
        this.emailSenderService.sendEmail(email, "Активиран нов кориснички профил", "Добредојдовте во HealthZilla Essentials! Вашиот нов профил е активиран. Можете да продолжите!");
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }
}
