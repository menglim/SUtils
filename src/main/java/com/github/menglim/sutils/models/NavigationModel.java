package com.github.menglim.sutils.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NavigationModel {
    private String url;
    private String name;
    private boolean menu;
    private String icon;
    private NavigationModel parent;

    boolean anonymous;

    boolean dashboardMenu;

    public NavigationModel(String url, String name, boolean menu, String icon, NavigationModel parent) {
        this.url = url;
        this.name = name;
        this.parent = parent;
        this.menu = menu;
        this.icon = icon;
    }
}
