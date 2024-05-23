package igor.abreu.picpay.service;

import igor.abreu.picpay.exceptions.WalletDataAlreadyExistsException;
import igor.abreu.picpay.model.Wallet;
import igor.abreu.picpay.model.dto.wallet.CreateWalletDTO;
import igor.abreu.picpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDTO dto) {

        var walletDb = walletRepository.findByCpfCnpjOrEmail(dto.cpfCnpj(), dto.email());
        if (walletDb.isPresent()) throw new WalletDataAlreadyExistsException("CPF/CNPJ or Email already exists!");

        return walletRepository.save(dto.toWallet());
    }
}
