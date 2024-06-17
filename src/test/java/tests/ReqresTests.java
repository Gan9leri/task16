package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.ReqresSpecs.*;


@DisplayName("API тесты")
@Tag("api")
public class ReqresTests extends TestBase {

    @DisplayName("Позитивный тест получения данных пользователя")
    @Tag("api_getSingleUser")
    @Test
    void getSingleUserTest(){
        SingleUserResponseModel response =
                step("Отправить GET запрос на получение пользователя ", () ->
        given(reqresRequestSpec)
        .get("users/2")
        .then()
                .spec(responseWithStatusCode200)
                .extract().as(SingleUserResponseModel.class));
        step("Проверяем id в ответе", () ->
        assertThat(response.getData().getId()).isEqualTo(2));
    }

    @DisplayName("Негативный тест получения данных пользователя")
    @Tag("api_getSingleUserNotFound")
    @Test
    void getSingleUserNotFoundTest(){
        step("Отправить GET запрос на получение пользователя ", () ->
        given(reqresRequestSpec)
                .get("users/23")
                .then()
                .spec(responseWithStatusCode404));
    }

    @DisplayName("Позитивный тест получения данных материала")
    @Tag("api_getSingleResource")
    @Test
    void getSingleResourceTest(){
        SingleResourseResponseModel response =
                step("Отправить GET запрос на получение материала ", () ->
        given(reqresRequestSpec)
        .get("unknown/2")
        .then()
                .spec(responseWithStatusCode200)
                .extract().as(SingleResourseResponseModel.class));
        step("Проверить id в ответе ", () ->
        assertThat(response.getData().getId()).isEqualTo(2));
    }

    @DisplayName("Негативный тест получения данных материала")
    @Tag("tag_getSingleResourceNotFount")
    @Test
    void getSingleResourceNotFoundTest(){
        step("Отправить GET запрос на получение материала ", () ->
        given(reqresRequestSpec)
                .get("unknown/23")
                .then()
                .spec(responseWithStatusCode404));
    }

    @DisplayName("Позитивный тест создания пользователя")
    @Tag("tag_postCreateUserTest")
    @Test
    void postCreateUserTest() {
        createUserBodyModel userData = new createUserBodyModel();
        userData.setName("morpheus");
        userData.setJob("leader");
        createUserResponseModel response =
                step("Отправить POST запрос на создание пользователя ", () ->
                given(reqresRequestSpec)
                        .body(userData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseWithStatusCode201)
                        .extract().as(createUserResponseModel.class));
        step("Проверить имя пользователя в ответе", () ->
            assertThat(response.getName()).isEqualTo("morpheus"));
        step("Проверить фамилию пользователя в ответе", () ->
            assertThat(response.getJob()).isEqualTo("leader"));
    }

    @DisplayName("Позитивный тест обновления пользователя")
    @Tag("tag_putUpdateUserTest")
    @Test
    void putUpdateUserTest(){
        updateUserBodyModel userUpdate = new updateUserBodyModel();
        userUpdate.setName("morpheus");
        userUpdate.setJob("zion resident");
        updateUserResponseModel response = step("Отправить PUT запрос на обновление пользователя ", () ->
            given(reqresRequestSpec)
                    .body(userUpdate)
                    .when()
                    .put("/users/2")
                    .then()
                    .spec(responseWithStatusCode200)
                    .extract().as(updateUserResponseModel.class));
        step("Проверить имя в ответе", () ->
            assertThat(response.getName()).isEqualTo("morpheus"));
        step("Проверить работу в ответе", () ->
            assertThat(response.getJob()).isEqualTo("zion resident"));
    }

    @DisplayName("Позитивный тест удаления пользователя")
    @Tag("tag_deleteUserTest")
    @Test
    void deleteUserTestTest(){
        step("Отправить DELETE запрос на удаление пользователя ", () ->
        given(reqresRequestSpec)
                .delete("/users/2")
                .then()
                .spec(responseWithStatusCode204));
    }
}