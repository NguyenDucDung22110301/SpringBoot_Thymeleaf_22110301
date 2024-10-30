package vn.iostar.SpringBoot_st4.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.iostar.SpringBoot_st4.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    long count();
    void deleteById(Long aLong);
    Page<Category> findAll(Pageable pageable);
    List<Category> findAll(Sort sort);
    Optional<Category> findById(Long aLong);
    Optional<Category> findByName(String name);
    Page<Category> findByNameContaining(String name, Pageable pageable);

    <S extends Category> S save(S entity);
}
