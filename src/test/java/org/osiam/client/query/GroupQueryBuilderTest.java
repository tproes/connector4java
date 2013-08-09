package org.osiam.client.query;


import org.junit.Before;
import org.junit.Test;
import org.osiam.client.exception.InvalidAttributeException;
import org.osiam.resources.scim.Group;

import static org.junit.Assert.assertEquals;

public class GroupQueryBuilderTest {

    private static final String DEFAULT_ATTR = "displayName";
    private static final String IRRELEVANT = "irrelevant";
    private QueryBuilder queryBuilder;

    @Before
    public void setUp() {
        queryBuilder = new QueryBuilder(Group.class);
    }

    @Test
    public void flat_attribute_is_added_to_query() {
        queryBuilder.query(DEFAULT_ATTR);
        buildStringMeetsExpectation(DEFAULT_ATTR);
    }

    @Test
    public void and_attribute_is_added_correctly() {
        queryBuilder.query(DEFAULT_ATTR)
                .contains(IRRELEVANT)
                .and(DEFAULT_ATTR).contains(IRRELEVANT);
        buildStringMeetsExpectation(DEFAULT_ATTR + " co \"" + IRRELEVANT + "\" and " + DEFAULT_ATTR + " co \"" + IRRELEVANT + "\"");
    }

    @Test(expected = InvalidAttributeException.class)
    public void exception_raised_when_attr_is_not_valid() {
        queryBuilder.query(IRRELEVANT);
    }

    private void buildStringMeetsExpectation(String buildString) {
        assertEquals(buildString, queryBuilder.build());
    }
}
