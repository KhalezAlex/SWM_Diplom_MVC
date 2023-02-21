package org.stepacademy.swm_diplom_mvc.utilities.error_messages;

public enum RegistrationErrorMessage {
    WRONG_LOGIN ("Введен уже существующий логин"),
    WRONG_PASSWORD ("Введенный пароль неверен"),
    PASSWORDS_MISS_MATCH("Пароли не совпадают");

    private String title;

    RegistrationErrorMessage(String title) {
        this.title = title;
    }
}
