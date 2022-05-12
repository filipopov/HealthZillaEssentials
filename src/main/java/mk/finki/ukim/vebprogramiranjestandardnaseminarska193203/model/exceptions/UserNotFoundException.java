package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username){
        super("User "+username+" was not found!");
    }
}
