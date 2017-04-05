package loanclient;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.Properties;

/**
 * Created by Wouter Vanmulken on 4-4-2017.
 */
public class Messager {

    public static void Send(Serializable message, String destination) {
        javax.jms.Connection connection; // to connect to the ActiveMQ
        Session session; // session for creating messages, producers and

        Destination sendDestination; // reference to a queue/topic destination
        MessageProducer producer; // for sending messages
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");

            // connect to the Destination called “myFirstChannel”
            // queue or topic: “queue.myFirstDestination” or “topic.myFirstDestination”
            props.put("queue."+destination, destination);

            Context jndiContext = new InitialContext(props);
            ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext
                    .lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // connect to the sender destination
            sendDestination = (Destination) jndiContext.lookup(destination);
            producer = session.createProducer(sendDestination);

//            String body = "Hello, this is my first message!"; //or serialize an object!
            // create a text message
//            TextMessage msg = session.createTextMessage(body);

            ObjectMessage msg = session.createObjectMessage(message);
            // send the message
            producer.send(msg);

            //print all message attributes; but JMSDestination is senderDestination name
            System.out.println(msg);
            try {
                //print only the attributes you want to see
                System.out.println("JMSMessageID=" + msg.getJMSMessageID()
                        + "    JMSDestination=" + msg.getJMSDestination()
                        + "    Text=" + msg.getObject());
            } catch (JMSException e) {
                e.printStackTrace();
            }


        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }

}

