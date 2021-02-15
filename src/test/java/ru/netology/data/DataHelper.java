package ru.netology.data;

import lombok.Data;
import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    @Value
    @Data
    public static class Card {
        private String number;
        private int balance;
    }

    public static Card getFirstCardInfo() {
        return new Card("5559 0000 0000 0001", 10000);
    }

    public static Card getSecondCardInfo() {
        return new Card("5559 0000 0000 0002", 10000);
    }
}
