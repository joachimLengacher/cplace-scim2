/*
 * Copyright 2015, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.examples.spring.adapter.cplace;

import cf.cplace.platform.assets.custom.FixedAppTypes;
import cf.cplace.platform.assets.custom.Multiplicities;
import cf.cplace.platform.assets.custom.def.AttributeDef;
import cf.cplace.platform.assets.custom.def.SingleDateAttributeDef;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.assets.custom.typeConstraints.factory.TypeConstraintFactories;
import cf.cplace.platform.internationalization.Message;

public class ImdbAppTypes {

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class MOVIE {

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };
        public static final TypeDef TYPE = new TypeDef("cf.cplace.examples.spring.movie", name_singular, name_plural, "fa-movie", null)
                .withInternalAttributeNamePrefix("cf.cplace.examples.spring");
    }

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class DIRECTOR {

        public static final SingleDateAttributeDef birthday = AttributeDef.build("birthday", null, TypeConstraintFactories.dateConstraint(Multiplicities.maximalOne));

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };
        public static final TypeDef TYPE = new TypeDef(
                "cf.cplace.examples.spring.director",
                name_singular, name_plural,
                "fa-hand-o-right",
                null,
                birthday)
                .withInternalAttributeNamePrefix("cf.cplace.examples.spring");
    }
}
