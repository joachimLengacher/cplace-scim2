/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.scim2;

import cf.cplace.platform.services.Plugin;

import javax.annotation.Nonnull;

/**
 * A simple plugin that demonstrates how the Springframework can be used in cplace plugins.
 */
@SuppressWarnings("unused")
public final class Scim2Plugin extends Plugin {

    private static final Scim2Plugin instance = new Scim2Plugin();

    public static Scim2Plugin INSTANCE() {
        return instance;
    }

    private Scim2Plugin() {
    }

    public final Scim2App app = new Scim2App();

    @Nonnull
    @Override
    public String getDefaultLanguage() {
        return "de";
    }
}
