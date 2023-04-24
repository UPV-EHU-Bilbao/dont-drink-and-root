/*
 * This file is part of the MASTODONFX-RESTAPI project.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @authors - Geru-Scotland (Basajaun) | Github: https://github.com/geru-scotland
 *          - Unai Salaberria          | Github: https://github.com/unaisala
 *          - Martin Jimenez           | Github: https://github.com/Matx1n3
 *          - Iñaki Azpiroz            | Github: https://github.com/iazpiroz15
 *          - Diego Forniés            | Github: https://github.com/DiegoFornies
 *
 */

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
