package org.osiam.client.query.metamodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dmoeb
 * Date: 16.08.13
 * Time: 09:37
 * To change this template use File | Settings | File Templates.
 */
public class DateAttribute extends Attribute{

    private static DateFormat df;

    static {
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    DateAttribute(String value){
        this.value = value;
    }
    public Filter equalTo(Date filter){
        return new Filter(value + " eq \"" + df.format(filter) + "\"");
    }

    public Filter present() {
        return new Filter(value + " pr ");
    }

    public Filter greaterThan(Date filter) {
        return new Filter(value + " gt \"" + df.format(filter) + "\"");
    }

    public Filter greaterEquals(Date filter) {
        return new Filter(value + " ge \"" + df.format(filter) + "\"");
    }

    public Filter lessThan(Date filter) {
        return new Filter(value + " lt \"" + df.format(filter) + "\"");
    }

    public Filter lessEquals(Date filter) {
        return new Filter(value + " le \"" + df.format(filter) + "\"");
    }
}
