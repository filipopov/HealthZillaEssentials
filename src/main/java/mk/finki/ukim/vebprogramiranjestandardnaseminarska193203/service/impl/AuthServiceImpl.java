package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.impl;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.InvalidArgumentsException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.InvalidUserCredentialsException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions.PasswordsDoNotMatchException;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.repository.UserRepository;
import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        if(username==null || username.isEmpty() || password==null || password.isEmpty())
            throw new InvalidArgumentsException();
        return this.userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}
