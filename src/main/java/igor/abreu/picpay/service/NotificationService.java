package igor.abreu.picpay.service;

import igor.abreu.picpay.client.NotificationClient;
import igor.abreu.picpay.model.Transfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    private final NotificationClient notificationClient;

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public void sendNotification(Transfer transfer) {

        try {
            var response = notificationClient.sendNotification(transfer);

            if (response.getStatusCode().isError()) log.error("Error while sending notification! Status coded is not OK");

            log.info("Sending notification...");
        } catch (Exception ex) {
            log.error("Error while sending notification!", ex);
        }
    }
}
