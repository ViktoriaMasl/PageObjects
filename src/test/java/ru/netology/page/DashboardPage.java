package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item [data-test-id]");
    private SelenideElement buttonCardOne = $("[data-test-id = \"92df3f1c-a033-48e6-8390-206f6b1f56c0\"] " +
            ">[data-test-id=action-deposit]");
    private SelenideElement buttonCardTwo = $("[data-test-id = \"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"] " +
            "> [data-test-id=action-deposit]");
    private String numberFirstCard = "5559 0000 0000 0001";
    private String numberSecondCard = "5559 0000 0000 0002";
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public TransferPage selectCardButton(String numberCard) {
        if (numberCard == numberFirstCard) {
            buttonCardOne.click();
        }
        if (numberCard == numberSecondCard) {
            buttonCardTwo.click();
        }
        return new TransferPage();
    }

    ;

    public int getCardBalance(String numberCard) {
        String balance = "";

        if (numberCard == numberFirstCard) {
            val cardBalance = $(".list__item [data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").getText();
            balance = cardBalance.toString();
        }
        if (numberCard == numberSecondCard) {
            val cardBalance = $(".list__item [data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").getText();
            balance = cardBalance.toString();
        }
        return extractBalance(balance);
    }

    public int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
