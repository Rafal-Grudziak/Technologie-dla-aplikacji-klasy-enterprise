package lab.backing;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.*;
import lab.ejb.NewsItem;
import lab.ejb.NewsItemFacadeLocal;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class NewsBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String headingText;
    private String bodyText;

    @Inject
    private NewsItemFacadeLocal facade;

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:app/jms/NewsQueue")
    private Queue queue;

    public void sendNewsItem(String heading, String body) {
        try {
            String text = heading + "|" + body;
            TextMessage message = context.createTextMessage(text);
            context.createProducer().send(queue, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public List<NewsItem> getNewsItems() {
        return facade.getAllNewsItems();
    }

    // Właściwości powiązane z formularzem JSF
    public String getHeadingText() {
        return headingText;
    }

    public void setHeadingText(String headingText) {
        this.headingText = headingText;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String submitNews() {
        sendNewsItem(headingText, bodyText);
        return null; // nie przekierowujemy
    }
}
