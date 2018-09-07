package me.theofrancisco.moneysight;

import android.graphics.Bitmap;

public class Data {

    private String id; // "football/2018/jan/25/worldwide-websites-money-uefa-football-trends"
    private String type; // "article"
    private String sectionId; // "football"
    private String sectionName; // "Football"
    private String webPublicationDate; // "2018-01-25T11:27:14Z"
    private String webTitle; // "Worldwide websites and money, money, money: Uefa’s football trends | Paul MacInnes"
    private String webUrl; // https://www.theguardian.com/football/2018/jan/25/worldwide-websites-money-uefa-football-trends
    private String apiUrl; // https://content.guardianapis.com/football/2018/jan/25/worldwide-websites-money-uefa-football-trends
    private String isHosted; // false
    private String pillarId; // "pillar/sport"
    private String pillarName; //"Sport"
    private String thumbnail; //"https://media.guim.co.uk/ecaa67933a4aa7ac3fdbdcf5c7cd31f0e343b408/0_36_3500_2100/500.jpg"
    private Bitmap bitmap;

    Data() {
        id = "";
        type = "";
        sectionId = "";
        sectionName = "";
        webPublicationDate = "2018-01-30T00:00:00Z";
        webTitle = "";
        webUrl = "";
        apiUrl = "";
        isHosted = "";
        pillarId = "";
        pillarName = "";
        bitmap = null;
    }

    public Data(String sectionId, String sectionName, String webPublicationDate, String webTitle, String webUrl, String pillarName, String thumbnail, Bitmap bitmap) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.pillarName = pillarName;
        this.thumbnail = thumbnail;
        this.bitmap = bitmap;
    }

    //https://content.guardianapis.com/search?q=money&api-key=55518aa4-ff5d-40a5-8ee0-0365060f27ef&show-fields=thumbnail

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    public String getUrl() {
        return webUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }


}
