/*
 * This file is part of the dont-drink-and-root project.
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
 * @author - Geru-Scotland (Basajaun)
 * Github: https://github.com/Geru-Scotland
 */

package eus.ehu.bum4_restapi.api;

import eus.ehu.bum4_restapi.model.Toot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MastodonAPITest {

    private MastodonAPI mastodonAPI;
    private Toot toot1;
    private Toot toot2;

    @BeforeEach
    public void setUp() {
        mastodonAPI = new MastodonAPI();
        toot1 = new Toot();
        toot2 = new Toot();
    }

    @Test
    public void testSetTootList() {
        List<Toot> toots = Arrays.asList(toot1, toot2);
        mastodonAPI.setTootList(toots);
        assertEquals(2, mastodonAPI.getTootListSize());
    }

    @Test
    public void testResetTootList() {
        List<Toot> toots = Arrays.asList(toot1, toot2);
        mastodonAPI.setTootList(toots);
        mastodonAPI.resetTootList();
        assertEquals(0, mastodonAPI.getTootListSize());
    }

    @Test
    public void testGetObjectFromList() {
        List<Toot> toots = Arrays.asList(toot1, toot2);
        mastodonAPI.setTootList(toots);
        assertEquals(toot1, mastodonAPI.getObjectFromList(0));
        assertEquals(toot2, mastodonAPI.getObjectFromList(1));
    }

    @Test
    public void testGetObjectFromListThrowsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> mastodonAPI.getObjectFromList(0));
    }

    @Test
    public void testSetTootListReplacesExistingList() {
        List<Toot> toots1 = Arrays.asList(new Toot(), new Toot());
        mastodonAPI.setTootList(toots1);
        List<Toot> toots2 = List.of(new Toot());
        mastodonAPI.setTootList(toots2);
        assertEquals(1, mastodonAPI.getTootListSize());
    }

    @Test
    public void testGetObjectFromListThrowsExceptionForNegativeIndex() {
        List<Toot> toots = Arrays.asList(new Toot(), new Toot());
        mastodonAPI.setTootList(toots);
        assertThrows(IndexOutOfBoundsException.class, () -> mastodonAPI.getObjectFromList(-1));
    }

    @Test
    public void testGetObjectFromListThrowsExceptionForOutOfRangeIndex() {
        List<Toot> toots = Arrays.asList(new Toot(), new Toot());
        mastodonAPI.setTootList(toots);
        assertThrows(IndexOutOfBoundsException.class, () -> mastodonAPI.getObjectFromList(2));
    }

    @Test
    public void testGetFromEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> mastodonAPI.getObjectFromList(0));
    }

    @Test
    public void testGetFromNonEmptyList() {
        mastodonAPI.setTootList(Collections.singletonList(toot1));
        assertEquals(toot1, mastodonAPI.getObjectFromList(0));
    }

    @Test
    public void testListSizeChangesWithSet() {
        mastodonAPI.setTootList(Arrays.asList(toot1, toot2));
        assertEquals(2, mastodonAPI.getTootListSize());
        mastodonAPI.setTootList(Collections.singletonList(toot1));
        assertEquals(1, mastodonAPI.getTootListSize());
    }

    @Test
    public void testSetTootListWithNull() {
        assertThrows(NullPointerException.class, () -> mastodonAPI.setTootList(null));
    }

    @Test
    public void testSetTootListWithContainsNull() {
        assertThrows(NullPointerException.class, () -> mastodonAPI.setTootList(Arrays.asList(toot1, null, toot2)));
    }

}