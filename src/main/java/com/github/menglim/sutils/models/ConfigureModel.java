package com.github.menglim.sutils.models;

import lombok.Data;

@Data
public class ConfigureModel {
    private String firebaseApiKey;
    private String firebaseAuthDomain;
    private String firebaseDatabaseURL;
    private String firebaseProjectId;
    private String firebaseStorageBucket;
    private String firebaseMessagingSenderId;
    private String firebaseAppId;
    private String measurementId;
    private String firebasePublicVapidKey;
    private String profilePictureAllowedExtension;
    private String profilePictureAllowedFileSize;
    private String profilePictureDefaultUrl;
}
