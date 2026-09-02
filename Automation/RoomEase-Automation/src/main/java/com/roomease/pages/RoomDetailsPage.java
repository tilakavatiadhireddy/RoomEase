package com.roomease.pages;

import com.roomease.utils.DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;

public class RoomDetailsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By roomDetailsContainer =
            By.cssSelector(".room-details");

    private By bookRoomButton =
            By.xpath(
                    "//button[contains(translate(text(), " +
                            "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                            "'abcdefghijklmnopqrstuvwxyz')," +
                            "'book')]"
            );

    private By bookingModal =
            By.cssSelector(".modal");

    private By checkInInput =
            By.cssSelector(
                    ".modal input[type='date']:nth-of-type(1)"
            );

    private By checkOutInput =
            By.cssSelector(
                    ".modal input[type='date']:nth-of-type(2)"
            );

    private By allDateInputs =
            By.cssSelector(
                    ".modal input[type='date']"
            );

    private By confirmBookingButton =
            By.cssSelector(
                    ".confirm-btn"
            );

    private By cancelButton =
            By.cssSelector(
                    ".cancel-btn"
            );

    private By unavailableMessage =
            By.xpath(
                    "//div[contains(@class,'modal')]//p[" +
                            "contains(translate(text()," +
                            "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                            "'abcdefghijklmnopqrstuvwxyz')," +
                            "'unavailable')" +
                            "]"
            );

    private By bookingSuccessToast =
            By.xpath(
                    "//*[contains(text(),'Booking Confirmed') " +
                            "or contains(text(),'Booking confirmed') " +
                            "or contains(text(),'Booking successful') " +
                            "or contains(text(),'Booking Successful')]"
            );

    public RoomDetailsPage() {

        driver = DriverFactory.getDriver();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

        js = (JavascriptExecutor) driver;
    }


    // =====================================
    // VERIFY ROOM DETAILS PAGE
    // =====================================

    public boolean isRoomDetailsPage() {

        try {

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            roomDetailsContainer
                    )
            ).isDisplayed();

        } catch (Exception e) {

            System.out.println(
                    "ROOM DETAILS PAGE NOT DISPLAYED"
            );

            return false;
        }
    }


    // =====================================
    // CLICK BOOK ROOM
    // =====================================

    public void clickBookRoom() {

        System.out.println(
                "CLICKING BOOK ROOM BUTTON"
        );

        WebElement button =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                bookRoomButton
                        )
                );

        button.click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        bookingModal
                )
        );

        System.out.println(
                "BOOKING MODAL OPENED"
        );
    }


    // =====================================
    // SET DATE USING JAVASCRIPT
    // =====================================

    private void setDate(
            WebElement input,
            LocalDate date
    ) {

        String value = date.toString();

        js.executeScript(

                "const input = arguments[0];" +

                        "const value = arguments[1];" +

                        "const nativeSetter = Object.getOwnPropertyDescriptor(" +
                        "HTMLInputElement.prototype, 'value').set;" +

                        "nativeSetter.call(input, value);" +

                        "input.dispatchEvent(new Event('input', {" +
                        "bubbles: true }));" +

                        "input.dispatchEvent(new Event('change', {" +
                        "bubbles: true }));",

                input,
                value
        );
    }


    // =====================================
    // SET CHECK-IN AND CHECK-OUT DATES
    // =====================================

    private void setDates(
            LocalDate checkIn,
            LocalDate checkOut
    ) {

        System.out.println(
                "SETTING DATES:"
        );

        System.out.println(
                "CHECK-IN: " + checkIn
        );

        System.out.println(
                "CHECK-OUT: " + checkOut
        );

        WebElement modal =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                bookingModal
                        )
                );

        java.util.List<WebElement> dateInputs =
                modal.findElements(
                        By.cssSelector(
                                "input[type='date']"
                        )
                );

        if (dateInputs.size() < 2) {

            throw new RuntimeException(
                    "Could not find both date inputs"
            );
        }

        setDate(
                dateInputs.get(0),
                checkIn
        );

        waitForReactUpdate();

        setDate(
                dateInputs.get(1),
                checkOut
        );

        waitForReactUpdate();
    }


    // =====================================
    // WAIT FOR UI UPDATE
    // =====================================

    private void waitForReactUpdate() {

        try {

            Thread.sleep(500);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }


    // =====================================
    // CHECK IF ROOM UNAVAILABLE
    // =====================================

    private boolean isRoomUnavailable() {

        try {

            java.util.List<WebElement> messages =
                    driver.findElements(
                            unavailableMessage
                    );

            for (WebElement message : messages) {

                if (message.isDisplayed()) {

                    String text =
                            message.getText()
                                    .toLowerCase();

                    if (text.contains("unavailable")) {

                        System.out.println(
                                "ROOM UNAVAILABLE MESSAGE FOUND: "
                                        + message.getText()
                        );

                        return true;
                    }
                }
            }

            return false;

        } catch (Exception e) {

            return false;
        }
    }


    // =====================================
    // FIND AVAILABLE DATES
    // =====================================

    public boolean findAvailableDates() {

        System.out.println(
                "SEARCHING FOR AVAILABLE DATES..."
        );

        /*
         * Start sufficiently in the future.
         *
         * The application may already contain
         * bookings for dates close to today.
         */

        LocalDate startDate =
                LocalDate.now().plusDays(30);

        /*
         * Try many combinations.
         */

        for (
                int offset = 0;
                offset < 365;
                offset += 3
        ) {

            LocalDate checkIn =
                    startDate.plusDays(offset);

            LocalDate checkOut =
                    checkIn.plusDays(1);

            System.out.println(
                    "TRYING:"
                            + checkIn
                            + " -> "
                            + checkOut
            );

            try {

                setDates(
                        checkIn,
                        checkOut
                );

                waitForReactUpdate();

                /*
                 * If unavailable message appears,
                 * try the next dates.
                 */

                if (isRoomUnavailable()) {

                    System.out.println(
                            "DATES NOT AVAILABLE"
                    );

                    continue;
                }

                /*
                 * Confirm button should now be
                 * available.
                 */

                if (isConfirmBookingAvailable()) {

                    System.out.println(
                            "AVAILABLE DATES FOUND!"
                    );

                    System.out.println(
                            "CHECK-IN: "
                                    + checkIn
                    );

                    System.out.println(
                            "CHECK-OUT: "
                                    + checkOut
                    );

                    return true;
                }

            } catch (Exception e) {

                System.out.println(
                        "FAILED DATE ATTEMPT: "
                                + e.getMessage()
                );
            }
        }

        System.out.println(
                "NO AVAILABLE DATES FOUND"
        );

        return false;
    }


    // =====================================
    // CHECK CONFIRM BUTTON
    // =====================================

    public boolean isConfirmBookingAvailable() {

        try {

            if (isRoomUnavailable()) {

                System.out.println(
                        "CONFIRM BUTTON NOT AVAILABLE " +
                                "BECAUSE ROOM IS UNAVAILABLE"
                );

                return false;
            }

            WebElement button =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    confirmBookingButton
                            )
                    );

            boolean displayed =
                    button.isDisplayed();

            boolean enabled =
                    button.isEnabled();

            System.out.println(
                    "CONFIRM BUTTON DISPLAYED: "
                            + displayed
            );

            System.out.println(
                    "CONFIRM BUTTON ENABLED: "
                            + enabled
            );

            return displayed && enabled;

        } catch (Exception e) {

            System.out.println(
                    "CONFIRM BUTTON NOT AVAILABLE"
            );

            System.out.println(
                    e.getMessage()
            );

            return false;
        }
    }


    // =====================================
    // CONFIRM BOOKING
    // =====================================

    public void confirmBooking() {

        System.out.println(
                "CLICKING CONFIRM BOOKING"
        );

        WebElement button =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                confirmBookingButton
                        )
                );

        /*
         * Normal click first.
         */

        try {

            button.click();

        } catch (Exception e) {

            System.out.println(
                    "NORMAL CLICK FAILED"
            );

            System.out.println(
                    "TRYING JAVASCRIPT CLICK"
            );

            js.executeScript(
                    "arguments[0].click();",
                    button
            );
        }

        System.out.println(
                "CONFIRM BOOKING CLICKED"
        );
    }


    // =====================================
    // VERIFY BOOKING SUCCESS
    // =====================================

    public boolean isBookingSuccessful() {

        try {

            System.out.println(
                    "WAITING FOR BOOKING SUCCESS MESSAGE..."
            );

            WebElement successMessage =
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    bookingSuccessToast
                            )
                    );

            System.out.println(
                    "BOOKING SUCCESS MESSAGE: "
                            + successMessage.getText()
            );

            return successMessage.isDisplayed();

        } catch (Exception e) {

            System.out.println(
                    "BOOKING SUCCESS MESSAGE NOT FOUND"
            );

            System.out.println(
                    e.getMessage()
            );

            return false;
        }
    }


    // =====================================
    // WAIT FOR BOOKING MODAL TO CLOSE
    // =====================================

    public void waitForBookingModalToClose() {

        try {

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            bookingModal
                    )
            );

            System.out.println(
                    "BOOKING MODAL CLOSED"
            );

        } catch (Exception e) {

            System.out.println(
                    "BOOKING MODAL DID NOT CLOSE"
            );
        }
    }
}