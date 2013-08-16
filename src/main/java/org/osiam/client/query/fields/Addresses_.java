package org.osiam.client.query.fields;

/**
 * Created with IntelliJ IDEA.
 * User: dmoeb
 * Date: 15.08.13
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
public class Addresses_ {

    public static final StringField type = new StringField("addresses.type");
    public static final StringField streetAddress = new StringField("addresses.streetAddress");
    public static final StringField locality = new StringField("addresses.locality");
    public static final StringField region = new StringField("addresses.region");
    public static final StringField postalCode = new StringField("addresses.postalCode");
    public static final StringField country = new StringField("addresses.country");
    public static final StringField formatted = new StringField("addresses.formatted");
    public static final StringField primary = new StringField("addresses.primary");
    public static final StringField operation = new StringField("addresses.operation");
}
