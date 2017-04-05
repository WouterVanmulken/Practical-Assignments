package loanbroker;

import loanclient.Messager;
import model.bank.BankInterestReply;
import model.loan.LoanReply;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by Wouter Vanmulken on 5-4-2017.
 */
public class BankMessageListener implements MessageListener {
    LoanBrokerFrame frame;

    public BankMessageListener(LoanBrokerFrame frame) {
        this.frame = frame;
    }

    @Override
    public void onMessage(Message message) {
        BankInterestReply bir = null;
        try {
            bir = (BankInterestReply)((ObjectMessage) message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if (bir != null) {

            frame.add(bir);
            LoanReply loanReply = new LoanReply(bir.getInterest(),bir.getQuoteId());
            Messager.Send(loanReply, "myFourthDestination");
        }

    }
}
