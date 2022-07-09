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
                "where ( c.name like concat('%',?1,'%') ) ";
        private static final String DESCRIPTION_SEARCH_CONDITION =
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

        public static String getSelectSearchableQuery(boolean buildWithTags, boolean isInverted) {
            StringBuilder builder = new StringBuilder();
            buildQuery(builder, buildWithTags, SELECT_ENTITIES);
            buildOrder(isInverted, builder);
            return builder.toString();
        }

        public static String getSelectByNameQuery() {
            StringBuilder builder = new StringBuilder();
            builder.append(SELECT).append(SELECT_ENTITIES).append(FROM).append(WHERE);
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
            builder.append(DESCRIPTION_SEARCH_CONDITION);
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

        private static final String SELECT_MOST_WIDELY_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ORDERS =
                "select t.id, t.name from " +
                        "(select sct.id as id from " +
                        "(select u.id as id " +
                        "from users u left join orders o on o.user = u.id group by u.id " +
                        "order by sum(o.cost) desc limit 1 " +
                        ") as sct " +
                        ") as u " +
                        "left join orders as o on o.user = u.id " +
                        "left join certificates as c on c.id = o.certificate_id " +
                        "left join certificate_tag as ct on ct.certificate_id = c.id " +
                        "left join tags as t on t.id = ct.tag_id " +
                        "group by t.id " +
                        "order by count(o.id) desc limit 1;";


        private Tag() {
        }

        public static String getSelectQuery(boolean isInverted) {
            return isInverted ? SELECT : SELECT + INVERSE_ORDER;
        }

        public static String getCountQuery() {
            return COUNT;
        }

        public static String getComplexSelectQuery() {
            return SELECT_MOST_WIDELY_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ORDERS;
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
        private static final String SELECT_BY_USER_ID = "select o " +
                "from Order o " +
                "join o.user u " +
                "where u.id = ?1 " +
                "order by o.timestamp";
        private static final String COUNT_BY_USER_ID = "select count(o) " +
                "from Order o " +
                "join o.user u " +
                "where u.id = ?1 " +
                "order by o.timestamp";

        private Order() {
        }

        public static String getSelectQuery() {
            return SELECT;
        }

        public static String getCountQuery() {
            return COUNT;
        }

        public static String getSelectByUserIdQuery() {
            return SELECT_BY_USER_ID;
        }

        public static String getCountByUserIdQuery() {
            return COUNT_BY_USER_ID;
        }
    }

}
