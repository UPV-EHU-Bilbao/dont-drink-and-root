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
    void getRequest() {
    }

    @Test
    void postRequest() {
    }

    @Test
    void getFollowers() {
    }

    @Test
    void setTootList() {
    }

    @Test
    void resetTootList() {
    }

    @Test
    void getTootListSize() {
    }

    @Test
    void setFollowersList() {
    }

    @Test
    void setFollowingList() {
    }

    @Test
    void setJSONtoList() {
    }

    @Test
    void getPreviousJSONObject() {
    }

    @Test
    void getNextJSONObject() {
    }

    @Test
    void getObjectFromList() {
    }

    @Test
    void getObjectFromListAsync() {
    }

    @Test
    void getObjectList() {
    }

    @Test
    void getObjectListSize() {
    }

    @Test
    void validateCredentials() {
    }
}