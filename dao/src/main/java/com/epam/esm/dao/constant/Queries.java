package com.epam.esm.dao.constant;

public final class Queries {

    private Queries() {
    }

    public static final class Certificate {
        private static final String SELECT = "select";
        private static final String SELECT_ENTITIES = " c ";
        private static final String SELECT_COUNT = " count(c) ";
        private static final String FROM = "from Certificate as c ";
        private static final String JOIN = "left join c.tags as t ";
        private static final String WHERE =
                "where ( c.name like concat('%',?1,'%') ) " +
                        "and ( c.description like concat('%',?2,'%') ) " +
                        "and c.isSearchable = true ";
        private static final String TAG_CONDITION =
                "and ( t.name in ?3 ) " +
                        "group by c.id " +
                        "having cast(count(t.name) as integer)>=?4 ";
        private static final String ORDER = "order by c.name";
        private static final String INVERSE_ORDER = " DESC";
        private static final String DELETE = "update Certificate set searchable = false where id = ?1";

        private Certificate() {
        }

        public static String getSelectQuery(boolean buildWithTags, boolean isInverted) {
            StringBuilder builder = new StringBuilder();
            buildQuery(builder, buildWithTags, SELECT_ENTITIES);
            buildOrder(isInverted, builder);
            return builder.toString();
        }

        public static String getCountQuery(boolean buildWithTags) {
            StringBuilder builder = new StringBuilder();
            buildQuery(builder, buildWithTags, SELECT_COUNT);
            return builder.toString();
        }

        public static String getDeleteQuery() {
            return DELETE;
        }

        private static void buildQuery(StringBuilder builder, boolean buildWithTags, String selectable) {
            builder.append(SELECT)
                    .append(selectable)
                    .append(FROM);
            if (buildWithTags) {
                builder.append(JOIN);
            }
            builder.append(WHERE);
            if (buildWithTags) {
                builder.append(TAG_CONDITION);
            }
        }

        private static void buildOrder(boolean isInverted, StringBuilder builder) {
            builder.append(ORDER);
            if (isInverted) {
                builder.append(INVERSE_ORDER);
            }
        }
    }

    public static final class Tag {
        private static final String SELECT = "select t " +
                "from Tag " +
                "t where t.name like concat('%',?1,'%') " +
                "order by t.name";
        private static final String COUNT = "select count(t)" +
                "from Tag " +
                "t where t.name like concat('%',?1,'%') " +
                "order by t.name";
        private static final String INVERSE_ORDER = " DESC";

        private Tag() {
        }

        public static String getSelectQuery(boolean isInverted) {
            return isInverted ? SELECT : SELECT + INVERSE_ORDER;
        }

        public static String getCountQuery() {
            return COUNT;
        }
    }

    public static final class User {
        private static final String SELECT = "select u " +
                "from User u " +
                "where u.name like concat('%', ?1, '%') " +
                "order by u.name";
        private static final String COUNT = "select count(u) " +
                "from User u " +
                "where u.name like concat('%', ?1, '%')";

        private User() {
        }

        public static String getSelectQuery() {
            return SELECT;
        }

        public static String getCountQuery() {
            return COUNT;
        }
    }

    public static final class Order {
        private static final String SELECT = "select o " +
                "from Order o " +
                "order by o.timestamp";
        private static final String COUNT = "select count(o) " +
                "from Order o";

        private Order() {
        }

        public static String getSelectQuery() {
            return SELECT;
        }

        public static String getCountQuery() {
            return COUNT;
        }
    }

}
