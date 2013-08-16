package org.osiam.client.query.fields;

/**
 * Created with IntelliJ IDEA.
 * User: dmoeb
 * Date: 15.08.13
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public class Attribute {

    private String value = "";

    Attribute(String value){
        this.value = value;
    }

    public String toString(){
        return value;
    }
}
