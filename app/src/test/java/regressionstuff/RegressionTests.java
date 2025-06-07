package regressionstuff;

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class RegressionTests {

    @Test
    @Description ("The task that was given to me")
    public void testTask() {
        Allure.step("Go to IMDB");
        open("https://www.imdb.com/");

        Allure.step("Close the consent banner");
        $("[data-testid='consent-banner']").should(appear);
        $("[data-testid='reject-button']").shouldBe(visible, enabled).click();
        
        Allure.step("Search and save the first movie title from the dropdown");
        $(byXpath("//input[@id='suggestion-search']")).setValue("QA");
        String xpathFirstMovieTitle = "//a[contains(@href, '/title/')][1]//div[contains(@class, 'constTitle')]";
        String firstMovieTitleName = $(byXpath(xpathFirstMovieTitle)).getText();

        Allure.step("Click the first movie in the dropdown");
        $(byXpath(xpathFirstMovieTitle)).click();
        assertTrue(title().contains(firstMovieTitleName));

        Allure.step("Check the top cast section for at least 4 links, click 3rd link");
        //note: javascript click seems to help reduce flakiness
        $(byXpath("//a[contains(@href, 'tt_cst_t_4')]")).scrollTo().shouldBe(visible);
        SelenideElement element = $(byXpath("//a[contains(@href, 'tt_cst_t_3')]"));
        element.scrollTo();
        executeJavaScript("arguments[0].click();", element);

        Allure.step("Check that the correct page was opened");
        String actorsName = element.getText();
        assertTrue(title().contains(actorsName));
}

}




