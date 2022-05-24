package com.epam.esm.dao.constant;

final public class TableNames {
    abstract public static class Certificate{
        public static final String TABLE_NAME = "gift_certificate";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
        public static final String DURATION = "duration";
        public static final String CREATE_DATE = "create_date";
        public static final String LAST_UPDATE_DATE = "last_update_date";
    }
    final public static class Tag{
        public static final String TABLE_NAME = "tag";
        public static final String ID = "id";
        public static final String NAME = "name";
    }
    final public static class CertificateTag{
        public static final String TABLE_NAME = "gift_certificate_tag";
        public static final String CERTIFICATE_ID = "gift_certificate_id";
        public static final String TAG_ID = "tag_id";
    }
}
