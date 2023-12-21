package com.ievlev.test_task.controller;

import com.ievlev.test_task.dto.AvailableItemDto;
import com.ievlev.test_task.service.AvailableItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

//@Validated
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final AvailableItemService availableItemService;

    @GetMapping("/api/v1/secured/items")
    public List<AvailableItemDto> getAllAvailableItems(@RequestParam @Min(0) int pageNumber, @RequestParam @Min(0) int pageSize) {
        return availableItemService.getAllAvailableItems(pageNumber, pageSize);
    }

    @PutMapping("/api/v1/admin/items")
    public void createNewItem(@RequestBody AvailableItemDto availableItemDto) {
        availableItemService.saveNew(availableItemDto);
    }

    @GetMapping("/api/v1/secured/items/{id}")
    public AvailableItemDto getItemById(@PathVariable
//                                            @Min(1)
                                                    long id) {
        return availableItemService.getById(id);
    }
}