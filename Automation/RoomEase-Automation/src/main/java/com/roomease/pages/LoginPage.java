package com.roomease.pages;

import com.roomease.utils.DriverFactory;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By emailInput =
            By.name("email");

    private By passwordInput =
            By.name("password");

    private By loginButton =
            By.cssSelector("button[type='submit']");

    private By toastMessage =
            By.cssSelector(".Toastify__toast");

    public LoginPage() {

        driver = DriverFactory.getDriver();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void open() {

        driver.get("http://localhost:5173/login");
    }

    public void login(
            String email,
            String password
    ) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        emailInput
                )
        ).clear();

        driver.findElement(emailInput)
                .sendKeys(email);

        driver.findElement(passwordInput)
                .clear();

        driver.findElement(passwordInput)
                .sendKeys(password);

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        loginButton
                )
        ).click();
    }

    public String getToastMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        toastMessage
                )
        ).getText();
    }

    public boolean isLoginSuccessful() {

        try {

            wait.until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "/login"
                            )
                    )
            );

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}