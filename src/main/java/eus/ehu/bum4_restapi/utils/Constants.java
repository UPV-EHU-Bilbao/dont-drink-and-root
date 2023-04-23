/*
 * This file is part of the Project-MastodonFX project.
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

public enum Constants {

    BASE_PATH("base.path"),

    /**
     * API calls
     */
    API_BASE("https://mastodon.social/api/v1/"),
    ACCOUNTS("accounts/"),

    ENDPOINT_FOLLOWERS("/followers"),
    ENDPOINT_FOLLOWING("/following"),
    ENDPOINT_STATUSES("/statuses"),
    ENDPOINT_FAVOURITES("/favourites"),
    ENDPOINT_MARK_TOOT_AS_FAV("/favourite"),
    ENDPOINT_MARK_TOOT_AS_NOT_FAV("/unfavourite"),

    /**
     * Views
     */
    USER_FOLLOWERS_VIEW("user.followers.view"),
    USER_FOLLOWING_VIEW("user.following.view"),
    USER_FRAME_VIEW("user.frame.view"),
    USER_TOOTS_VIEW("user.toots.view"),
    USER_FAVOURITE_TOOTS_VIEW("user.favourite-toots.view"),

    APP_VIEW("app.view"),

    /**
     * Test users
     */
    USER_GERU("userid.geru"),
    USER_JUANAN("userid.juanan"),

    /**
     * Persistent data
     */
    CURRENT_TOOT("currenttoot"),

    /**
     * Placeholders
     */
    PLACEHOLDER_ACCOUNT("${accountID}");

    private final String key;

    Constants(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString(){ return key; }
}
