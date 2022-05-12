package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException{
    public PasswordsDoNotMatchException(){
        super("Passwords Do Not Match Exception");
    }
}
