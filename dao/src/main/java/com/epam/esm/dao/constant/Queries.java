package com.epam.esm.dao.constant;

final public class Queries {
    public final static class Certificate{
        public static final String INSERT = "INSERT INTO " + TableNames.Certificate.TABLE_NAME
                + " (" + TableNames.Certificate.NAME + ", "
                + TableNames.Certificate.DESCRIPTION + ", "
                + TableNames.Certificate.PRICE + ", "
                + TableNames.Certificate.CREATE_DATE + ", "
                + TableNames.Certificate.LAST_UPDATE_DATE + ", "
                + TableNames.Certificate.DURATION + ") "
                + " VALUES (?, ?, ?, ?, ?, ?);";
        public static final String SELECT = "SELECT * FROM "
                + TableNames.Certificate.TABLE_NAME + " as c"
                + " left join " + TableNames.CertificateTag.TABLE_NAME + " as ct"
                + " ON c." + TableNames.Certificate.ID
                + " = ct." + TableNames.CertificateTag.CERTIFICATE_ID
                + " left join " + TableNames.Tag.TABLE_NAME + " as t"
                + " ON ct." + TableNames.CertificateTag.TAG_ID
                + " = t." + TableNames.Tag.ID
                + " WHERE c."
                + TableNames.Certificate.NAME
                + " LIKE ? AND c."
                + TableNames.Certificate.DESCRIPTION
                + " LIKE ? AND ifnull(t."
                + TableNames.Tag.NAME
                + ", '') LIKE ?"
                + " GROUP BY c." + TableNames.Certificate.ID + ";";
        public static final String SELECT_BY_ID = "SELECT * FROM "
                + TableNames.Certificate.TABLE_NAME
                + " WHERE "
                + TableNames.Certificate.ID
                + " = ?;";
        public static final String DELETE = "DELETE FROM " + TableNames.Certificate.TABLE_NAME + " WHERE " + TableNames.Certificate.ID + " = ?";

        public static final String UPDATE = "UPDATE " + TableNames.Certificate.TABLE_NAME
                + " SET "
                + TableNames.Certificate.NAME + "=?, "
                + TableNames.Certificate.DESCRIPTION + "=?, "
                + TableNames.Certificate.PRICE + "=?, "
                + TableNames.Certificate.CREATE_DATE + "=?, "
                + TableNames.Certificate.LAST_UPDATE_DATE + "=?, "
                + TableNames.Certificate.DURATION + "=? "
                + " WHERE " + TableNames.Certificate.ID + " = ?";
    }

    public final static class Tag{
        public static final String INSERT = "INSERT INTO " + TableNames.Tag.TABLE_NAME
                + " (" + TableNames.Tag.NAME + ") "
                + " VALUES (?);";
        public static final String SELECT = "SELECT * FROM "
                + TableNames.Tag.TABLE_NAME
                + " WHERE "
                + TableNames.Tag.NAME
                + " LIKE ?;";
        public static final String SELECT_BY_ID = "SELECT * FROM "
                + TableNames.Tag.TABLE_NAME
                + " WHERE "
                + TableNames.Tag.ID
                + " = ?;";
        public static final String SELECT_BY_CERTIFICATE_ID = "SELECT"
                + " t." + TableNames.Tag.NAME
                + ", t." + TableNames.Tag.ID
                + " FROM "
                + TableNames.Tag.TABLE_NAME+ " as t "
                + "INNER JOIN " + TableNames.CertificateTag.TABLE_NAME  + " as gct"
                + " ON t." + TableNames.Tag.ID + " = gct." + TableNames.CertificateTag.TAG_ID
                + " WHERE"
                + " gct." + TableNames.CertificateTag.CERTIFICATE_ID
                + " = ?;";

        public static final String DELETE = "DELETE FROM " + TableNames.Tag.TABLE_NAME + " WHERE " + TableNames.Tag.ID + " = ?";
    }

    public final static class CertificateTag{
        public static final String INSERT = "INSERT INTO " + TableNames.CertificateTag.TABLE_NAME
                + " (" + TableNames.CertificateTag.CERTIFICATE_ID + ", " + TableNames.CertificateTag.TAG_ID + ") "
                + " VALUES (?,?);";
        public static final String SELECT_BY_CERTIFICATE_ID = "SELECT * FROM "
                + TableNames.CertificateTag.TABLE_NAME
                + "JOIN " + TableNames.Tag.TABLE_NAME
                + "ON " + TableNames.CertificateTag.TABLE_NAME +  TableNames.CertificateTag.TAG_ID
                + " = " + TableNames.Tag.TABLE_NAME + "." + TableNames.Tag.ID
                + " WHERE "
                + TableNames.CertificateTag.CERTIFICATE_ID
                + " = ?;";
        public static final String DELETE = "DELETE FROM " + TableNames.CertificateTag.TABLE_NAME + " WHERE " + TableNames.CertificateTag.CERTIFICATE_ID + " = ?";
    }
}
