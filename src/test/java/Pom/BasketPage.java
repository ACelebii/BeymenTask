package Pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasketPage {
    public BasketPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//span[@class='priceBox__salePrice']")
    public WebElement priceOfBasket;

    @FindBy(id= "quantitySelect0-key-0")
    public WebElement clickNumberOfProductDropdown;

    @FindBy(id= "removeCartItemBtn0-key-0")
    public WebElement removeProductFromBasketButton;

    @FindBy(xpath= "//strong[text()='Sepetinizde Ürün Bulunmamaktadır']")
    public WebElement sepetinizdeUrunBulunmamaktadirText;



}
