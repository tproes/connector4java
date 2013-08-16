package org.osiam.client.query.fields;

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
public class DateField {

    private String value;
    private static DateFormat df;

    static {
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }
    DateField(String value){
        this.value = value;
    }
    public FilterTry equalTo(Date filter){
        return new FilterTry(value + " eq \"" + df.format(filter) + "\"");
    }

    public FilterTry contains(Date filter){
        return new FilterTry(value + " co \"" + df.format(filter) + "\"");
    }

    public FilterTry present() {
        return new FilterTry(value + " pr ");
    }

    public FilterTry greaterThan(Date filter) {
        return new FilterTry(value + " gt \"" + df.format(filter) + "\"");
    }

    public FilterTry greaterEquals(Date filter) {
        return new FilterTry(value + " ge \"" + df.format(filter) + "\"");
    }

    public FilterTry lessThan(Date filter) {
        return new FilterTry(value + " lt \"" + df.format(filter) + "\"");
    }

    public FilterTry lessEquals(Date filter) {
        return new FilterTry(value + " le \"" + df.format(filter) + "\"");
    }

    public Attribute getAttribute(){
        return new Attribute(value);
    }
}
