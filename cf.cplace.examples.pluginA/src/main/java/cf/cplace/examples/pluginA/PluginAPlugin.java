/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.examples.pluginA;

import javax.annotation.Nonnull;

import cf.cplace.platform.handler.TestSetupHandlerExtension;
import cf.cplace.platform.services.Plugin;
import cf.cplace.examples.pluginA.handler.test.TestSetupHandler;

/**
 * TODO Describe the plugin purpose.
 */
// suppress warning: Extensions that are not used directly should be package-private. They are found by reflection anyway.
// Without the suppression, they would be reported as unused, but they are used and must not be removed automatically.
@SuppressWarnings("unused")
public final class PluginAPlugin extends Plugin {

    private static final PluginAPlugin instance = new PluginAPlugin();

    public static PluginAPlugin INSTANCE() {
        return instance;
    }

    private PluginAPlugin() {
    }

    public final PluginAApp app = new PluginAApp();

    public final TestSetupHandlerExtension testSetupHandlerExtension = new TestSetupHandlerExtension(TestSetupHandler.class);

    @Nonnull
    @Override
    public String getDefaultLanguage() {
        return "de";
    }
}
