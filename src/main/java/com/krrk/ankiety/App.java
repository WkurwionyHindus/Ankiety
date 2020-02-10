package com.krrk.ankiety;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {
	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		System.out.println("Jakiej przegladarki uzywasz? (C)chrome, (F)Firefox:");

		String browser = sc.nextLine();
		WebDriver driver = null;
		
		if(browser.toLowerCase().equals("c")) {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
		} else if(browser.toLowerCase().equals("f")) {
			System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.out.println("Podano zły znak");
			System.in.read();
			System.exit(0);
		}
		
	    WebDriverWait wait = new WebDriverWait(driver, 10);
		
	    List<WebElement> frekwencja = null;
	    List<WebElement> opcjaPierwsza;
	    List<WebElement> opcjaDruga;
	    List<WebElement> opcjaTrzecia;
	    
	    WebElement ank;
	    WebElement ankieta;
		WebElement ankiety;
	    
		String baseUrl = "https://edziekanat.zut.edu.pl/WU/";
		String login = "";
		String pass = "";

		int choose = 0;
		
		
		System.out.println("==================================================");
		System.out.println("Login e-dziekanat: ");
		login = sc.nextLine();
		System.out.println("Pass e-dziekanat: ");
		pass = sc.nextLine();
		
		System.out.println("Dla 1 - Frekwencja wszędzie 75-100%");
		System.out.println("Dla 2 - Frekwencja losowo 50-75% / 75-100%");
		System.out.println("Podaj wartość (1), (2):");
		
		while(choose != 1 && choose != 2) {
			try {
				choose = sc.nextInt();
				if(choose != 1 && choose != 2) {
					System.out.println("Podano złą opcję");
				}
			} catch(InputMismatchException e) {
				System.out.println("Podano zły znak");
				sc.next();
			}
		}
		
		sc.close();
		
		driver.get(baseUrl);

		WebElement loginInput = driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_txtIdent"));
		WebElement passInput = driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_txtHaslo"));
		
		loginInput.clear();
		loginInput.sendKeys(login);
		
		passInput.clear();
		passInput.sendKeys(pass);
		
		driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_rbKto_0")).click();

		driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_butLoguj")).click();

		if(driver.getPageSource().contains("Zła nazwa użytkownika lub hasło")) {
			System.out.println("Nie udało się zalogować");
			driver.close();
			System.exit(0);
		}
		
		Actions act = new Actions(driver);
		
		Random rand = new Random();
		
		driver.get("https://edziekanat.zut.edu.pl/WU/PokazAnkEgz.aspx?typ=ank");
		WebElement ankietyStart = driver.findElement(By.cssSelector(".gridDane a"));
		ankietyStart.click();
		
		while(driver.findElements(By.className("gridDane")).size() != 0) {
				ankieta = driver.findElement(By.className("gridDane"));
				ank = ankieta.findElement(By.tagName("a"));
				act.moveToElement(ank).click().perform();
				
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G0_G0_2904")));
				
				
				//frekwencja
		        	if(choose == 1) {
						frekwencja = Arrays.asList(driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G0_G0_2905")));
		        	} else if (choose == 2) {
		        		frekwencja = Arrays.asList(
							driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G0_G0_2904")), 
							driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G0_G0_2905"))
						);
		        	}
				//podpunkt 1
						opcjaPierwsza = Arrays.asList(
						driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G1_G1_2910")), 
						driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G1_G1_2911")),
						driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G1_G1_2912"))
						);
				//podpunkt 2
						opcjaDruga = Arrays.asList(
						driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G2_G2_2916")), 
						driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G2_G2_2917")),
						driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G2_G2_2918"))
						);
				//podpunkt 3
						opcjaTrzecia = Arrays.asList(
						driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G3_G3_2922")), 
						driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G3_G3_2923")),
						driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_G3_G3_2924"))
						);
				
				frekwencja.get(rand.nextInt(frekwencja.size())).click();
				opcjaPierwsza.get(rand.nextInt(opcjaPierwsza.size())).click();
				opcjaDruga.get(rand.nextInt(opcjaDruga.size())).click();
				opcjaTrzecia.get(rand.nextInt(opcjaTrzecia.size())).click();
				
				driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder_MiddleContentPlaceHolder_btn_Zapisz")).click();
				
				driver.get("https://edziekanat.zut.edu.pl/WU/PokazAnkEgz.aspx?typ=ank");
				ankiety = driver.findElement(By.cssSelector(".gridDane a"));
				ankiety.click();
		}
		System.out.println("Koniec");
		driver.close();
	}
}
