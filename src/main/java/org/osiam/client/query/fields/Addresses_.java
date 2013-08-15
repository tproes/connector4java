package org.osiam.client.query.fields;

/**
 * Created with IntelliJ IDEA.
 * User: dmoeb
 * Date: 15.08.13
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
public class Addresses_ {

    public static final Field TYPE = new Field("addresses.type");
    public static final Field STREET_ADDRESS = new Field("addresses.streetAddress");
    public static final Field LOCALITY = new Field("addresses.locality");
    public static final Field REGION = new Field("addresses.region");
    public static final Field POSTAL_CODE = new Field("addresses.postalCode");
    public static final Field COUNTRY = new Field("addresses.country");
    public static final Field FORMATTED = new Field("addresses.formatted");
    public static final Field PRIMARY = new Field("addresses.primary");
    public static final Field OPERATION = new Field("addresses.operation");
}
