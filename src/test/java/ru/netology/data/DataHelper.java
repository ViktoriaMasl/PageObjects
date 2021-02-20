package ru.netology.data;

import com.codeborne.selenide.Condition;
import lombok.Data;
import lombok.Value;
import lombok.val;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

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

    public static void adjustmentOfTheBalance() {
        DashboardPage dashboardPage = new DashboardPage();
        val balanceFirstCardStart = dashboardPage.getCardBalance("5559 0000 0000 0001");
        val balanceSecondCardStart = dashboardPage.getCardBalance("5559 0000 0000 0002");
        int sum = (balanceFirstCardStart + balanceSecondCardStart) / 2;

        if (balanceFirstCardStart > balanceSecondCardStart) {
            int difference = balanceFirstCardStart - sum;
            val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0002");
            $(withText("Пополнение карты")).shouldBe(Condition.visible);
            transferPage.transaction(getFirstCardInfo(), difference);
        } else {
            int difference = balanceSecondCardStart - sum;
            val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0001");
            $(withText("Пополнение карты")).shouldBe(Condition.visible);
            transferPage.transaction(getSecondCardInfo(), difference);
        }
    }
}
