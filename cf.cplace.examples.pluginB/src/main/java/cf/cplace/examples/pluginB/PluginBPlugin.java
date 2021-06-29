/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.examples.pluginB;

import javax.annotation.Nonnull;

import cf.cplace.platform.handler.TestSetupHandlerExtension;
import cf.cplace.platform.services.Plugin;
import cf.cplace.examples.pluginB.handler.test.TestSetupHandler;

/**
 * TODO Describe the plugin purpose.
 */
// suppress warning: Extensions that are not used directly should be package-private. They are found by reflection anyway.
// Without the suppression, they would be reported as unused, but they are used and must not be removed automatically.
@SuppressWarnings("unused")
public final class PluginBPlugin extends Plugin {

    private static final PluginBPlugin instance = new PluginBPlugin();

    public static PluginBPlugin INSTANCE() {
        return instance;
    }

    private PluginBPlugin() {
    }

    public final PluginBApp app = new PluginBApp();

    public final TestSetupHandlerExtension testSetupHandlerExtension = new TestSetupHandlerExtension(TestSetupHandler.class);

    @Nonnull
    @Override
    public String getDefaultLanguage() {
        return "de";
    }
}
