package com.nixsolutions.laba4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import interfaces.task4.CollectionUtils;

public class CollectionUtilsImpl implements  CollectionUtils {

    public CollectionUtilsImpl(){}

    @Override
    public Collection<Integer> difference(Collection<Integer> collection,
            Collection<Integer> collection1) {
        if (collection == null || collection1 == null) {
            throw new NullPointerException();
        }
        List<Integer> result = new ArrayList<>(
        collection.size() + collection1.size());
        result.addAll(collection);
        result.removeAll(collection1);

        return result ;
    }


    @Override
    public List<Integer> intersection(Collection<Integer> collection,
            Collection<Integer> collection1) {
        if (collection == null || collection1 == null) {
            throw new NullPointerException();
        }        
        List<Integer> result = new ArrayList<>(
        collection.size() + collection1.size());
        result.addAll(collection);
        result.retainAll(collection1);

        return result;

    }

    @Override
    public Set<Integer> intersectionWithoutDuplicate(Collection<Integer> collection,
            Collection<Integer> collection1) {
        if (collection == null || collection1 == null) {
            throw new NullPointerException();
        }        
        HashSet<Integer> result = new HashSet<>(
        collection.size() + collection1.size());
        result.addAll(collection);
        result.retainAll(collection1);
        
        return result;
    }

    @Override

    public List<Integer> union(Collection<Integer> collection,
            Collection<Integer> collection1) {
        if (collection == null || collection1 == null) {
            throw new NullPointerException();
        }

        List<Integer> result = new ArrayList<>(
        collection.size() + collection1.size());
        result.addAll(collection);
        result.addAll(collection1);

        return result;
    }


    
    public static void main(String[] args) {
        CollectionUtils coll = new CollectionUtilsImpl();

        Collection<Integer> collection = new ArrayList<>(
                Arrays.asList(
                        new Integer[] { 0, 1 ,2 ,3, 3 ,4 }));

        Collection<Integer> collection1 = new ArrayList<>(
                Arrays.asList(
                        new Integer[] { 3, 4 ,5, 6 , 7 }));;

        System.out.println(coll.union(collection, collection1));
        System.out.println(coll.intersection(collection, collection1));
        System.out.println(coll.intersectionWithoutDuplicate(collection, collection1));
        System.out.println(coll.difference(collection, collection1));



    }

}
