package org.stepacademy.swm_diplom_mvc.model.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface IDaoDB<E> {
    List<E> findAll();

    Optional<E> findById(Integer id);

    E save(E e);

    E update(E e);

    E delete(Integer id);
}
