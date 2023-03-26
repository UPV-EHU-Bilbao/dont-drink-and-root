/*
 * This file is part of the BUM4_REST-API project.
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

package eus.ehu.bum4_restapi.model;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Account {
    String id;
    String username;
    String acct;
    String display_name;
    boolean locked;
    boolean bot;
    String created_at;
    String note;
    String url;
    String avatar;
    String avatar_static;
    String header;
    String header_static;
    int followers_count;
    int following_count;
    int statuses_count;
    String last_status_at;
    ArrayList<Emojis> emojis;
    ArrayList<Field> fields;

    public Account(String id, String username, String acct, String display_name, boolean locked, boolean bot, String created_at, String note, String url, String avatar, String avatar_static, String header, String header_static, int followers_count, int following_count, int statuses_count, String last_status_at, ArrayList<Emojis> emojis, ArrayList<Field> fields){
        this.id = id;
        this.username = username;
        this.acct = acct;
        this.display_name = display_name;
        this.locked = locked;
        this.bot = bot;
        this.created_at = created_at;
        this.note = note;
        this.url = url;
        this.avatar = avatar;
        this.avatar_static = avatar_static;
        this.header = header;
        this.header_static = header_static;
        this.followers_count = followers_count;
        this.following_count = following_count;
        this.statuses_count = statuses_count;
        this.last_status_at = last_status_at;
        this.emojis = emojis;
        this.fields = fields;
    }

    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", acct='" + acct + '\'' +
                ", display_name='" + display_name + '\'' +
                ", locked=" + locked +
                ", bot=" + bot +
                ", created_at='" + created_at + '\'' +
                ", note='" + note + '\'' +
                ", url='" + url + '\'' +
                ", avatar='" + avatar + '\'' +
                ", avatar_static='" + avatar_static + '\'' +
                ", header='" + header + '\'' +
                ", header_static='" + header_static + '\'' +
                ", followers_count=" + followers_count +
                ", following_count=" + following_count +
                ", statuses_count=" + statuses_count +
                ", last_status_at='" + last_status_at + '\'' +
                '}';
    }

    public String getAvatar(){
        return avatar;
    }

    public String getUsername(){
        return username;
    }

    public String getDisplay_name(){
        return display_name;
    }

    public int getFollowersCount(){
        return followers_count;
    }

    public int getFollowingCount(){
        return following_count;
    }
}
