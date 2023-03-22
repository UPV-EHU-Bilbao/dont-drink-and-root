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

public class Toot {
    private String id;
    private String created_at;
    private String in_reply_to_id;
    private String in_reply_to_account_id;
    private boolean sensitive;
    private String spoiler_text;
    private String visibility;
    private String language;
    private String uri;
    private String content;
    private int replies_count;
    private int reblogs_count;
    private int favourites_count;
    private String edited_at;
    private Boolean favourited;
    private Boolean muted;
    private Boolean bookmarked;
    private Toot reblog;
    private Account account;

    public Toot(){}

    public String toString() {
        return "<html>" +
                "<b>id:</b> " + id + "<br>" +
                "<b>created_at:</b> " + created_at + "<br>" +
                "<b>in_reply_to_id:</b> " + in_reply_to_id + "<br>" +
                "<b>in_reply_to_account_id:</b> " + in_reply_to_account_id + "<br>" +
                "<b>sensitive:</b> " + sensitive + "<br>" +
                "<b>spoiler_text:</b> " + spoiler_text + "<br>" +
                "<b>visibility:</b> " + visibility + "<br>" +
                "<b>language:</b> " + language + "<br>" +
                "<b>uri:</b> " + uri + "<br>" +
                "<b>content:</b> " + content + "<br>" +
                "<b>replies_count:</b> " + replies_count + "<br>" +
                "<b>reblogs_count:</b> " + reblogs_count + "<br>" +
                "<b>favourites_count:</b> " + favourites_count + "<br>" +
                "<b>edited_at:</b> " + edited_at + "<br>" +
                "<b>favourited:</b> " + favourited + "<br>" +
                "<b>muted:</b> " + muted + "<br>" +
                "<b>bookmarked:</b> " + bookmarked + "<br>" +
                "<b>reblog:</b> " + reblog + "<br>" +
                "<b>account:</b> " + account.toString() + "<br>" +
                "</html>";
    }

    public String getUsername(){
        return account.getUsername();
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getInReplyToId() {
        return in_reply_to_id;
    }

    public String getInReplyToAccountId() {
        return in_reply_to_account_id;
    }

    public boolean isSensitive() {
        return sensitive;
    }

    public String getSpoilerText() {
        return spoiler_text;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getLanguage() {
        return language;
    }

    public String getUri() {
        return uri;
    }

    public String getContent() {
        return content;
    }

    public int getRepliesCount() {
        return replies_count;
    }

    public int getReblogsCount() {
        return reblogs_count;
    }

    public int getFavouritesCount() {
        return favourites_count;
    }

    public String getEditedAt() {
        return edited_at;
    }

    public Boolean isFavourited() {
        return favourited;
    }

    public Boolean isMuted() {
        return muted;
    }

    public Boolean isBookmarked() {
        return bookmarked;
    }

    public Toot getReblog() {
        return reblog;
    }

    public Account getAccount() {
        return account;
    }

    public Boolean isReblog(){ return reblog != null; }
}
