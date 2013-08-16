package org.osiam.client.query;


import org.junit.Before;
import org.junit.Test;
import org.osiam.client.exception.InvalidAttributeException;
import org.osiam.client.query.fields.Group_;
import org.osiam.client.query.fields.StringField;
import org.osiam.client.query.fields.User_;
import org.osiam.resources.scim.Group;

import static org.junit.Assert.assertEquals;

public class GroupQueryBuilderTest {

    private static final StringField DEFAULT_ATTR = Group_.displayName;
    private static final String IRRELEVANT = "irrelevant";
    private static final StringField IRRELEVANT_ATTRIBUTE = User_.nickName;
    private Query.Builder queryBuilder;

    @Before
    public void setUp() {
        queryBuilder = new Query.Builder(Group.class);
    }

    @Test
    public void flat_attribute_is_added_to_query() {
        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT));
        buildStringMeetsExpectation("filter=" + DEFAULT_ATTR.getAttribute() + " co \"" + IRRELEVANT + "\"");
    }

    @Test
    public void and_attribute_is_added_correctly() {
        queryBuilder.filter(DEFAULT_ATTR.contains(IRRELEVANT))
                .and(DEFAULT_ATTR.contains(IRRELEVANT));
        buildStringMeetsExpectation("filter=" + DEFAULT_ATTR.getAttribute() + " co \"" + IRRELEVANT + "\" and " + DEFAULT_ATTR.getAttribute() + " co \"" + IRRELEVANT + "\"");
    }

    @Test(expected = InvalidAttributeException.class)
    public void exception_raised_when_attr_is_not_valid() {
        queryBuilder.filter(IRRELEVANT_ATTRIBUTE.contains(IRRELEVANT));
    }

    private void buildStringMeetsExpectation(String buildString) {
        Query expectedQuery = new Query(buildString);
        assertEquals(expectedQuery, queryBuilder.build());
    }
}
