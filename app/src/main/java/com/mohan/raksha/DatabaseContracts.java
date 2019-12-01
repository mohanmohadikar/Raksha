package com.mohan.raksha;

import android.provider.BaseColumns;

public class DatabaseContracts{

    private DatabaseContracts(){}


    public static final class ContactsEntry implements BaseColumns {

        public static final String TABLE_NAME = "contact_list";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_TIMESTAMP = "time_stamp";
    }
}

