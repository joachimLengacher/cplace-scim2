/*
 * Copyright 2015, collaboration Factory AG. All rights reserved.
 */
package cf.cplace.examples.spring.adapter.cplace;

import cf.cplace.platform.assets.custom.FixedAppTypes;
import cf.cplace.platform.assets.custom.Multiplicities;
import cf.cplace.platform.assets.custom.def.AttributeDef;
import cf.cplace.platform.assets.custom.def.SingleDateAttributeDef;
import cf.cplace.platform.assets.custom.def.SinglePageReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.assets.custom.typeConstraints.factory.TypeConstraintFactories;
import cf.cplace.platform.internationalization.Message;

/**
 * The cplace custom entities type to which the domain objects are mapped.
 */
public class ImdbAppTypes {

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class MOVIE {

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };

        public static final Message director_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef MOVIE_DIRECTOR =
                AttributeDef.build("cf.cplace.examples.spring.movie.director", director_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, DIRECTOR.TYPE.name, null, true));

        public static final TypeDef TYPE = new TypeDef("cf.cplace.examples.spring.movie", name_singular, name_plural, "fa-movie", null, MOVIE_DIRECTOR)
                .withInternalAttributeNamePrefix("cf.cplace.examples.spring");
    }

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class DIRECTOR {

        public static final SingleDateAttributeDef BIRTHDAY = AttributeDef.build("birthday", null, TypeConstraintFactories.dateConstraint(Multiplicities.maximalOne));

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };
        public static final TypeDef TYPE = new TypeDef(
                "cf.cplace.examples.spring.director",
                name_singular, name_plural,
                "fa-hand-o-right",
                null,
                BIRTHDAY)
                .withInternalAttributeNamePrefix("cf.cplace.examples.spring");
    }
}
