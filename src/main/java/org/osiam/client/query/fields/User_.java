package org.osiam.client.query.fields;


import org.osiam.resources.scim.User;

import javax.persistence.metamodel.SingularAttribute;

public abstract class User_ {

    public static final StringField id = new StringField("id");
    public static final StringField externalId = new StringField("externalId");
    public static volatile Meta_ meta;
    public static final StringField userName = new StringField("userName");
    public static volatile NAME_ name;
    public static final StringField displayName = new StringField("displayName");
    public static final StringField nickName = new StringField("nickName");
    public static final StringField profileUrl = new StringField("profileUrl");
    public static final StringField title = new StringField("title");
    public static final StringField userType = new StringField("userType");
    public static final StringField preferredLanguage = new StringField("preferredLanguage");
    public static final StringField locale = new StringField("locale");
    public static final StringField timezone = new StringField("timezone");
    public static final StringField active = new StringField("active");
    public static volatile Emails_ emails;
    public static volatile PhoneNumbers_ phoneNumber;
    public static volatile Ims_ ims;
    public static volatile Photos_ photos;
    public static volatile Addresses_ addresses;
    public static volatile UserGroups_ groups;
    public static volatile Entitlements_ entitlements;
    public static volatile Roles_ toles;

}
