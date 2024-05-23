package igor.abreu.picpay.controller;

import igor.abreu.picpay.model.Transfer;
import igor.abreu.picpay.model.dto.transfer.TransferDTO;
import igor.abreu.picpay.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transfer> transfer(@RequestBody @Valid TransferDTO dto) {
        var response = transferService.transfer(dto);

        return ResponseEntity.ok(response);
    }
}
