package fiap.tech.challenge.restaurant_manager.exceptions.custom;

public class InvalidMenuItemException extends RuntimeException{

    public InvalidMenuItemException() {
        super("O item de menu não pode ser nulo ou vazio!");
    }

    	public InvalidMenuItemException(Long menuId) {
        super("Item de ID " + menuId + " não encontrado.");
    }

}
