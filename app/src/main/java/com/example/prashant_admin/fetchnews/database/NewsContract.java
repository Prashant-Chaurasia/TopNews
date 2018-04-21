package com.example.prashant_admin.fetchnews.database;

import android.provider.BaseColumns;

public class NewsContract {

    public static final class NewsEntry implements BaseColumns{
        public static final String TABLE_NAME = "News";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URL_TO_IMAGE = "urlToImage";
        public static final String COLUMN_PUBLISHED_AT = "publishedAT";

    }
}
