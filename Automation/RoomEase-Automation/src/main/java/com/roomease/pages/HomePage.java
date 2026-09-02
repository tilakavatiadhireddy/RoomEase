package com.roomease.pages;

import com.roomease.utils.DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By roomCards =
            By.cssSelector(".room-card");

    private By firstRoomButton =
            By.cssSelector(
                    ".room-card a, .room-card button"
            );


    public HomePage() {

        driver = DriverFactory.getDriver();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }


    public void open() {

        driver.get(
                "http://localhost:5173/"
        );
    }


    public boolean areRoomsDisplayed() {

        try {

            List<WebElement> rooms =
                    wait.until(
                            ExpectedConditions
                                    .visibilityOfAllElementsLocatedBy(
                                            roomCards
                                    )
                    );

            return rooms.size() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Rooms were not displayed."
            );

            return false;
        }
    }


    public void clickFirstRoom() {

        List<WebElement> rooms =
                wait.until(
                        ExpectedConditions
                                .visibilityOfAllElementsLocatedBy(
                                        roomCards
                                )
                );

        System.out.println(
                "ROOM CARDS FOUND: "
                        + rooms.size()
        );


        List<WebElement> buttons =
                driver.findElements(
                        firstRoomButton
                );

        System.out.println(
                "CLICKABLE ELEMENTS FOUND: "
                        + buttons.size()
        );


        if (buttons.isEmpty()) {

            throw new RuntimeException(
                    "No clickable room element found. "
                            + "Current URL: "
                            + driver.getCurrentUrl()
            );
        }


        WebElement firstButton =
                buttons.get(0);


        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                firstButton
                        )
        ).click();
    }
}