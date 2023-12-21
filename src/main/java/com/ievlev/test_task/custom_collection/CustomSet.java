package com.ievlev.test_task.custom_collection;

import com.ievlev.test_task.exceptions.ObjectDuplicatesInCollectionException;

import java.util.HashSet;

public class CustomSet<E> extends HashSet<E> {
    @Override
    //this method will throw exception to user it user will try to add two identical objects to one request
    public boolean add(E e) {
        if (!super.add(e))
            throw new ObjectDuplicatesInCollectionException("you can't add identical items to one request");
        return true;
    }
}
