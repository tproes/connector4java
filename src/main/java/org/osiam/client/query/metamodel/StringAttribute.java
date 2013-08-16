package org.osiam.client.query.metamodel;

public class StringAttribute extends Attribute{

    StringAttribute(String value){
        this.value = value;
    }
    public Filter equalTo(String filter){
        return new Filter(value + " eq \"" + filter + "\"");
    }

    public Filter contains(String filter){
        return new Filter(value + " co \"" + filter + "\"");
    }

    public Filter startsWith(String filter) {
        return new Filter(value + " sw \"" + filter + "\"");
    }

    public Filter present() {
        return new Filter(value + " pr ");
    }
}
