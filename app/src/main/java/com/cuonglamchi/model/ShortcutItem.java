package com.cuonglamchi.model;

public class ShortcutItem {
    // Attributes
    private int avatar;
    private boolean isGroup; //
    private String name;

    // Constructor
    public ShortcutItem(int avatar, boolean isGroup, String name) {
        this.avatar = avatar;
        this.isGroup = isGroup;
        this.name = name;
    }

    // Getter
    public int getAvatar() {
        return avatar;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public String getName() {
        return name;
    }
}
