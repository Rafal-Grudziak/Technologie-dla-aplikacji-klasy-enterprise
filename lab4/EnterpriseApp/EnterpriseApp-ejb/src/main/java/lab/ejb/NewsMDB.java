package lab.ejb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import jakarta.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(
        name = "java:app/jms/NewsQueue",
        interfaceName = "jakarta.jms.Queue",
        resourceAdapter = "jmsra",
        destinationName = "NewsQueue"
)
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/NewsQueue"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
        }
)
public class NewsMDB implements MessageListener {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                String[] parts = text.split("\\|", 2);
                if (parts.length == 2) {
                    NewsItem news = new NewsItem();
                    news.setHeading(parts[0]);
                    news.setBody(parts[1]);
                    em.persist(news);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
