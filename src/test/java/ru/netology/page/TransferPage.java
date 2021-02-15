package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement sumField = $("[data-test-id=\"amount\"]   input");
    private SelenideElement fromCard = $("[data-test-id=\"from\"]  input");
    private SelenideElement buttonDeposit = $("[data-test-id=\"action-transfer\"]");

    public TransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public DashboardPage transaction(DataHelper.Card card, int sum) {
        sumField.setValue(String.valueOf(sum));
        fromCard.setValue(card.getNumber());
        buttonDeposit.click();
        return new DashboardPage();
    }
}
