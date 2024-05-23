package igor.abreu.picpay.repository;

import igor.abreu.picpay.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
