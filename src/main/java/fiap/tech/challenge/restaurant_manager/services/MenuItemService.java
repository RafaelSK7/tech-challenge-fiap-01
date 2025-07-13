package fiap.tech.challenge.restaurant_manager.services;

import fiap.tech.challenge.restaurant_manager.entities.MenuItem;
import fiap.tech.challenge.restaurant_manager.repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    private final MenuItemRepository repository;
    public MenuItemController(MenuItemRepository repository) {
        this.repository = repository;
    }

    public List<MenuItem> findAll() {
        return repository.findAll();
    }

    public Optional<MenuItem> findById(Long id) {
        return repository.findById(id);
    }

    public MenuItem save(MenuItem item) {
        return repository.save(item);
    }

    public MenuItem update(Long id, MenuItem updatedItem) {
        return repository.findById(id).map(item -> {
            item.setName(updatedItem.getName());
            item.setDescription(updatedItem.getDescription());
            item.setPrice(updatedItem.getPrice());
            item.setLocalOnly(updatedItem.isLocalOnly());
            item.setPhotoPath(updatedItem.getPhotoPath());
            return repository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
