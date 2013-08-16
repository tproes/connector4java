package org.osiam.client.query.fields;

import sun.font.CreatedFontTracker;

public abstract class Meta_ {

    public static final StringField resourceType = new StringField("meta.resourceType");
    public static final DateField created = new DateField("meta.created");
    public static final DateField lastModified = new DateField("meta.lastModified");
    public static final StringField location = new StringField("meta.location");
    public static final StringField version = new StringField("meta.version");

}
