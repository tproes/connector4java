package org.osiam.client.query.fields;


public abstract class User_ {

    public static final Field ID = new Field("id");
    public static final Field EXTERNAL_ID = new Field("externalId");
    public static volatile Meta_ META;
    public static final Field USER_NAME = new Field("userName");
    public static volatile NAME_ NAME;
    public static final Field DISPLAY_NAME = new Field("displayName");
    public static final Field NICKNAME = new Field("nickName");
    public static final Field PROFILE_URL = new Field("profileUrl");
    public static final Field TITLE = new Field("title");
    public static final Field USER_TYPE = new Field("userType");
    public static final Field PREFERRED_LANGUAGE = new Field("preferredLanguage");
    public static final Field LOCALE = new Field("locale");
    public static final Field TIMEZONE = new Field("timezone");
    public static final Field ACTIVE = new Field("active");
    public static volatile Emails_ ENAILS;
    public static volatile PhoneNumbers_ PHONE_NUMBERS;
    public static volatile Ims_ IMS;
    public static volatile Photos_ PHOTOS;
    public static volatile Addresses_ ADDRESSES;
    public static volatile UserGroups_ GROUPS;
    public static volatile Entitlements_ ENTITLEMENTS;
    public static volatile Roles_ ROLES;
}
