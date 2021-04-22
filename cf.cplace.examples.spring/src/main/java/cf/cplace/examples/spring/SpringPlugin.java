/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.examples.spring;

import javax.annotation.Nonnull;

import cf.cplace.platform.handler.TestSetupHandlerExtension;
import cf.cplace.platform.services.Plugin;
import cf.cplace.examples.spring.handler.test.TestSetupHandler;

/**
 * TODO Describe the plugin purpose.
 */
// suppress warning: Extensions that are not used directly should be package-private. They are found by reflection anyway.
// Without the suppression, they would be reported as unused, but they are used and must not be removed automatically.
@SuppressWarnings("unused")
public final class SpringPlugin extends Plugin {

    private static final SpringPlugin instance = new SpringPlugin();

    public static SpringPlugin INSTANCE() {
        return instance;
    }

    private SpringPlugin() {
    }

    public final SpringApp app = new SpringApp();

    public final TestSetupHandlerExtension testSetupHandlerExtension = new TestSetupHandlerExtension(TestSetupHandler.class);

    @Nonnull
    @Override
    public String getDefaultLanguage() {
        return "de";
    }
}
