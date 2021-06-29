/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */

package cf.cplace.examples.pluginA.test;

import cf.cplace.platform.services.Plugin;
import cf.cplace.platform.test.AbstractTestPluginStatically;
import cf.cplace.examples.pluginA.PluginAPlugin;

public class TestPluginStatically extends AbstractTestPluginStatically {

    @Override
    protected Plugin getTestPlugin() {
        return PluginAPlugin.INSTANCE();
    }
}
