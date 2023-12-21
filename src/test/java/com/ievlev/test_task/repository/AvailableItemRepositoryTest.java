package com.ievlev.test_task.repository;

import com.ievlev.test_task.initializer.IntegrationTestBase;
import com.ievlev.test_task.model.AvailableItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
public class AvailableItemRepositoryTest extends IntegrationTestBase {
    private final AvailableItemRepository availableItemRepository;

    @Autowired
    public AvailableItemRepositoryTest(AvailableItemRepository availableItemRepository) {
        this.availableItemRepository = availableItemRepository;
    }

    @Sql("classpath:sql/data.sql")
    @Test
    public void getAllByQuantityIsGreaterThanShouldReturnQuantityBiggerThat0() {
        List<AvailableItem> availableItemList = availableItemRepository.getAllByQuantityIsGreaterThan(0, PageRequest.of(0, 100));
        assertThat(availableItemList.size() == 1 && availableItemList.get(0).getQuantity() == 1);
    }
}
