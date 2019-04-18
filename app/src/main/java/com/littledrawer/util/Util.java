package com.littledrawer.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 土小贵
 * @date 2019/4/18 20:02
 */
public class Util {

    public static Date getDate(long date) {
        return new Timestamp(date);
    }

    public static NewsColumn getNewsColumn(String colName) {
        NewsColumn column = null;

        if (NewsColumn.SOCIAL.columnName.equals(colName)) {
            column = NewsColumn.SOCIAL;
        } else if (NewsColumn.SCIENCE.columnName.equals(colName)) {
            column = NewsColumn.SCIENCE;
        } else if (NewsColumn.LIFE.columnName.equals(colName)) {
            column = NewsColumn.LIFE;
        } else if (NewsColumn.ENTERTAINMENT.columnName.equals(colName)) {
            column = NewsColumn.ENTERTAINMENT;
        } else if (NewsColumn.AGRICULTURAL.columnName.equals(colName)) {
            column = NewsColumn.AGRICULTURAL;
        }else if (NewsColumn.INTERNATIONAL.columnName.equals(colName)) {
            column = NewsColumn.INTERNATIONAL;
        } else if (NewsColumn.SPORTS.columnName.equals(colName)) {
            column = NewsColumn.SPORTS;
        }

        return column;
    }
}
