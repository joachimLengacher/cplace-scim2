package cf.cplace.scim2.adapter.scim2;

import cf.cplace.platform.test.util.StartServerRule;
import com.unboundid.scim2.common.types.Email;
import com.unboundid.scim2.common.types.Name;
import com.unboundid.scim2.common.types.UserResource;
import io.restassured.response.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class IntegrationTest {

    @Rule
    public TestRule startServer = new StartServerRule(this);

    @Test
    public void name() {
        UserResource user = new UserResource()
                .setUserName("bart.simpson@cplace.io")
                .setName(new Name().setGivenName("Bart").setFamilyName("Simpson"))
                .setEmails(List.of(new Email().setPrimary(true).setValue("bart.simpson@cplace.io").setType("work")))
                .setActive(true);

        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .header("X-XSRF-TOKEN", "baae13ec-8516-4d22-aff2-b7b809065a6b")
                .cookie("XSRF-TOKEN","baae13ec-8516-4d22-aff2-b7b809065a6b")
                .and()
                .body(user)
                .when()
                .post("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Users")
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(201));
        assertThat(response.jsonPath().getString("userName"), is("bart.simpson@cplace.io"));
        assertThat(response.jsonPath().getBoolean("active"), is(true));
        assertThat(response.jsonPath().getList("schemas"), hasSize(1));
        assertThat(response.jsonPath().getList("schemas"), contains("urn:ietf:params:scim:schemas:core:2.0:User"));
        assertThat(response.jsonPath().getList("emails", Email.class).get(0).getPrimary(), is(true));
        assertThat(response.jsonPath().getList("emails", Email.class).get(0).getValue(), is("bart.simpson@cplace.io"));
    }
}
