package cmsAutomation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.mysql.jdbc.Statement;

public class TestCms {

	private static final String WebelementID = null;
	private static Connection conn;

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// Create a new instance of the Chrome driver
		WebDriver driver = new ChromeDriver();
		
        //Launch the website
		driver.get("https://www.travelagent.co.id/");        

        WebElement username = driver.findElement(By.id("user_s"));
        username.sendKeys("xxxva0058");    

        WebElement password = driver.findElement(By.id("pass_s"));
        password.sendKeys("123");
        
        driver.findElement(By.xpath("//button[contains(@class,'login_s btn btn-success btn-sm')]")).click();
        
        driver.findElement(By.linkText("Admin")).click();
        
        driver.findElement(By.xpath("//a[@href='listuser.jsp']")).click();
 
		//Wait for 5 Sec
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
        String host = "jdbc:mysql://localhost:3306/seminarp_cms_seminar_penelitian";
        String uName = "root";
        String uPass = "";

        Class.forName("com.mysql.jdbc.Driver"); 
        Connection conn = DriverManager.getConnection(host, uName, uPass);
        System.out.println("Connection Established Successfull and the DATABASE NAME IS:"+ conn.getMetaData().getDatabaseProductName());
		java.sql.Statement stmt = conn.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT username FROM cms_pengguna");
        int i = 1;
        while ( rs.next() ) {
            String userName = rs.getString("username");            
            WebElement element = driver.findElement(By.xpath("//div[@id='pnrList_page']/table/tbody/tr["+i+"]/td[2]"));
            String text = element.getText();
            Assert.assertEquals(userName, text);
            System.out.println("DB : "+userName+" = UI : "+text);
            
            i = i+1;
		}
        // Close the driver
        driver.quit();
	}
}