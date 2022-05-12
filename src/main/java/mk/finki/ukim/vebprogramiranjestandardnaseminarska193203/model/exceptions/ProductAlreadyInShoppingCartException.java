package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.exceptions;

public class ProductAlreadyInShoppingCartException extends RuntimeException{
    public ProductAlreadyInShoppingCartException(Long id){
        super("Product with id: "+id+" already exists in shopping cart");
    }
}
