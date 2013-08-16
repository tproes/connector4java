package org.osiam.client.query;
/*
 * for licensing see the file license.txt.
 */

import org.osiam.client.exception.InvalidAttributeException;
import org.osiam.client.query.metamodel.Attribute;
import org.osiam.client.query.metamodel.Filter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a query as it is run against the OSIAM service.
 */
public class Query {
    static final private int DEFAULT_COUNT = 100;
    static final private int DEFAULT_INDEX = 0;
    static final private Pattern INDEX_PATTERN = Pattern.compile("startIndex=(\\d+)&?");
    static final private Pattern COUNT_PATTERN = Pattern.compile("count=(\\d+)&?");
    final private String queryString;

    private Matcher indexMatcher;
    private Matcher countMatcher;

    public Query(String queryString) {
        this.queryString = queryString;
        indexMatcher = INDEX_PATTERN.matcher(queryString);
        countMatcher = COUNT_PATTERN.matcher(queryString);
    }

    /**
     * Returns the number of items per page this query is configured for. If no explicit number was given, the default
     * number of items per page is returned, which is 100.
     *
     * @return The number of Items this Query is configured for.
     */
    public int getCount() {
        if (queryStringContainsCount()) {
            return Integer.parseInt(countMatcher.group(1));
        }
        return DEFAULT_COUNT;
    }

    /**
     * Returns the startIndex ot this query. If no startIndex was set, it returns the default, which is 0.
     *
     * @return The startIndex of this query.
     */
    public int getStartIndex() {
        if (queryStringContainsIndex()) {
            return Integer.parseInt(indexMatcher.group(1));
        }
        return DEFAULT_INDEX;
    }

    /**
     * Create a new query that is moved forward in the result set by one page.
     *
     * @return A new query paged forward by one.
     */
    public Query nextPage() {
        String nextIndex = "startIndex=" + (getCount() + getStartIndex());
        if (queryStringContainsIndex()) {
            return new Query(indexMatcher.replaceFirst(nextIndex));
        }
        return new Query(queryString + "&" + nextIndex);
    }

    /**
     * Create a new query that is moved backward the result set by one page.
     *
     * @return A new query paged backward by one.
     */
    public Query previousPage() {
        int newIndex = getStartIndex() - getCount();
        if (newIndex < 0) {
            throw new IllegalStateException("Negative startIndex is not possible.");
        }
        String prevIndex = "startIndex=" + newIndex;
        return new Query(indexMatcher.replaceFirst("" + prevIndex));
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Query query = (Query) other;

        return queryString.equals(query.queryString);

    }

    @Override
    public int hashCode() {
        return queryString.hashCode();
    }

    /**
     * @return The query as a String that can be used in a web request.
     */
    public String toString() {
        return queryString;
    }

    private boolean queryStringContainsIndex() {
        return indexMatcher.find(0);
    }

    private boolean queryStringContainsCount() {
        return countMatcher.find(0);
    }

    /**
     * The Builder is used to construct instances of the {@link Query}
     */
    public static final class Builder {

        static final private int DEFAULT_START_INDEX = 0;
        static final private int DEFAULT_COUNT_PER_PAGE = 100;
        private Class clazz;
        private StringBuilder filterBuilder;
        private String sortBy;
        private SortOrder sortOrder;
        private int startIndex = DEFAULT_START_INDEX;
        private int countPerPage = DEFAULT_COUNT_PER_PAGE;

        /**
         * The Constructor of the QueryBuilder
         *
         * @param clazz The class of Resources to query for.
         */
        public Builder(Class clazz) {
            filterBuilder = new StringBuilder();
            this.clazz = clazz;
        }

        /**
         * Add a filter on the given Attribute.
         *
         * @param filter The name of the attribute to filter on.
         * @return A {@link org.osiam.client.query.metamodel.Filter} to specify the filtering criteria
         * @throws org.osiam.client.exception.InvalidAttributeException if the given attribute is not valid for a query
         */
        public Builder filter(Filter filter) {
            filterBuilder = new StringBuilder();
            return query(filter);
        }

        /**
         * Add an 'logical and' operation to the filter with another attribute to filter on.
         *
         * @param filter The name of the attribute to filter the and clause on.
         * @return A {@link org.osiam.client.query.metamodel.Filter} to specify the filtering criteria
         * @throws org.osiam.client.exception.InvalidAttributeException if the given attribute is not valid for a query
         */
        public Builder and(Filter filter) {
            ensureFilterBuilderHasBeenCalledFirst();
            filterBuilder.append(" and ");
            return query(filter);
        }

        /**
         * Adds the query of the given Builder into ( and ) to the filter
         *
         * @param innerFilter the inner filter
         * @return The Builder with the inner filter added.
         */
        public Builder and(Builder innerFilter) {
            ensureFilterBuilderHasBeenCalledFirst();
            filterBuilder.append(" and (").append(innerFilter.filterBuilder).append(")");
            return this;
        }

