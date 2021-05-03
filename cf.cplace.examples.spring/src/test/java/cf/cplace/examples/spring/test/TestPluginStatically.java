/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */

package cf.cplace.examples.spring.test;

import cf.cplace.platform.services.Plugin;
import cf.cplace.platform.test.AbstractTestPluginStatically;
import cf.cplace.examples.spring.SpringPlugin;

public class TestPluginStatically extends AbstractTestPluginStatically {

    @Override
    protected Plugin getTestPlugin() {
        return SpringPlugin.INSTANCE();
    }
}
