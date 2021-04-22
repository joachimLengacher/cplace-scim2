/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.examples.spring;

import cf.cplace.platform.services.Plugin;

import javax.annotation.Nonnull;

/**
 * A simple plugin that demonstrates how the Springframework can be used in cplace plugins.
 */
@SuppressWarnings("unused")
public final class SpringPlugin extends Plugin {

    private static final SpringPlugin instance = new SpringPlugin();

    public static SpringPlugin INSTANCE() {
        return instance;
    }

    private SpringPlugin() {
    }

    public final SpringApp app = new SpringApp();

    @Nonnull
    @Override
    public String getDefaultLanguage() {
        return "de";
    }
}
