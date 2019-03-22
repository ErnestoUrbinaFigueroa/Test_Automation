package cl.empresa.qa.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cl.empresa.qa.pages.LoginPage;

public class TestSistemaHorarios {
	private WebDriver driver;
	private ExtentReports extent;
	private ExtentTest test;
	private static String SUBDIR = "Ambiente_Base_Ta/";
	private static Boolean TAKE_SS = true;

	@BeforeSuite
	public void configExtentReports() {
		// ExtentReports config
		this.extent = new ExtentReports("ExtentReports/AMBIENTE_BASE_TA.html", true);
	}

	@BeforeMethod
	public void configSelenium() {
		// Selenium config
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("Empresa", "Tecnova");
//		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.navigate().to("http://horarios.tecnova.cl/");
	}

	@Test
	public void pruebaLoginCorrecto() {
		String subDir = SUBDIR + Thread.currentThread().getStackTrace()[1].getMethodName();
		test = extent.startTest("Validar Login Correcto", "Prueba del Login Ingresado Correctamente");
		test.log(LogStatus.INFO, "Prueba Login Correcto.-");
		LoginPage login = new LoginPage(driver, test, TAKE_SS);

		login.loginUser("eurbinaf", "1507euf", subDir);
		login.assertLoginCorrecto();
	}

	@Test
	public void pruebaLoginIncorrecto() {
		String subDir = SUBDIR + Thread.currentThread().getStackTrace()[1].getMethodName();
		test = extent.startTest("Validar Login Incorrecto", "Prueba del Login Ingresado Incorrectamente");
		test.log(LogStatus.INFO, "Prueba Login Incorrecto.-");
		LoginPage login = new LoginPage(driver, test, TAKE_SS);

		login.loginUser("123123123", "XXXX", subDir);
		login.assertLoginIncorrecto();
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test failed.- <br>" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test skipped.- <br>" + result.getThrowable());
		} else {
			test.log(LogStatus.PASS, "Test passed.-");
		}
		driver.close();
		extent.endTest(test);
	}

	@AfterSuite
	public void closeExtentReports() {
		// writing everything to document.
		extent.flush();
	}
}
