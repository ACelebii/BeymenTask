package Test;

import Pom.BasketPage;
import Pom.HomePage;
import Pom.ProductPage;
import Pom.SelectProductPage;
import Utilities.BaseDriver;
import Utilities.ReadData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.manipulation.Alphanumeric;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

@OrderWith(Alphanumeric.class)
public class E2EProductTest extends BaseDriver {

    WebDriverWait wait;

    HomePage homePage;
    ReadData readData;
    ProductPage productPage;
    SelectProductPage selectProductPage;
    BasketPage basketPage;
    String numberOfExpectedProduct = "2 adet";
    String expectedSting ="SEPETINIZDE ÜRÜN BULUNMAMAKTADIR" ;
    private static final Logger logger = LogManager.getLogger(E2EProductTest.class);



    @BeforeClass
    public static void setUp() {
        initializeDriver();
    }


    @Test
    public void T1_closePopUpTest() throws InterruptedException {
        driver.get(URL);
        logger.info("Browser is launched");
        homePage = new HomePage(driver);

        Thread.sleep(2000);
        try {
            homePage.acceptAllButton.click();
            logger.info("PopUp is closed with button");

        } catch (NoSuchElementException ignored) {
        }

    }

    @Test

    public void T2_closeUpdatePopUp() throws InterruptedException {

        homePage = new HomePage(driver);
        Thread.sleep(4000);
        homePage.noThanksButtonForUpdatePopUp.click();

    }
    @Test
    public void T3_closeGenderPopUp() throws InterruptedException {
        homePage = new HomePage(driver);
        Thread.sleep(2000);
        homePage.closeGenderChoosePopUp.click();
    }
    @Test
    public void T4_enterAndDeleteSortOnSearchBar() throws InterruptedException {
        homePage = new HomePage(driver);
        readData = new ReadData(driver);
        Thread.sleep(4000);
        homePage.searchField.click();
        homePage.vazgecButton.isDisplayed();
        homePage.searchField2.sendKeys(readData.getKeywordShort());
        logger.info("data is read with readExcelData");

        Thread.sleep(4000);
        homePage.silButtonOfSearchBar.click();


        homePage.searchField2.sendKeys(readData.getKeywordShirt());
        logger.info("data is read with readExcelData");
        homePage.searchField2.sendKeys(Keys.ENTER);
    }

    @Test
    public void T5_chooseRandomShirt() throws InterruptedException {
        productPage= new ProductPage(driver);
        int sizeOfShirtList = productPage.ListOfShirts.size();

        Random random = new Random();
        int randomIndex = random.nextInt(sizeOfShirtList);

        WebElement randomProduct = productPage.ListOfShirts.get(randomIndex);
        Thread.sleep(4000);
        randomProduct.click();
        Thread.sleep(4000);
    }

    @Test
    public void T6_writingValuesOnTxt() throws InterruptedException {
        selectProductPage = new SelectProductPage(driver);

        Thread.sleep(7000);
        String productName = selectProductPage.choosenProductDescription.getText();
        String productPrice = selectProductPage.choosenProductPrice.getText();


        try (FileWriter writer = new FileWriter("product_info.txt")) {
            writer.write("Ürün Adı: " + productName + "\n");
            writer.write("Ürün Fiyatı: " + productPrice + "\n");
            logger.info("Txt file is created and added values");

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @Test
    public void T7_addToBasketTest() throws InterruptedException {
        selectProductPage = new SelectProductPage(driver);

        Thread.sleep(1500);
        if(ExpectedConditions.visibilityOf(selectProductPage.sizeOfStocks).apply(driver) != null) {
            selectProductPage.sizeOfStocks.click();
            logger.info("sizeOfStocks is checked and clicked it");
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(selectProductPage.sizeOfCriticalStocks));
            selectProductPage.sizeOfCriticalStocks.click();
            logger.info("sizeOfCriticalStocks is checked and clicked it");
        }

        Thread.sleep(4000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();",selectProductPage.addToBasketButton);
        Assert.assertTrue(selectProductPage.addToBasketButton.isDisplayed());
        selectProductPage.addToBasketButton.click();
    }

    @Test
    public void T8_comparingPricesTest() throws InterruptedException {
        selectProductPage = new SelectProductPage(driver);
        basketPage = new BasketPage(driver);
        Thread.sleep(4000);
        selectProductPage.basketIcon.click();
        Thread.sleep(3000);
        String basketPrice = basketPage.priceOfBasket.getText();

        String filePath = "product_info.txt";

            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        logger.info("TXT file is read and value is manipulated ");
             String manipulationOfTxTfile =stringBuilder.toString().replaceAll("[^\\d.]", "");

             String productPrice= manipulationOfTxTfile + " TL";

             String priceWithoutZero = basketPrice.replaceAll(",00|\\.00", "");

             Assert.assertEquals(priceWithoutZero, productPrice);

    }

    @Test
    public void T9_comparingPricesAfterAddingMoreTest() throws InterruptedException {
        basketPage = new BasketPage(driver);

        Select dropdown = new Select(basketPage.clickNumberOfProductDropdown);
        dropdown.selectByIndex(1);

        WebElement selectedOption = dropdown.getFirstSelectedOption();
        String numberOfProduct = selectedOption.getText();
        Assert.assertEquals(numberOfExpectedProduct, numberOfProduct);
        Thread.sleep(4000);
    }
    @Test
    public void TX_removeProductTest() throws InterruptedException {
        basketPage = new BasketPage(driver);

        basketPage.removeProductFromBasketButton.click();
        Thread.sleep(1500);
        String actualString = basketPage.sepetinizdeUrunBulunmamaktadirText.getText();
        Assert.assertEquals(actualString,expectedSting);
    }
}
