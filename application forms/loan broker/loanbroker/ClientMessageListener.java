package loanbroker;

import loanclient.Messager;
import model.bank.BankInterestRequest;
import model.loan.LoanRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class ClientMessageListener implements MessageListener {
    LoanBrokerFrame frame;
    public ClientMessageListener(LoanBrokerFrame frame){
        this.frame = frame;
    }

    @Override
    public void onMessage(Message message) {
        LoanRequest loanRequest= null;
        try {
             loanRequest= (LoanRequest)((ObjectMessage)message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if(loanRequest !=null) {
            BankInterestRequest interestRequest = new BankInterestRequest(loanRequest.getAmount(),loanRequest.getTime());
//            frame.add(lr);
            frame.add(loanRequest,interestRequest);
            Messager.Send(interestRequest,"mySecondDestination");
        }

    }
}
