package igor.abreu.picpay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_wallet_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    public enum Enum {

        USER(1L, "user"),
        MERCHANT(2L, "merchant");

        private Long id;
        private String description;

        Enum(Long id, String description) {
            this.id = id;
            this.description = description;
        }

        public WalletType get() {
            return new WalletType(id, description);
        }
    }
}
