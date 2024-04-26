package Pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage {

    public ProductPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

   @FindBy(xpath = "//div[@class='m-productCard']")
    public List<WebElement> ListOfShirts;

}
