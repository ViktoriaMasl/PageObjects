package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;

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

    @Test
    void shouldTransferOnFirstCard() {
        val dashboardPage = new DashboardPage();
        val sum = 5000;
        val balanceFirstCardStart = dashboardPage.extractFirstCardBalance();
        val balanceSecondCardStart = dashboardPage.extractSecondCardBalance();
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0001");
        transferPage.fillingFieldsSecondCard(sum);
        transferPage.transaction(getFirstCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractFirstCardBalance() + sum;
        val balanceSecondCardFinish = dashboardPage.extractSecondCardBalance() - sum;
        assertEquals(balanceFirstCardStart + sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart - sum, balanceSecondCardFinish);
    }

    @Test
    void shouldTransferZeroOnFirstCard() {
        val dashboardPage = new DashboardPage();
        val sum = 0;
        val balanceFirstCardStart = dashboardPage.extractFirstCardBalance();
        val balanceSecondCardStart = dashboardPage.extractSecondCardBalance();
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0001");
        transferPage.fillingFieldsSecondCard(sum);
        transferPage.transaction(getFirstCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractFirstCardBalance() + sum;
        val balanceSecondCardFinish = dashboardPage.extractSecondCardBalance() - sum;
        assertEquals(balanceFirstCardStart + sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart - sum, balanceSecondCardFinish);
    }

    //проверка на пополнение счета сверх лимита донорской карты
    @Test
    void shouldTransferOverLimitOnFirstCard() {
        val dashboardPage = new DashboardPage();
        val sum = 11000;
        val balanceFirstCardStart = dashboardPage.extractFirstCardBalance();
        val balanceSecondCardStart = dashboardPage.extractSecondCardBalance();
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0001");
        transferPage.fillingFieldsSecondCard(sum);
        transferPage.transaction(getFirstCardInfo(), sum);
        transferPage.moneyTransferError();
    }

    @Test
    void shouldTransferMaxOnFirstCard() {
        val dashboardPage = new DashboardPage();
        val sum = 10000;
        val balanceFirstCardStart = dashboardPage.extractFirstCardBalance();
        val balanceSecondCardStart = dashboardPage.extractSecondCardBalance();
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0001");
        transferPage.fillingFieldsSecondCard(sum);
        transferPage.transaction(getFirstCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractFirstCardBalance() + sum;
        val balanceSecondCardFinish = dashboardPage.extractSecondCardBalance() - sum;
        assertEquals(balanceFirstCardStart + sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart - sum, balanceSecondCardFinish);
    }

    @Test
    void shouldTransferOnSecondCard() {
        val dashboardPage = new DashboardPage();
        val sum = 5000;
        val balanceFirstCardStart = dashboardPage.extractFirstCardBalance();
        val balanceSecondCardStart = dashboardPage.extractSecondCardBalance();
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0002");
        transferPage.fillingFieldsFirstCard(sum);
        transferPage.transaction(getSecondCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractFirstCardBalance() - sum;
        val balanceSecondCardFinish = dashboardPage.extractSecondCardBalance() + sum;
        assertEquals(balanceFirstCardStart - sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart + sum, balanceSecondCardFinish);
    }

    @Test
    void shouldTransferZeroOnSecondCard() {
        val dashboardPage = new DashboardPage();
        val sum = 0;
        val balanceFirstCardStart = dashboardPage.extractFirstCardBalance();
        val balanceSecondCardStart = dashboardPage.extractSecondCardBalance();
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0002");
        transferPage.fillingFieldsFirstCard(sum);
        transferPage.transaction(getSecondCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractFirstCardBalance() - sum;
        val balanceSecondCardFinish = dashboardPage.extractSecondCardBalance() + sum;
        assertEquals(balanceFirstCardStart - sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart + sum, balanceSecondCardFinish);
    }

    @Test
    void shouldTransferMaxOnSecondCard() {
        val dashboardPage = new DashboardPage();
        val sum = 10000;
        val balanceFirstCardStart = dashboardPage.extractFirstCardBalance();
        val balanceSecondCardStart = dashboardPage.extractSecondCardBalance();
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0002");
        transferPage.fillingFieldsFirstCard(sum);
        transferPage.transaction(getSecondCardInfo(), sum);
        val balanceFirstCardFinish = dashboardPage.extractFirstCardBalance() - sum;
        val balanceSecondCardFinish = dashboardPage.extractSecondCardBalance() + sum;
        assertEquals(balanceFirstCardStart - sum, balanceFirstCardFinish);
        assertEquals(balanceSecondCardStart + sum, balanceSecondCardFinish);
    }

    //проверка на пополнение счета сверх лимита донорской карты
    @Test
    void shouldTransferOverLimitOnSecondCard() {
        val dashboardPage = new DashboardPage();
        val sum = 11000;
        val balanceFirstCardStart = dashboardPage.extractFirstCardBalance();
        val balanceSecondCardStart = dashboardPage.extractSecondCardBalance();
        val transferPage = dashboardPage.selectCardButton("5559 0000 0000 0002");
        transferPage.fillingFieldsFirstCard(sum);
        transferPage.transaction(getSecondCardInfo(), sum);
        transferPage.moneyTransferError();
    }
}
