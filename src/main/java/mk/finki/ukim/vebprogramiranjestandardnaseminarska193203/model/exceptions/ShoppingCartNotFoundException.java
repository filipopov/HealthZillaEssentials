package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions;

public class ShoppingCartNotFoundException extends RuntimeException{
    public ShoppingCartNotFoundException(Long id){
        super("Shopping cart with id: "+id+" was not found!");
    }
}
