package cf.cplace.examples.pluginA.impl;

import cf.cplace.examples.pluginA.api.Multiplier;

public class DefaultMultiplier implements Multiplier {

    private final boolean multiTenancy;

    public DefaultMultiplier(boolean multiTenancy) {
        this.multiTenancy = multiTenancy;
    }

    @Override
    public long multiply(int a, int b) {
        return a * b;
    }
}
