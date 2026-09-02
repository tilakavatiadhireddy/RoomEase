package com.roomease.pages;

import com.roomease.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NavbarPage {

    private WebDriver driver;

    private WebDriverWait wait;

    private By homeLink = By.linkText("Home");

    private By aboutLink = By.linkText("About");

    private By loginLink = By.linkText("Login");

    private By registerLink = By.linkText("Register");

    private By myBookingsLink = By.linkText("My Bookings");

    private By logoutButton = By.cssSelector(".logout-btn");

    private By modalOverlay = By.cssSelector(".modal-overlay");

    private By toastMessage = By.cssSelector(".Toastify__toast");

    public NavbarPage() {

        driver = DriverFactory.getDriver();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void clickHome() {

        wait.until(
                ExpectedConditions.elementToBeClickable(homeLink)
        ).click();
    }

    public void clickAbout() {

        wait.until(
                ExpectedConditions.elementToBeClickable(aboutLink)
        ).click();
    }

    public void clickLogin() {

        wait.until(
                ExpectedConditions.elementToBeClickable(loginLink)
        ).click();
    }

    public void clickRegister() {

        wait.until(
                ExpectedConditions.elementToBeClickable(registerLink)
        ).click();
    }

  public void clickMyBookings() {

    WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
    );

    By myBookingsLink = By.cssSelector("a[href='/my-bookings']");

    // Wait for any Toastify notification to disappear
    try {

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.cssSelector(".Toastify__toast")
                )
        );

    } catch (Exception ignored) {

        // If no toast exists, continue normally
    }

    // Wait until My Bookings link is clickable
    wait.until(
            ExpectedConditions.elementToBeClickable(myBookingsLink)
    );

    driver.findElement(myBookingsLink).click();
}

    public boolean isLogoutButtonVisible() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        logoutButton
                )
        ).isDisplayed();
    }

    public void logout() {

        // Wait for success toast to disappear
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        toastMessage
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        logoutButton
                )
        ).click();
    }
}