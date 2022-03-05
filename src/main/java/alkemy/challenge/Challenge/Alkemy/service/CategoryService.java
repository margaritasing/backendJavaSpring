package alkemy.challenge.Challenge.Alkemy.service;

import alkemy.challenge.Challenge.Alkemy.model.Category;
import alkemy.challenge.Challenge.Alkemy.repository.CategoryRepository;
import alkemy.challenge.Challenge.Alkemy.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    /*Obtener lista de categorias por nombres*/
    public List<String> listCategoriesByName() {
        List<Category> listCategories = categoryRepository.findAll();
        List<String> listCategoriesByname = new ArrayList<>();
        for (Category c : listCategories) {
            listCategoriesByname.add(c.getName());
        }
        return listCategoriesByname;
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findByIdAndDeletedFalse(id);
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public boolean createCategories(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<?> update(Category category, Category categoryAux) {
        categoryAux.setName(category.getName());
        categoryAux.setDescription(category.getDescription());
        categoryAux.setImage(category.getImage());
        categoryRepository.save(categoryAux);
        return new ResponseEntity(new Message("Categoria actualizada"),
                HttpStatus.OK);
    }

    public void delete(Category category) {
        category.setDeleted(true);
        categoryRepository.save(category);
    }
}
