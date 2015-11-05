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

    public void run() {
        loadProperties();
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
        toNumber = properties.getProperty("to-number");        
        fromNumber = properties.getProperty("from-number");        
    }

    private void sendSms() {
        TwilioRestClient client = new TwilioRestClient(accountSid, authToken);
     
        // Build a filter for the MessageList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", "Jenny please?! I love you <3"));
        params.add(new BasicNameValuePair("To", toNumber));
        params.add(new BasicNameValuePair("From", fromNumber));
     
        MessageFactory messageFactory = client.getAccount().getMessageFactory();

        try {
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
        }
        catch(TwilioRestException e) {
            throw new RuntimeException(e);
        }
    }
}
