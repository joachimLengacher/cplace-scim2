/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.scim2;

import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.services.app.ProgrammaticallyDefinedAppWithTypeDefs;

import javax.annotation.Nonnull;

/**
 * The cplace SCIM2 app.
 */
public class Scim2App extends ProgrammaticallyDefinedAppWithTypeDefs {

    public static final Message displayName = new Message() {
    };

    public static final Message description = new Message() {
    };

    Scim2App() {
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
        return "fa-users";
    }

    @Nonnull
    @Override
    protected Class<?>[] getTypeDefContainerClasses() {
        return new Class<?>[] { };
    }

}
