package com.roomease.pages;

import com.roomease.utils.DriverFactory;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By nameInput = By.name("name");
    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");

    private By registerButton =
            By.cssSelector("button[type='submit']");

    private By toastMessage =
            By.cssSelector(".Toastify__toast");

    public RegisterPage() {

        driver = DriverFactory.getDriver();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void open() {

        driver.get("http://localhost:5173/register");
    }

    public void register(
            String name,
            String email,
            String password
    ) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(nameInput)
        ).clear();

        driver.findElement(nameInput)
                .sendKeys(name);

        driver.findElement(emailInput)
                .clear();

        driver.findElement(emailInput)
                .sendKeys(email);

        driver.findElement(passwordInput)
                .clear();

        driver.findElement(passwordInput)
                .sendKeys(password);

        wait.until(
                ExpectedConditions.elementToBeClickable(registerButton)
        ).click();
    }

    public String getToastMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        toastMessage
                )
        ).getText();
    }

    public boolean isRegistrationSuccessful() {

        try {

            String message =
                    getToastMessage().toLowerCase();

            return message.contains("success")
                    || message.contains("registered")
                    || message.contains("created");

        } catch (Exception e) {

            return false;
        }
    }
}