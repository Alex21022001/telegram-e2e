package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Path;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("https://web.telegram.org/");
            page.waitForSelector("canvas");
            System.out.println("QRCODE");
            page.waitForCondition(() -> page.locator("#column-left").isVisible());
            page.screenshot(new Page.ScreenshotOptions().setPath(Path.of("page.png")));
            System.out.println("You successfully logged in");

            context.storageState(new BrowserContext.StorageStateOptions().setPath(Path.of("auth.json")));

            context.close();
            browser.close();
        }
    }
}
