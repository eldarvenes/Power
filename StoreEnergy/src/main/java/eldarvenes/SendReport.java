package eldarvenes;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendReport implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		GeneratePowerReport gpr = new GeneratePowerReport();
		String text = "";
		try {
			 
			String tidsstempel = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
						
			text +="Rapport generert: " + tidsstempel;
			text += "\nForbuk siste d�gn: " + gpr.getUsageLastDay() + " kWh";			
			text += "\nM�larstand: " + gpr.getCurrentKwhStatus() + " kWh";
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFrom("eldarvenes@gmail.com");
		emailMessage.setTo("eldarvenes@gmail.com");
		emailMessage.setSubject("Str�mavlesing");
		emailMessage.setText(text);

		SendMailTLS sendMail = new SendMailTLS(emailMessage);
		sendMail.sendEmail();

	}

}
