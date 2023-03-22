package eus.ehu.bum4_restapi.model;

public class Emojis {

    private String shortcode;
    private String url;
    private String static_url;
    private boolean visible_in_picker;
    private String category;

    public Emojis(String shortcode, String url, String static_url, boolean visible_in_picker, String category){
        this.shortcode = shortcode;
        this.url = url;
        this.static_url = static_url;
        this.visible_in_picker = visible_in_picker;
        this.category = category;
    }
}
