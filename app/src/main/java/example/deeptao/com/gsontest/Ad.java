package example.deeptao.com.gsontest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by deeptaco on 2/29/16.
 */
public class Ad {
    @JsonProperty("id")
    int id;
    @JsonProperty("ad_name")
    String name;
    @JsonProperty("ad_text")
    String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}