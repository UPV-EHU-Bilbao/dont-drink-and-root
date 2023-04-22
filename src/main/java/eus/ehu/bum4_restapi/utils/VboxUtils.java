package eus.ehu.bum4_restapi.utils;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class VboxUtils {
    public static <S, T> void mapByValue(
            ObservableList<S> sourceList,
            ObservableList<T> targetList,
            Function<S, T> mapper) {
        Objects.requireNonNull(sourceList);
        Objects.requireNonNull(targetList);
        Objects.requireNonNull(mapper);
        targetList.clear();
        Map<S, T> sourceToTargetMap = new HashMap<>();
        // Populate targetList by sourceList and mapper
        for (S s : sourceList) {
            T t = mapper.apply(s);
            targetList.add(t);
            sourceToTargetMap.put(s, t);
        }
        // Listen to changes in sourceList and update targetList accordingly
        ListChangeListener<S> sourceListener = new ListChangeListener<S>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends S> c) {
                while (c.next()) {
                    if (c.wasPermutated()) {
                        for (int i = c.getFrom(); i < c.getTo(); i++) {
                            int j = c.getPermutation(i);
                            S s = sourceList.get(j);
                            T t = sourceToTargetMap.get(s);
                            targetList.set(i, t);
                        }
                    } else {
                        for (S s : c.getRemoved()) {
                            T t = sourceToTargetMap.get(s);
                            targetList.remove(t);
                            sourceToTargetMap.remove(s);
                        }
                        int i = c.getFrom();
                        for (S s : c.getAddedSubList()) {
                            T t = mapper.apply(s);
                            targetList.add(i, t);
                            sourceToTargetMap.put(s, t);
                            i += 1;
                        }
                    }
                }
            }
        };
    }
}
