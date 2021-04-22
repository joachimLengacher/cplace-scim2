/*
 * Copyright 2020, collaboration Factory AG. All rights reserved.
 */

package cf.cplace.examples.spring.test;

import org.junit.runner.RunWith;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchRules;
import com.tngtech.archunit.junit.ArchTest;

import cf.cplace.platform.archunit.ArchUnitRunner;
import cf.cplace.platform.archunit.PlatformUsageRules;

/**
 * This test validates certain architectural rules defined for using the cplace platform.
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "cf.cplace.examples.spring")
public class PlatformUsageRulesTest {
    @ArchTest
    public static final ArchRules platformUsageRules = ArchRules.in(PlatformUsageRules.class);
}
