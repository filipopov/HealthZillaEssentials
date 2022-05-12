package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions;

public class NoCategoryFoundException extends RuntimeException{
    public NoCategoryFoundException(){
        super("Одберете барем една категорија");
    }
}
