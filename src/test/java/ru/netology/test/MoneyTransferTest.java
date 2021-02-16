package ru.netology.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.getFirstCardInfo;
import static ru.netology.data.DataHelper.getSecondCardInfo;


public class MoneyTransferTest {

    @BeforeEach
    void login() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
        adjustmentOfTheBalance();
    }

    void adjustmentOfTheBalance() {
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


    @Test
    void shouldTransferOnFirstCard() {
        val dashboardPage = new DashboardPage();
        val sum = 5000;
        val balanceFirstCardStart = dashboardPage.getCardBalance("5559 0000 0000 0001");
        val balanceSecondCardStart = dashboardPage.getCardBalance("5559 0000 0000 0002");
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0001");
        $(withText("Пополнение карты")).shouldBe(Condition.visible);
        $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=from] input").setValue(getSecondCardInfo().getNumber());
        $("[data-test-id=amount] input").setValue(String.valueOf(sum));
        transferPage.transaction(getFirstCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractBalance($("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]").text()) + sum;
        val balanceSecondCardFinish = dashboardPage.extractBalance($("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]").text()) - sum;
        assertEquals(balanceFirstCardStart + sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart - sum, balanceSecondCardFinish);
    }

    @Test
    void shouldTransferZeroOnFirstCard() {
        val dashboardPage = new DashboardPage();
        val sum = 0;
        val balanceFirstCardStart = dashboardPage.getCardBalance("5559 0000 0000 0001");
        val balanceSecondCardStart = dashboardPage.getCardBalance("5559 0000 0000 0002");
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0001");
        $(withText("Пополнение карты")).shouldBe(Condition.visible);
        $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=from] input").setValue(getSecondCardInfo().getNumber());
        $("[data-test-id=amount] input").setValue(String.valueOf(sum));
        transferPage.transaction(getFirstCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractBalance($("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]").text()) + sum;
        val balanceSecondCardFinish = dashboardPage.extractBalance($("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]").text()) - sum;
        assertEquals(balanceFirstCardStart + sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart - sum, balanceSecondCardFinish);
    }

//проверка на пополнение счета сверх лимита донорской карты
//    @Test
//    void shouldTransferOverLimitOnFirstCard() {
//        val dashboardPage = new DashboardPage();
//        val sum = 11000;
//    val balanceFirstCardStart = dashboardPage.getCardBalance("5559 0000 0000 0001");
//    val balanceSecondCardStart = dashboardPage.getCardBalance("5559 0000 0000 0002");
//    val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0001");
//    $(withText("Пополнение карты")).shouldBe(Condition.visible);
//    $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
//    $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
//    $("[data-test-id=from] input").setValue(getSecondCardInfo().getNumber());
//    $("[data-test-id=amount] input").setValue(String.valueOf(sum));
//        transferPage.transaction(getFirstCardInfo(), sum);
//        $(withText("Недостаточно средств!")).shouldBe(Condition.visible);
//    }

    @Test
    void shouldTransferMaxOnFirstCard() {
        val dashboardPage = new DashboardPage();
        val sum = 10000;
        val balanceFirstCardStart = dashboardPage.getCardBalance("5559 0000 0000 0001");
        val balanceSecondCardStart = dashboardPage.getCardBalance("5559 0000 0000 0002");
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0001");
        $(withText("Пополнение карты")).shouldBe(Condition.visible);
        $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=from] input").setValue(getSecondCardInfo().getNumber());
        $("[data-test-id=amount] input").setValue(String.valueOf(sum));
        transferPage.transaction(getFirstCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractBalance($("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]").text()) + sum;
        val balanceSecondCardFinish = dashboardPage.extractBalance($("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]").text()) - sum;
        assertEquals(balanceFirstCardStart + sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart - sum, balanceSecondCardFinish);
    }

    @Test
    void shouldTransferOnSecondCard() {
        val dashboardPage = new DashboardPage();
        val sum = 5000;
        val balanceFirstCardStart = dashboardPage.getCardBalance("5559 0000 0000 0001");
        val balanceSecondCardStart = dashboardPage.getCardBalance("5559 0000 0000 0002");
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0002");
        $(withText("Пополнение карты")).shouldBe(Condition.visible);
        $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=from] input").setValue(getFirstCardInfo().getNumber());
        $("[data-test-id=amount] input").setValue(String.valueOf(sum));
        transferPage.transaction(getSecondCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractBalance($("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]").text()) - sum;
        val balanceSecondCardFinish = dashboardPage.extractBalance($("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]").text()) + sum;
        assertEquals(balanceFirstCardStart - sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart + sum, balanceSecondCardFinish);
    }

    @Test
    void shouldTransferZeroOnSecondCard() {
        val dashboardPage = new DashboardPage();
        val sum = 0;
        val balanceFirstCardStart = dashboardPage.getCardBalance("5559 0000 0000 0001");
        val balanceSecondCardStart = dashboardPage.getCardBalance("5559 0000 0000 0002");
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0002");
        $(withText("Пополнение карты")).shouldBe(Condition.visible);
        $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=from] input").setValue(getFirstCardInfo().getNumber());
        $("[data-test-id=amount] input").setValue(String.valueOf(sum));
        transferPage.transaction(getSecondCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractBalance($("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]").text()) - sum;
        val balanceSecondCardFinish = dashboardPage.extractBalance($("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]").text()) + sum;
        assertEquals(balanceFirstCardStart - sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart + sum, balanceSecondCardFinish);
    }

    @Test
    void shouldTransferMaxOnSecondCard() {
        val dashboardPage = new DashboardPage();
        val sum = 10000;
        val balanceFirstCardStart = dashboardPage.getCardBalance("5559 0000 0000 0001");
        val balanceSecondCardStart = dashboardPage.getCardBalance("5559 0000 0000 0002");
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0002");
        $(withText("Пополнение карты")).shouldBe(Condition.visible);
        $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
        $("[data-test-id=from] input").setValue(getFirstCardInfo().getNumber());
        $("[data-test-id=amount] input").setValue(String.valueOf(sum));
        transferPage.transaction(getSecondCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractBalance($("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]").text()) - sum;
        val balanceSecondCardFinish = dashboardPage.extractBalance($("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]").text()) + sum;
        assertEquals(balanceFirstCardStart - sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart + sum, balanceSecondCardFinish);
    }

    //проверка на пополнение счета сверх лимита донорской карты
//    @Test
//    void shouldTransferOverLimitOnSecondCard() {
//        val dashboardPage = new DashboardPage();
//        val sum = 11000;
//    val balanceFirstCardStart = dashboardPage.getCardBalance("5559 0000 0000 0001");
//    val balanceSecondCardStart = dashboardPage.getCardBalance("5559 0000 0000 0002");
//    val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0002");
//    $(withText("Пополнение карты")).shouldBe(Condition.visible);
//    $("[data-test-id=from] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
//    $("[data-test-id=amount] input").sendKeys((Keys.CONTROL + "A" + Keys.DELETE));
//    $("[data-test-id=from] input").setValue(getFirstCardInfo().getNumber());
//    $("[data-test-id=amount] input").setValue(String.valueOf(sum));
//        transferPage.transaction(getSecondCardInfo(), sum);
//        $(withText("Недостаточно средств!")).shouldBe(Condition.visible);
//    }
}
