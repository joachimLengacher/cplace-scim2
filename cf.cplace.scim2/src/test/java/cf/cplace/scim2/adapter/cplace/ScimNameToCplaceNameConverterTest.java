package cf.cplace.scim2.adapter.cplace;

import com.unboundid.scim2.common.types.Name;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(value = Parameterized.class)
public class ScimNameToCplaceNameConverterTest {

    private final Name scimName;
    private final String expectedCplaceName;

    public ScimNameToCplaceNameConverterTest(final Name scimName, final String expectedCplaceName) {
        this.scimName = scimName;
        this.expectedCplaceName = expectedCplaceName;
    }

    @Parameterized.Parameters(name = "{index}: testToCplaceName({0}, {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new Name().setGivenName("Bart").setFamilyName("Simpson"), "Bart Simpson"},
                {new Name().setGivenName("Bart").setMiddleName("JoJo").setFamilyName("Simpson"), "Bart JoJo Simpson"},
                {new Name().setGivenName(null).setMiddleName(null).setFamilyName("Simpson"), "Simpson"},
                {new Name().setGivenName(null).setMiddleName("JoJo").setFamilyName("Simpson"), "JoJo Simpson"},
                {new Name().setGivenName(null).setMiddleName(null).setFamilyName(null), ""},
                {new Name().setGivenName("").setMiddleName("").setFamilyName(""), ""},
                {null, ""}
        });
    }

    @Test
    public void testToCplaceName() {
        assertThat(NameConverter.toCplaceName(scimName), is(expectedCplaceName));
    }
}
