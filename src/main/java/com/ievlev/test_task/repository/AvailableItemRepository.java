package com.ievlev.test_task.repository;

import com.ievlev.test_task.model.AvailableItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@Validated
public interface AvailableItemRepository extends JpaRepository<AvailableItem, Long> {
    List<AvailableItem> getAllByQuantityIsGreaterThan(@Min(0) int quantity, @NotNull Pageable pageable);
}
