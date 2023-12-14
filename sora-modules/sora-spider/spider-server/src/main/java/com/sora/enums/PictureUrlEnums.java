package com.sora.enums;

/**
 * @Classname TargetWebEnums
 * @Description
 * @Date 2023/12/05 16:22
 * @Author by Sora33
 */
public enum PictureUrlEnums {

    WALLHAVEN("https://wallhaven.cc/", null,null),
    HIPPOPX("https://www.hippopx.com/zh/", null,null);

    private final String url;
    private final String[] label;
    private final String attr;

    public PictureUrlEnums of(String url) {
        for (PictureUrlEnums value : PictureUrlEnums.values()) {
            if (value.url.equals(url)) {
                return value;
            }
        }
        return null;
    }


    PictureUrlEnums(String url, String[] label, String attr) {
        this.url = url;
        this.label = label;
        this.attr = attr;
    }

    public String getUrl() {
        return url;
    }

    public String[] getLabel() {
        return label;
    }

    public String getAttr() {
        return attr;
    }
}
