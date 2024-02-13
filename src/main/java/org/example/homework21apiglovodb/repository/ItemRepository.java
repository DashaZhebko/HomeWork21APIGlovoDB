package org.example.homework21apiglovodb.repository;

import org.example.homework21apiglovodb.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
}