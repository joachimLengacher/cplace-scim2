/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */

package cf.cplace.examples.pluginB.test;

import cf.cplace.platform.services.Plugin;
import cf.cplace.platform.test.AbstractTestPluginStatically;
import cf.cplace.examples.pluginB.PluginBPlugin;

public class TestPluginStatically extends AbstractTestPluginStatically {

    @Override
    protected Plugin getTestPlugin() {
        return PluginBPlugin.INSTANCE();
    }
}
