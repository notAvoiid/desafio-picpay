package igor.abreu.picpay.service;

import igor.abreu.picpay.client.AuthorizationClient;
import igor.abreu.picpay.exceptions.PicPayException;
import igor.abreu.picpay.model.Transfer;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(Transfer transfer) {

        var response = authorizationClient.isAuthorized();
        if (response.getStatusCode().isError()) throw new PicPayException();

        return response.getBody().authorized();
    }
}
