package org.osiam.client.query.fields;

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
    DateField(String value){
        this.value = value;
    }
    public FilterTry equalTo(Date filter){
        return new FilterTry(value + " eq \"" + filter.toString() + "\"");
    }

    public FilterTry contains(Date filter){
        return new FilterTry(value + " co \"" + filter.toString() + "\"");
    }

    public FilterTry present() {
        return new FilterTry(value + " pr ");
    }

    public FilterTry greaterThan(Date filter) {
        return new FilterTry(value + " gt \"" + filter.toString() + "\"");
    }

    public FilterTry greaterEquals(Date filter) {
        return new FilterTry(value + " ge \"" + filter.toString() + "\"");
    }

    public FilterTry lessThan(Date filter) {
        return new FilterTry(value + " lt \"" + filter.toString() + "\"");
    }

    public FilterTry lessEquals(Date filter) {
        return new FilterTry(value + " le \"" + filter.toString() + "\"");
    }

    public Attribute getAttribute(){
        return new Attribute(value);
    }
}
