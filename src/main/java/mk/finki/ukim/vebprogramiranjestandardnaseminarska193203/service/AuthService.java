package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.service;

import mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.User;

public interface AuthService {
    User save(User user);
    User login(String username, String password);

}
