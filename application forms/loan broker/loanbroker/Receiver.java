package loanbroker;

import javax.jms.JMSException;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Created by Wouter Vanmulken on 4-4-2017.
 */
public class Receiver {
    public static void Receive(MessageListener messageListener, String endpoint){
        Connection connection; // to connect to the JMS
        Session session; // session for creating consumers

        Destination receiveDestination; //reference to a queue/topic destination
        MessageConsumer consumer = null; // for receiving messages

        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");

            // connect to the Destination called “myFirstChannel”
            // queue or topic: “queue.myFirstDestination” or
            //                   “topic.myFirstDestination”
            props.put("queue."+endpoint, endpoint);

            Context jndiContext = new InitialContext(props);
            ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext
                    .lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // connect to the receiver destination
            receiveDestination = (Destination) jndiContext.lookup(endpoint);
            consumer = session.createConsumer(receiveDestination);

            connection.start(); // this is needed to start receiving messages

            consumer.setMessageListener(messageListener);

        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }
}
