package org.osiam.client.query;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.osiam.client.exception.InvalidAttributeException;
import org.osiam.client.query.metamodel.*;
import org.osiam.resources.scim.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserQueryBuilderTest {

    private static final StringAttribute DEFAULT_ATTR = User_.name.givenName;
    private static final DateAttribute VALID_META_ATTR = User_.meta.created;
    private static final StringAttribute VALID_NAME_ATTR = User_.name.givenName;
    private static final StringAttribute VALID_EMAIL_ATTR = User_.emails.value;
    private static final String INVALID_EMAIL_ATTR = "emails.false";
    private static final String IRRELEVANT = "irrelevant";
    private static final StringAttribute IRRELEVANT_FIELD = Group_.members.value;
    private static final int START_INDEX = 5;
    private static final int COUNT_PER_PAGE = 7;
    private static final String FILTER = "filter=";
    private Query.Builder queryBuilder;
    private Date DATE = new Date();
    private String DATE_STR;

    @Before
    public void setUp() {
        queryBuilder = new Query.Builder(User.class);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        DATE_STR = df.format(DATE);
    }

    @Test
    public void nested_email_attribute_is_added_to_query() {
        queryBuilder.filter(VALID_EMAIL_ATTR.equalTo(IRRELEVANT));
        buildStringMeetsExpectation(FILTER + VALID_EMAIL_ATTR + " eq \"" + IRRELEVANT + "\"");
    }

    @Test
    public void nested_name_attribute_is_added_to_query() {
        queryBuilder.filter(VALID_NAME_ATTR.equalTo(IRRELEVANT));
        buildStringMeetsExpectation(FILTER + VALID_NAME_ATTR + " eq \"" + IRRELEVANT + "\"");
    }

    @Test
    public void nested_meta_attribute_is_added_to_query() {
        queryBuilder.filter(VALID_META_ATTR.equalTo(DATE));
        buildStringMeetsExpectation(FILTER + VALID_META_ATTR + " eq \"" + DATE_STR + "\"");
    }

    @Test
    public void flat_attribute_is_added_to_query() {
        queryBuilder.filter(DEFAULT_ATTR.equalTo(IRRELEVANT));
        buildStringMeetsExpectation(FILTER + DEFAULT_ATTR + " eq \"" + IRRELEVANT + "\"");
    }

    @Test
    public void and_attribute_is_added_correctly() {
        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT))
                .and(DEFAULT_ATTR.contains(IRRELEVANT));
        buildStringMeetsExpectation(FILTER + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\" and " + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\"");
    }

    @Test
    public void or_attribute_is_added_correctly() {
        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT))
                .or(DEFAULT_ATTR.contains(IRRELEVANT));
        buildStringMeetsExpectation(FILTER + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\" or " + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\"");
    }

    @Test
    public void filter_contains_is_added_to_query() {
        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT));
        buildStringMeetsExpectation(FILTER + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\"");
    }

    @Test
    public void filter_equals_is_added_to_query() {
        queryBuilder.filter(DEFAULT_ATTR.equalTo(IRRELEVANT));
        buildStringMeetsExpectation(FILTER + DEFAULT_ATTR + " eq \"" + IRRELEVANT + "\"");
    }

    @Test
    public void filter_startsWith_is_added_to_query() {
        queryBuilder.filter(DEFAULT_ATTR.startsWith(IRRELEVANT));
        buildStringMeetsExpectation(FILTER + DEFAULT_ATTR + " sw \"" + IRRELEVANT + "\"");
    }

    @Test
    public void filter_present_is_added_to_query() {
        queryBuilder.filter(DEFAULT_ATTR.present());
        buildStringMeetsExpectation(FILTER + DEFAULT_ATTR + " pr ");
    }

    @Test
    public void filter_greater_than_is_added_to_query() {
        queryBuilder.filter(VALID_META_ATTR.greaterThan(DATE));
        buildStringMeetsExpectation(FILTER + VALID_META_ATTR + " gt \"" + DATE_STR + "\"");
    }

    @Test
    public void filter_greater_equals_is_added_to_query() {
        queryBuilder.filter(VALID_META_ATTR.greaterEquals(DATE));
        buildStringMeetsExpectation(FILTER + VALID_META_ATTR + " ge \"" + DATE_STR + "\"");
    }

    @Test
    public void filter_less_than_is_added_to_query() {
        queryBuilder.filter(VALID_META_ATTR.lessThan(DATE));
        buildStringMeetsExpectation(FILTER + VALID_META_ATTR + " lt \"" + DATE_STR + "\"");
    }

    @Test
    public void filter_less_equals_is_added_to_query() {
        queryBuilder.filter(VALID_META_ATTR.lessEquals(DATE));
        buildStringMeetsExpectation(FILTER + VALID_META_ATTR + " le \"" + DATE_STR + "\"");
    }

    @Test(expected = InvalidAttributeException.class)
    public void exception_raised_when_attr_is_not_valid() {
        queryBuilder.filter(IRRELEVANT_FIELD.equalTo(IRRELEVANT));
    }

    @Test
    public void sort_order_ascending() {
        queryBuilder.withSortOrder(SortOrder.ASCENDING);
        buildStringMeetsExpectation("sortOrder=ascending");
    }

    @Test
    public void sort_order_descending() {
        queryBuilder.withSortOrder(SortOrder.DESCENDING);
        buildStringMeetsExpectation("sortOrder=descending");
    }

    @Test
    public void query_and_sort_order_ascending() {
        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT))
                .and(DEFAULT_ATTR.contains(IRRELEVANT))
                .withSortOrder(SortOrder.ASCENDING);
        buildStringMeetsExpectation(FILTER + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\" and " + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\"&sortOrder=ascending");
    }

    @Test
    public void two_times_set_sort_order_descending() {
        queryBuilder.withSortOrder(SortOrder.ASCENDING).withSortOrder(SortOrder.DESCENDING);
        buildStringMeetsExpectation("sortOrder=descending");
    }

    @Test
    public void start_index_added() {
        queryBuilder.startIndex(START_INDEX);
        buildStringMeetsExpectation("startIndex=" + START_INDEX);
    }

    @Test
    public void count_per_page_added() {
        queryBuilder.countPerPage(COUNT_PER_PAGE);
        buildStringMeetsExpectation("count=" + COUNT_PER_PAGE);
    }

    @Test
    public void start_index_and_count_added_to_complete_query() {
        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT))
                .and(DEFAULT_ATTR.contains(IRRELEVANT))
                .startIndex(START_INDEX)
                .countPerPage(COUNT_PER_PAGE)
                .withSortOrder(SortOrder.ASCENDING);
        String exceptedQuery = DEFAULT_ATTR + " co \"" + IRRELEVANT + "\" and " + DEFAULT_ATTR
                + " co \"" + IRRELEVANT + "\"&sortOrder=ascending&count=" + COUNT_PER_PAGE
                + "&startIndex=" + START_INDEX;
        buildStringMeetsExpectation(FILTER + exceptedQuery);
    }

    @Test
    public void inner_and_sql_added() {
        Query.Builder innerBuilder = new Query.Builder(User.class);
        innerBuilder.filter(DEFAULT_ATTR.equalTo(IRRELEVANT));

        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT)).and(innerBuilder).startIndex(START_INDEX);

        String exceptedQuery = FILTER + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\" and (" + DEFAULT_ATTR
                + " eq \"" + IRRELEVANT + "\")&startIndex=" + START_INDEX;
        buildStringMeetsExpectation(exceptedQuery);
    }

    @Test
    public void inner_or_sql_added() {
        Query.Builder innerBuilder = new Query.Builder(User.class);
        innerBuilder.filter(DEFAULT_ATTR.equalTo(IRRELEVANT));

        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT)).or(innerBuilder).startIndex(START_INDEX);

        String exceptedQuery = FILTER + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\" or (" + DEFAULT_ATTR
                + " eq \"" + IRRELEVANT + "\")&startIndex=" + START_INDEX;
        buildStringMeetsExpectation(exceptedQuery);
    }

    @Test
    public void one_sort_by_value_added(){
        queryBuilder.sortBy(DEFAULT_ATTR);
        String exceptedQuery = "sortBy=" + DEFAULT_ATTR;
        buildStringMeetsExpectation(exceptedQuery);
    }

    @Test
    public void complet_query_with_all_attributes(){
        Query.Builder innerBuilder = new Query.Builder(User.class);
        innerBuilder.filter(DEFAULT_ATTR.equalTo(IRRELEVANT));

        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT)).or(innerBuilder).startIndex(START_INDEX)
                .countPerPage(COUNT_PER_PAGE).sortBy(DEFAULT_ATTR).withSortOrder(SortOrder.ASCENDING);

        String exceptedQuery = FILTER + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\" or (" + DEFAULT_ATTR
                + " eq \"" + IRRELEVANT + "\")&sortBy=" + DEFAULT_ATTR + "&sortOrder=" + SortOrder.ASCENDING.toString()
                + "&count=" + COUNT_PER_PAGE + "&startIndex=" + START_INDEX;
        buildStringMeetsExpectation(exceptedQuery);
    }

    @Test (expected = InvalidAttributeException.class)
    public void invalid_attribut_to_sort_by_added(){
        queryBuilder.sortBy(IRRELEVANT_FIELD);
        fail("Exception excpected");
    }

    @Test (expected = InvalidAttributeException.class)
    public void invalid_attribut_to_filter_added(){
        queryBuilder.filter(IRRELEVANT_FIELD.contains(IRRELEVANT));
        fail("Exception excpected");
    }

    @Test (expected = IllegalStateException.class)
    public void invalid_call_order(){
        queryBuilder.and(User_.userName.equalTo(IRRELEVANT));
        fail("Exception excpected");
    }

    @Test (expected = InvalidAttributeException.class)
    public void invalid_attribut_to_and_added(){
        queryBuilder.filter((User_.userName.equalTo(IRRELEVANT))).and(IRRELEVANT_FIELD.contains(IRRELEVANT));
        fail("Exception excpected");
    }

    @Test (expected = InvalidAttributeException.class)
    public void invalid_attribut_to_or_added(){
        queryBuilder.filter(User_.userName.equalTo(IRRELEVANT)).or(IRRELEVANT_FIELD.contains(IRRELEVANT));
        fail("Exception excpected");
    }

    private void buildStringMeetsExpectation(String buildString) {
        Query expectedQuery = new Query(buildString);
        assertEquals(expectedQuery, queryBuilder.build());
    }
}
