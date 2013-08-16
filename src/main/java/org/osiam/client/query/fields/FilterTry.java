package org.osiam.client.query.fields;

/**
 * Created with IntelliJ IDEA.
 * User: dmoeb
 * Date: 16.08.13
 * Time: 09:10
 * To change this template use File | Settings | File Templates.
 */
public class FilterTry {

    private String filter;
    FilterTry(String filter){
        this.filter = filter;
    }

    public String toString(){
        return filter;
    }
}
