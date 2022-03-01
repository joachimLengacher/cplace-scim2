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
public class CplaceNameToScimNameConverterTest {

    private final Name expectedScimName;
    private final String cplaceName;

    public CplaceNameToScimNameConverterTest(final String cplaceName, final Name expectedScimName) {
        this.cplaceName = cplaceName;
        this.expectedScimName = expectedScimName;
    }

    @Parameterized.Parameters(name = "{index}: testToCplaceName({0}, {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Bart Simpson", new Name().setGivenName("Bart").setFamilyName("Simpson")},
                {"Bart JoJo Simpson", new Name().setGivenName("Bart").setMiddleName("JoJo").setFamilyName("Simpson")},
                {"Bart Simon Jo Simpson", new Name().setGivenName("Bart").setMiddleName("Simon Jo").setFamilyName("Simpson")},
                {"Simpson", new Name().setGivenName(null).setMiddleName(null).setFamilyName("Simpson")},
                {"", new Name().setGivenName(null).setMiddleName(null).setFamilyName(null)},
                {null, new Name().setGivenName(null).setMiddleName(null).setFamilyName(null)}
        });
    }

    @Test
    public void testToCplaceName() {
        assertThat(NameConverter.toScimName(cplaceName), is(expectedScimName));
    }
}
