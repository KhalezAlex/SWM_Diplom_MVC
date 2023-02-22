package org.stepacademy.swm_diplom_mvc.utilities.error_messages;

public enum LoginErrorMessage {
    WRONG_LOGIN ("Введен несуществующий логин"),
    WRONG_PASSWORD ("Пара \"логин - пароль\" не совпадает");

    private final String title;

    LoginErrorMessage(String title) {
        this.title = title;
    }
}