        /**
         * Add an 'logical or' operation to the filter with another attribute to filter on.
         *
         * @param filter The name of the attribute to filter the or clause on.
         * @return A {@link org.osiam.client.query.metamodel.Filter} to specify the filtering criteria
         * @throws org.osiam.client.exception.InvalidAttributeException if the given attribute is not valid for a query
         */
        public Builder or(Filter filter) {
            ensureFilterBuilderHasBeenCalledFirst();
            filterBuilder.append(" or ");
            return query(filter);
        }

        /**
         * Adds the query of the given Builder into ( and ) to the filter
         *
         * @param innerFilter the filter in parentheses
         * @return The Builder with the filter in parentheses added.
         */
        public Builder or(Builder innerFilter) {
            ensureFilterBuilderHasBeenCalledFirst();
            filterBuilder.append(" or (").append(innerFilter.filterBuilder).append(")");
            return this;
        }

        /**
         * Adds the given {@link SortOrder} to the query
         *
         * @param sortOrder The order in which to sort the result
         * @return The Builder with this sort oder added.
         */
        public Builder withSortOrder(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        /**
         * Add the start Index from where on the list will be returned to the query
         *
         * @param startIndex The position to use as the first entry in the result.
         * @return The Builder with this start Index added.
         */
        public Builder startIndex(int startIndex) {
            this.startIndex = startIndex;
            return this;
        }

        /**
         * Add the number of wanted results per page to the query
         *
         * @param count The number of items displayed per page.
         * @return The Builder with this count per page added.
         */
        public Builder countPerPage(int count) {
            this.countPerPage = count;
            return this;
        }

        /**
         * Add the wanted attribute names to the sortBy statement.
         *
         * @param attribute attributes to sort by the query
         * @return The Builder with sortBy added.
         */
        public Builder sortBy(Attribute attribute) {
            if (!(isAttributeValid(attribute.toString()))) {
                throw new InvalidAttributeException("Sorting for this attribute is not supported");
            }
            sortBy = attribute.toString();
            return this;
        }

        /**
         * Build the query String to use against OSIAM.
         *
         * @return The query as a String
         */
        public Query build() {
            StringBuilder builder = new StringBuilder();
            if (filterBuilder.length() != 0) {
                ensureQueryParamIsSeparated(builder);
                builder.append("filter=")
                        .append(filterBuilder);
            }
            if (sortBy != null) {
                ensureQueryParamIsSeparated(builder);
                builder.append("sortBy=")
                        .append(sortBy);
            }
            if (sortOrder != null) {
                ensureQueryParamIsSeparated(builder);
                builder.append("sortOrder=")
                        .append(sortOrder);

            }
            if (countPerPage != DEFAULT_COUNT_PER_PAGE) {
                ensureQueryParamIsSeparated(builder);
                builder.append("count=")
                        .append(countPerPage);
            }
            if (startIndex != DEFAULT_START_INDEX) {
                ensureQueryParamIsSeparated(builder);
                builder.append("startIndex=")
                        .append(startIndex);
            }
            return new Query(builder.toString());
        }

        private void ensureFilterBuilderHasBeenCalledFirst(){
            if(filterBuilder.length() == 0){
                throw new IllegalStateException("Method filter has to be called first");
            }
        }

        private void ensureQueryParamIsSeparated(StringBuilder builder) {
            if (builder.length() != 0) {
                builder.append("&");
            }
        }

        private Builder query(Filter filter) {
            if (!(isAttributeValid(filter))) {
                throw new InvalidAttributeException("Querying for this attribute is not supported");
            }

            filterBuilder.append(filter.toString());
            return this;
        }

        private boolean isAttributeValid(Filter filter) {
            String attribute = filter.toString().substring(0, filter.toString().indexOf(" "));
            return isAttributeValid(attribute, clazz);
        }

        private boolean isAttributeValid(String attribute) {
            return isAttributeValid(attribute, clazz);
        }

        private boolean isAttributeValid(String attribute, Class clazz) {
            String compositeField = "";
            if (attribute.contains(".")) {
                compositeField = attribute.substring(attribute.indexOf('.') + 1);
            }
            if (attribute.startsWith("meta.")) {
                return isAttributeValid(compositeField, org.osiam.resources.scim.Meta.class);
            }
            if (attribute.startsWith("emails.")) {
                return isAttributeValid(compositeField, org.osiam.resources.scim.MultiValuedAttribute.class);
            }
            if (attribute.startsWith("name.")) {
                return isAttributeValid(compositeField, org.osiam.resources.scim.Name.class);
            }

            for (Field field : clazz.getDeclaredFields()) {
                if (Modifier.isPrivate(field.getModifiers()) && field.getName().equalsIgnoreCase(attribute)) {
                    return true;
                }
            }
            return false;
        }
    }


}