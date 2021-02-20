package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.data.DataHelper.getFirstCardInfo;
import static ru.netology.data.DataHelper.getSecondCardInfo;

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

    public void fillingFieldsSecondCard(int sum) {
        $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=from] input").setValue(getSecondCardInfo().getNumber());
        $("[data-test-id=amount] input").setValue(String.valueOf(sum));
    }

    public void fillingFieldsFirstCard(int sum) {
        $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=from] input").setValue(getFirstCardInfo().getNumber());
        $("[data-test-id=amount] input").setValue(String.valueOf(sum));
    }

    public void moneyTransferError() {
        $(withText("Недостаточно средств!")).shouldBe(Condition.visible);
    }
}
