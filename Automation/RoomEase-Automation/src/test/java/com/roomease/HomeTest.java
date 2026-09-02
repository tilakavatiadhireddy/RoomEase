package com.roomease;

import com.roomease.base.BaseTest;
import com.roomease.pages.HomePage;
import com.roomease.pages.NavbarPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {

    @Test
    public void verifyRoomsAreDisplayed() {

        HomePage homePage =
                new HomePage();

        homePage.open();

        Assert.assertTrue(
                homePage.areRoomsDisplayed(),
                "Rooms should be displayed"
        );
    }

    @Test
    public void verifyUserCanOpenRoomDetails() {

        HomePage homePage =
                new HomePage();

        homePage.open();

        homePage.clickFirstRoom();

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains("/room/"),
                "Room details page should open"
        );
    }

    @Test
    public void verifyAboutPageOpens() {

        HomePage homePage =
                new HomePage();

        NavbarPage navbar =
                new NavbarPage();

        homePage.open();

        navbar.clickAbout();

        Assert.assertTrue(
                driver.getCurrentUrl()
                        .contains("/about"),
                "About page should open"
        );
    }
}