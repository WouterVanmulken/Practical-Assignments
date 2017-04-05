package loanclient;

import model.loan.LoanReply;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by Wouter Vanmulken on 5-4-2017.
 */
public class ClientListener implements MessageListener {
    LoanClientFrame frame;

    public ClientListener(LoanClientFrame frame) {
        this.frame = frame;
    }

    @Override
    public void onMessage(Message message) {
        LoanReply reply = null;
        try {
            reply = (LoanReply) ((ObjectMessage) message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if (reply != null) {
            frame.add(reply);
//            Messager.Send(LoanReply, "myFourthDestination");
        }
    }
}

