import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.*;
import java.io.*;

class Program {

    void run() {
        try {
            Properties properties = new Properties();
            try {
               properties.load(new FileInputStream("config.properties"));
            } catch (IOException e) {}

            String accountSid = properties.getProperty("account-sid");        
            String authToken = properties.getProperty("auth-token");        
            String toNumber = properties.getProperty("to-number");        
            String fromNumber = properties.getProperty("from-number");        
    
            TwilioRestClient client = new TwilioRestClient(accountSid, authToken);
         
            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", "Jenny please?! I love you <3"));
            params.add(new BasicNameValuePair("To", toNumber));
            params.add(new BasicNameValuePair("From", fromNumber));
         
            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
        }
        catch(TwilioRestException e) {
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        new Program().run();
    }
}
