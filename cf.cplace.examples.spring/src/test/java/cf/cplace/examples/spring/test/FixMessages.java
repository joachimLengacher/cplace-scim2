/*
 * Copyright 2018, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.examples.spring.test;

import cf.cplace.platform.test.i18n.MessagesUtility;
import cf.cplace.examples.spring.SpringPlugin;

public class FixMessages {

    public static void main(String[] args) {
        MessagesUtility.fix(SpringPlugin.INSTANCE());
    }
}
