package com.inditex.priceschedulerapi.infrastructure.persistence.repository;

import com.inditex.priceschedulerapi.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for PriceEntity.
 * Provides basic CRUD operations and custom queries.
 */
@Repository
public interface JpaPriceRepositoryAdapter extends JpaRepository<PriceEntity, Long> {
}
