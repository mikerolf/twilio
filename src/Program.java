import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.*;
import java.io.*;

class Program {
    String accountSid;        
    String authToken;        
    String toNumber;        
    String fromNumber;        
    String smsMessage;

    public void run() {
        loadProperties();
        readCmdLine();
        sendSms();
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try {
           properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        accountSid = properties.getProperty("account-sid");        
        authToken = properties.getProperty("auth-token");        
        fromNumber = properties.getProperty("from-number");        
    }

    private void readCmdLine() {
	System.out.print("Enter number: ");
	toNumber = System.console().readLine();

        System.out.print("Enter msg: ");
	smsMessage = System.console().readLine();
    }

    private void sendSms() {
        TwilioRestClient client = new TwilioRestClient(accountSid, authToken);
     
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", smsMessage));
        params.add(new BasicNameValuePair("To", toNumber));
        params.add(new BasicNameValuePair("From", fromNumber));
     
        MessageFactory messageFactory = client.getAccount().getMessageFactory();

        Message message;

        try {
            message = messageFactory.create(params);
        }
        catch(TwilioRestException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n----- Message Details ------");
        System.out.println("Date Created: " + message.getDateCreated());
        System.out.println("Status: " + message.getStatus());
    }
}
