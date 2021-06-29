/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.examples.pluginB;

import javax.annotation.Nonnull;

import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.services.app.ProgrammaticallyDefinedAppWithTypeDefs;

public class PluginBApp extends ProgrammaticallyDefinedAppWithTypeDefs {

    public static final Message displayName = new Message() {
    };

    public static final Message description = new Message() {
    };

    PluginBApp() {
        // package scope, only the plugin creates an instance
    }

    @Nonnull
    @Override
    public Message getDisplayName() {
        return displayName;
    }

    @Override
    public Message getDescription() {
        return description;
    }

    @Override
    public String getIconName() {
        return "fa-info";
    }

    @Nonnull
    @Override
    protected Class<?>[] getTypeDefContainerClasses() {
        return new Class<?>[] { PluginBAppTypes.class };
    }

}
