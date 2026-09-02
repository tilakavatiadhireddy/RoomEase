package com.roomease;

import com.roomease.base.BaseTest;
import com.roomease.pages.HomePage;
import com.roomease.pages.LoginPage;
import com.roomease.pages.RegisterPage;
import com.roomease.pages.RoomDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookingTest extends BaseTest {

    @Test
    public void verifyUserCanPrepareRoomBooking() {

        // =========================
        // CREATE UNIQUE USER
        // =========================

        String email =
                "booking"
                        + System.currentTimeMillis()
                        + "@test.com";

        System.out.println(
                "REGISTERING USER: " + email
        );

        // =========================
        // REGISTER
        // =========================

        RegisterPage registerPage =
                new RegisterPage();

        registerPage.open();

        registerPage.register(
                "Booking User",
                email,
                "password123"
        );

        Assert.assertTrue(
                registerPage.isRegistrationSuccessful(),
                "User registration should be successful"
        );

        System.out.println(
                "REGISTRATION SUCCESSFUL"
        );

        // =========================
        // LOGIN
        // =========================

        LoginPage loginPage =
                new LoginPage();

        loginPage.open();

        loginPage.login(
                email,
                "password123"
        );

        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "User should login successfully"
        );

        System.out.println(
                "LOGIN SUCCESSFUL"
        );

        // =========================
        // OPEN ROOM
        // =========================

        HomePage homePage =
                new HomePage();

        Assert.assertTrue(
                homePage.areRoomsDisplayed(),
                "Rooms should be displayed"
        );

        homePage.clickFirstRoom();

        System.out.println(
                "FIRST ROOM OPENED"
        );

        // =========================
        // ROOM DETAILS
        // =========================

        RoomDetailsPage roomDetails =
                new RoomDetailsPage();

        Assert.assertTrue(
                roomDetails.isRoomDetailsPage(),
                "Room details page should open"
        );

        System.out.println(
                "ROOM DETAILS PAGE OPENED"
        );

        // =========================
        // OPEN BOOKING MODAL
        // =========================

        roomDetails.clickBookRoom();

        System.out.println(
                "BOOK ROOM BUTTON CLICKED"
        );

        // =========================
        // FIND AVAILABLE DATES
        // =========================

        boolean datesAvailable =
                roomDetails.findAvailableDates();

        Assert.assertTrue(
                datesAvailable,
                "Could not find available dates for this room"
        );

        System.out.println(
                "AVAILABLE DATES FOUND"
        );

        // =========================
        // VERIFY CONFIRM BUTTON
        // =========================

        Assert.assertTrue(
                roomDetails.isConfirmBookingAvailable(),
                "Confirm Booking button should be available"
        );

        System.out.println(
                "CONFIRM BOOKING BUTTON AVAILABLE"
        );

        System.out.println(
                "================================"
        );

        System.out.println(
                "BOOKING PREPARATION TEST PASSED"
        );

        System.out.println(
                "================================"
        );
    }
}