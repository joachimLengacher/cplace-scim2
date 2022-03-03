package cf.cplace.scim2.adapter;

import cf.cplace.platform.test.util.StartServerRule;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.types.GroupResource;
import io.restassured.response.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class GroupIntegrationTest {

    public static final String GROUP_NAME = "The Simpsons";

    @Rule
    public TestRule startServer = new StartServerRule(this);

    @Test
    public void testGroupLifecycle() {
        findAllGroups();
        String id = createGroup();
        findGroup(id);
        updateGroup(id);
        patchGroup(id);
    }

    private void findAllGroups() {
        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .and()
                .param("count", 30)
                .param("startIndex", 1)
                .when()
                .get("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Groups")
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(OK.value()));
        @SuppressWarnings("unchecked")
        final ListResponse<GroupResource> allGroups = response.getBody().as(ListResponse.class);
        assertThat(allGroups.getTotalResults(), is(1));
        assertThat(allGroups.getItemsPerPage(), is(30));
        assertThat(allGroups.getStartIndex(), is(0));
        assertThat(response.jsonPath().getList("schemas"), contains("urn:ietf:params:scim:api:messages:2.0:ListResponse"));
    }


    private GroupResource findGroup(String id) {
        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .when()
                .get("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Groups/" + id)
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(OK.value()));
        assertGroupValues(response, GROUP_NAME);
        assertThat(response.jsonPath().getString("id"), is(id));
        return response.as(GroupResource.class);
    }

    private void updateGroup(String id) {
        GroupResource group = findGroup(id);
        group.setDisplayName("The Simpsons Family");

        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .and()
                .body(group)
                .when()
                .put("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Groups/" + id)
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(OK.value()));
        assertGroupValues(response, "The Simpsons Family");
        assertThat(response.jsonPath().getString("id"), is(id));
    }


    private void patchGroup(String id) {
        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .and()
                .body("{\"displayName\":\"The Simpsons\"}")
                .when()
                .patch("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Groups/" + id)
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(OK.value()));
        assertGroupValues(response, "The Simpsons");
        assertThat(response.jsonPath().getString("id"), is(id));
    }

    private String createGroup() {
        GroupResource group = new GroupResource()
                .setDisplayName(GROUP_NAME);

        Response response = given()
                .auth()
                .preemptive()
                .basic("mustermann@test.tricia", "ottto")
                .header("Content-type", "application/json")
                .header("Accept", "application/scim+json; charset=utf-8")
                .and()
                .body(group)
                .when()
                .post("http://localhost:8083/intern/tricia/cplace-api/cf.cplace.scim2/Groups")
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(CREATED.value()));
        assertGroupValues(response, GROUP_NAME);
        assertThat(response.jsonPath().getString("id"), is(not(nullValue())));

        return response.jsonPath().getString("id");
    }

    private void assertGroupValues(Response response, String name) {
        assertThat(response.jsonPath().getList("schemas"), hasSize(1));
        assertThat(response.jsonPath().getList("schemas"), contains("urn:ietf:params:scim:schemas:core:2.0:Group"));
        assertThat(response.jsonPath().getString("displayName"), is(name));
    }
}
