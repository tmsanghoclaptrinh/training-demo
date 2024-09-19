package com.cuonglamchi.model;

import java.util.List;

public class User {
    // Attributes
    private String fullName;
    private int avatarSourceId;
    private List<ShortcutItem> shortcuts;

    // Constructor
    public User(String fullName, int avatarSourceId, List<ShortcutItem> shortcuts) {
        this.fullName = fullName;
        this.avatarSourceId = avatarSourceId;
        this.shortcuts = shortcuts;
    }

    // Getter
    public String getFullName() {
        return fullName;
    }

    public int getAvatarSourceId() {
        return avatarSourceId;
    }

    public List<ShortcutItem> getShortcuts() {
        return shortcuts;
    }
}
