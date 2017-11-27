package com.alin.hourse.common.event;

import java.io.Serializable;

/**
 * @创建者 hailin
 * @创建时间 2017/8/28 16:30
 * @描述 ${}.
 */

public class PermissionsRequestEvent implements Serializable{
    private boolean hasPermissions;

    public boolean isHasPermissions() {
        return hasPermissions;
    }
    public PermissionsRequestEvent(boolean hasPermissions) {
        this.hasPermissions = hasPermissions;
    }
}
