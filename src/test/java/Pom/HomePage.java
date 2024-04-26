package Pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {


    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//button[@id='onetrust-reject-all-handlerr']")
    public WebElement acceptAllButton;

    @FindBy(css = ".icon-close[width='24px']")
    public WebElement closeGenderChoosePopUp;

    @FindBy(id ="onetrust-reject-all-handler")
    public WebElement noThanksButtonForUpdatePopUp;


    @FindBy(css="[placeholder='Ürün, Marka Arayın']")
    public WebElement searchField;

    @FindBy(id="o-searchSuggestion__input")
    public WebElement searchField2;

    @FindBy(css=".o-header__search--close")
    public WebElement silButtonOfSearchBar;


    @FindBy(css=".o-header__form--close")
   public WebElement vazgecButton;









}
