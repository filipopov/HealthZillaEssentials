package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions;

public class UserWithUsernameAlreadyExistsException extends RuntimeException{
    public UserWithUsernameAlreadyExistsException(){
        super("A user with that username already exists! Chose another username");
    }
}
