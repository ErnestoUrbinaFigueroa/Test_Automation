package cl.empresa.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;

import cl.empresa.qa.helpers.Helper;

public class LoginPage {
	private WebDriver driver;
	private ExtentTest test;
	private Boolean TAKE_SS;
	private By usuarioInput;
	private By passwordInput;
	private By submit;
	private By validadorLoginCorrecto;
	private By validadorLoginErroneo;

	public LoginPage(WebDriver driver, ExtentTest test, Boolean TAKE_SS) {
		this.driver = driver;
		this.test = test;
		this.TAKE_SS = TAKE_SS;
		usuarioInput = By.name("usuario");
		passwordInput = By.name("clave");
		submit = By.name("Submit");
		validadorLoginCorrecto = By.xpath("//*[@id=\"header\"]/tbody/tr/td[1]/h2");// "Men� Rol: usuario"
		validadorLoginErroneo = By.xpath("/html/body/table[2]/tbody/tr[1]/td/b"); // "Su clave/usuario son incorrectos"
	}

	public void loginUser(String user, String pass, String subDir) {
		driver.findElement(usuarioInput).sendKeys(user);
		driver.findElement(passwordInput).sendKeys(pass);

		Helper.addEvidence(TAKE_SS, driver, test, "Evidencia paso 1 : ", subDir, "login_01");

		driver.findElement(submit).click();

		Helper.addEvidence(TAKE_SS, driver, test, "Evidencia paso 2 : ", subDir, "login_02");
	}

	public void assertLoginCorrecto() {
		Assert.assertTrue(driver.findElement(validadorLoginCorrecto).getText().equals("Men� Rol: usuario"));
	}

	public void assertLoginIncorrecto() {
		Assert.assertTrue(
				driver.findElement(validadorLoginErroneo).getText().equals("Su clave/usuario son incorrectos"));
	}
}
