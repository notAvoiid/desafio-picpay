package igor.abreu.picpay.service;

import igor.abreu.picpay.client.AuthorizationClient;
import igor.abreu.picpay.exceptions.PicPayException;
import igor.abreu.picpay.model.dto.transfer.TransferDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDTO transfer) {

        var response = authorizationClient.isAuthorized();
        if (response.getStatusCode().isError()) throw new PicPayException();

        return response.getBody().authorized();
    }
}
