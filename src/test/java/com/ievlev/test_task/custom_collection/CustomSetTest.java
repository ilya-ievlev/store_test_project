package com.ievlev.test_task.custom_collection;

import com.ievlev.test_task.exceptions.ObjectDuplicatesInCollectionException;
import com.ievlev.test_task.initializer.IntegrationTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class CustomSetTest extends IntegrationTestBase {

    @Test
    public void customSetMustThrowExceptionIfUserAddsItemThatIsAlreadyPresentInSet() {
        CustomSet<String> stringCustomSet = new CustomSet<>();
        String helloString = "hello";
        stringCustomSet.add(helloString);
        Assertions.assertThrows(ObjectDuplicatesInCollectionException.class, () -> stringCustomSet.add(helloString));
    }
}
