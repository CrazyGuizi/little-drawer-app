package com.littledrawer.util;

/**
 * @author 土小贵
 * @date 2019/4/19 13:11
 */
public enum VideoType {
    FUNNY(0, "搞笑"),
    GAME(1, "游戏"),
    LIFE(2, "生活"),
    FILM(3, "影视"),
    SCIENCE(4, "科技"),
    OTHER(5, "其他");

    public int typeIndex;
    public String typeName;

    VideoType(int typeIndex, String typeName) {
        this.typeIndex = typeIndex;
        this.typeName = typeName;
    }
}
