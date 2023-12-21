package com.ievlev.test_task.service;


import com.ievlev.test_task.dto.AvailableItemDto;
import com.ievlev.test_task.exceptions.ItemNotFoundException;
import com.ievlev.test_task.exceptions.UnableToUpdateEntityException;
import com.ievlev.test_task.model.AvailableItem;
import com.ievlev.test_task.repository.AvailableItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class AvailableItemServiceTest {

    @Mock
    private AvailableItemRepository availableItemRepository;
    @InjectMocks
    private AvailableItemService availableItemService;


    @Test
    public void findByIdShouldThrowValidationExceptionIfIdLessThanOne() {
        // TODO: 01-Dec-23 create class that runs validator
        assertThrows(ConstraintViolationException.class, () -> availableItemService.findById(-1));
    }

    @Test
    public void findByIdShouldReturnFullItemOptionalIfItemAvailable() {
        Optional<AvailableItem> availableItemOptional = Optional.of(new AvailableItem("item1", 123, 123));
        Mockito.doReturn(availableItemOptional).when(availableItemRepository).findById(1L);
        assertThat(availableItemService.findById(1L).isPresent());
    }

    @Test
    public void findByIdShouldEmptyItemOptionalIfItemNotAvailable() {
        Optional<AvailableItem> availableItemOptional = Optional.empty();
        Mockito.doReturn(availableItemOptional).when(availableItemRepository).findById(Long.MAX_VALUE);
        assertThat(availableItemService.findById(Long.MAX_VALUE).isEmpty());
    }

    @Test
    public void getByIdThrowExceptionIfAvailableItemNotFound() {
        Optional<AvailableItem> availableItemOptional = Optional.empty();
        Mockito.doReturn(availableItemOptional).when(availableItemRepository).findById(Long.MAX_VALUE);
        Assertions.assertThrows(ItemNotFoundException.class, () -> availableItemService.getById(Long.MAX_VALUE));
    }

    @Test
    public void getByIdReturnItemIfAvailable() {
        Optional<AvailableItem> availableItemOptional = Optional.of(new AvailableItem("item1", 123, 123));
        Mockito.doReturn(availableItemOptional).when(availableItemRepository).findById(1L);
        assertThat(availableItemService.findById(1).isPresent());
    }

    @Test
    public void saveMethodCallWithoutItemIdEvenIfPassedWithId() {
        availableItemService.saveNew(new AvailableItemDto(1L, "item2", 123, 123));
        ArgumentCaptor<AvailableItem> availableItemArgumentCaptor = ArgumentCaptor.forClass(AvailableItem.class);
        Mockito.verify(availableItemRepository).save(availableItemArgumentCaptor.capture());
        AvailableItem availableItem = availableItemArgumentCaptor.getValue();
        assertThat(availableItem.getId() == null
                && availableItem.getName().equals("item2"));
    }

    @Test
    public void saveNewShouldThrowExceptionIfArgumentIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> availableItemService.saveNew(null));
    }

    // TODO: 02-Dec-23 test that checks validation of AvailableItemDto in saveNew

    @Test
    public void updateThrowsExceptionIfItemWithSameIdNotExistsInDb() {
        Mockito.doReturn(false).when(availableItemRepository).existsById(1L);
        AvailableItem availableItem = new AvailableItem(1L, "item1", 123, 123);
        Assertions.assertThrows(UnableToUpdateEntityException.class, () -> availableItemService.update(availableItem));
    }

    @Test
    public void updateThrowsExceptionIfParamIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> availableItemService.update(null));
    }

    @Test
    public void updateThrowsExceptionIfItemIdIsNull() {
        AvailableItem availableItem = new AvailableItem("item1", 123, 123);
        Assertions.assertThrows(UnableToUpdateEntityException.class, () -> availableItemService.update(availableItem));
    }

    @Test
    public void getAllAvailableItemsMustPutAllAvailableItemsFromRepositoryToList() {
        AvailableItem availableItem1 = new AvailableItem(1L, "item1", 123, 123);
        AvailableItem availableItem2 = new AvailableItem(2L, "item2", 123, 123);
        List<AvailableItem> availableItemList = List.of(availableItem1, availableItem2);
        PageRequest pageRequest = PageRequest.of(0, 100);
        Mockito.doReturn(availableItemList).when(availableItemRepository).getAllByQuantityIsGreaterThan(0, pageRequest);
        List<AvailableItemDto> listToCheck = availableItemService.getAllAvailableItems(0, 100);
        assertThat(listToCheck.size() == 2
                && listToCheck.get(0).getName().equals("item1")
                && listToCheck.get(1).getName().equals("item2"));
    }
}
