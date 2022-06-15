package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String shouldSetDate(int numberDays) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, numberDays);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(currentDate.getTime());
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");}

    @Test
    void shouldSubmitOrder() {
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(shouldSetDate(10));
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71231231212");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldSubmitComplexOrder() {
        $("[placeholder='Город']").setValue("са");
        $$(".menu-item__control").find(exactText("Санкт-Петербург")).click();
        Calendar currentDate = Calendar.getInstance();
        Calendar newDate = Calendar.getInstance();
        newDate.add(Calendar.DATE, 10);
        String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
        String month = monthNames[newDate.get(Calendar.MONTH)];
        $(".icon_name_calendar").click();
        String monthInCalendar = $(".calendar__name").getText();
        if (!monthInCalendar.contains(month)) {
            $(".calendar__title [data-step='1']").click();
        }
        String day = Integer.toString(newDate.get(Calendar.DATE));
        $$(".calendar__day").find(exactText(day)).click();
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71231231212");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}