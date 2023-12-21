package com.ievlev.test_task.service;

import com.ievlev.test_task.dto.AvailableItemDto;
import com.ievlev.test_task.exceptions.ItemNotFoundException;
import com.ievlev.test_task.exceptions.UnableToUpdateEntityException;
import com.ievlev.test_task.model.AvailableItem;
import com.ievlev.test_task.repository.AvailableItemRepository;
import com.ievlev.test_task.util.AvailableItemDtoConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//@Validated
public class AvailableItemService {
    private static final String AVAILABLE_ITEM_DTO_CAN_T_BE_NULL = "availableItemDto can't be null";
    private static final String YOU_CAN_T_UPDATE_ITEM_WITHOUT_ID = "you can't update item without id";
    private static final String CAN_T_FIND_ITEM_WITH_ID_D = "can't find item with id:%d";
    private static final String AVAILABLE_ITEM_CAN_T_BE_NULL = "availableItem can't be null";
    private static final String YOU_CAN_T_UPDATE_ITEM_THAT_IS_NOT_IN_DATABASE = "you can't update item that is not in database";
    private final AvailableItemRepository availableItemRepository;

    public Optional<AvailableItem> findById(long availableItemId) {
        return availableItemRepository.findById(availableItemId);
    }

    public AvailableItemDto getById(long availableItemId) {
        return AvailableItemDtoConverterUtil.itemDtoFromAvailableItemWithId(availableItemRepository.findById(availableItemId)
                .orElseThrow(() -> new ItemNotFoundException(String.format(CAN_T_FIND_ITEM_WITH_ID_D, availableItemId))));
    }

    public void saveNew(AvailableItemDto availableItemDto) {
        if (availableItemDto == null) {
            throw new IllegalArgumentException(AVAILABLE_ITEM_CAN_T_BE_NULL);
        }
        availableItemRepository.save(AvailableItemDtoConverterUtil.availableItemFromItemDtoWithoutId(availableItemDto));
    }

    public void update(AvailableItem availableItem) {
        if (availableItem == null) {
            throw new IllegalArgumentException(AVAILABLE_ITEM_DTO_CAN_T_BE_NULL);
        }
        if (availableItem.getId() == null) {
            throw new UnableToUpdateEntityException(YOU_CAN_T_UPDATE_ITEM_WITHOUT_ID);
        }
        if (availableItemRepository.existsById(availableItem.getId())) {
            availableItemRepository.save(availableItem);
        } else {
            throw new UnableToUpdateEntityException(YOU_CAN_T_UPDATE_ITEM_THAT_IS_NOT_IN_DATABASE);
        }

    }

    public List<AvailableItemDto> getAllAvailableItems(int pageNumber, int pageSize) {
        List<AvailableItem> availableItems = availableItemRepository.getAllByQuantityIsGreaterThan(0, PageRequest.of(pageNumber, pageSize));
        List<AvailableItemDto> availableItemDtoList = new ArrayList<>();
        for (AvailableItem availableItem : availableItems) {
            availableItemDtoList.add(AvailableItemDtoConverterUtil.itemDtoFromAvailableItemWithId(availableItem));
        }
        return availableItemDtoList;
    }

}
