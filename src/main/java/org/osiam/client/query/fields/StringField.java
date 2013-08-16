package org.osiam.client.query.fields;

public class StringField {

    private String value;
    StringField(String value){
        this.value = value;
    }
    public FilterTry equalTo(String filter){
        return new FilterTry(value + " eq \"" + filter + "\"");
    }

    public FilterTry contains(String filter){
        return new FilterTry(value + " co \"" + filter + "\"");
    }

    public FilterTry startsWith(String filter) {
        return new FilterTry(value + " sw \"" + filter + "\"");
    }

    public FilterTry present() {
        return new FilterTry(value + " pr ");
    }

    public Attribute getAttribute(){
        return new Attribute(value);
    }
}
