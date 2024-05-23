package igor.abreu.picpay.service;

import igor.abreu.picpay.exceptions.InsufficientBalanceException;
import igor.abreu.picpay.exceptions.TransferNotAllowedForThisTypeException;
import igor.abreu.picpay.exceptions.TransferNotAuthorizedException;
import igor.abreu.picpay.exceptions.WalletNotFoundException;
import igor.abreu.picpay.model.Transfer;
import igor.abreu.picpay.model.Wallet;
import igor.abreu.picpay.model.dto.transfer.TransferDTO;
import igor.abreu.picpay.repository.TransferRepository;
import igor.abreu.picpay.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final NotificationService notificationService;
    private final AuthorizationService authorizationService;
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    public TransferService(NotificationService notificationService, AuthorizationService authorizationService, TransferRepository transferRepository, WalletRepository walletRepository) {
        this.notificationService = notificationService;
        this.authorizationService = authorizationService;
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(TransferDTO transferDTO) {

        var sender = walletRepository.findById(transferDTO.payer()).orElseThrow(() -> new WalletNotFoundException(transferDTO.payer()));
        var receiver = walletRepository.findById(transferDTO.payee()).orElseThrow(() -> new WalletNotFoundException(transferDTO.payee()));

        validateTransfer(transferDTO, sender);

        sender.debit(transferDTO.value());
        receiver.credit(transferDTO.value());

        var transfer = new Transfer(sender, receiver, transferDTO.value());

        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;
    }

    private void validateTransfer(TransferDTO transferDTO, Wallet sender) {

        if (!sender.isTransferAllowedForWalletType()) throw new TransferNotAllowedForThisTypeException();
        if (!sender.isBalancerEqualOrGreaterThan(transferDTO.value())) throw new InsufficientBalanceException();
        if (!authorizationService.isAuthorized(transferDTO)) throw new TransferNotAuthorizedException();

    }


}
