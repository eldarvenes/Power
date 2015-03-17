package eldarvenes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PostEnergyToWeb implements Job {

	public void saveReport() throws SQLException, InterruptedException {
		FirefoxDriver driver = new FirefoxDriver();

		driver.get("https://kundeweb.sognekraft.no/pls/kundeweb_sognekraft/webuser.avlesning.controlProc?p_session_id=217990");

		WebElement element = driver.findElement(By.name("p_navn"));
		element.sendKeys("129863");

		element = driver.findElement(By.name("p_pass"));
		element.sendKeys("6427");
		element.submit();

		element = driver.findElement(By.name("p_maalarStand"));

		String SELECT = "select id,timestamp,kwh from power order by id desc limit 1";
		Connector.getInstance();
		Connection connection = Connector.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SELECT);
		String kwh = "";
		if (rs.next()) {
			kwh = rs.getString(3);
			kwh = kwh.replace('.', ',');
		}
		stmt.close();
		connection.close();

		System.out.println("Registrerer: " + kwh + " kwh til "
				+ driver.getTitle());
		element.sendKeys(kwh);
		element.submit();
		Thread.sleep(1000);
		element = driver
				.findElementByXPath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/form/table/tbody/tr[3]/td[1]/input");
		element.click();

		if (driver.findElement(By.cssSelector("body")).getText()
				.contains("kvittering")) {
			System.out.println("Måleravlesning var velykket");
		} else {
			System.out
					.println("Noko gjekk gale med registrering av strømforbruk");
		}

		driver.close();

	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			saveReport();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
