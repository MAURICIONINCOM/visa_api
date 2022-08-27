package bo.visa.nincom.rest;

import bo.visa.nincom.domain.Bill;
import bo.visa.nincom.domain.Transaction;
import bo.visa.nincom.exception.EntityNotFoundException;
import bo.visa.nincom.service.BillService;
import bo.visa.nincom.service.dto.Operation;
import bo.visa.nincom.shared.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api_v1/bill")
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/add")
    public JsonResult addBill(@RequestBody Bill request) throws EntityNotFoundException {
        Bill response = billService.addBill(request);
        return new JsonResult(true, response, "Cuenta registrada correctamente.");
    }

    @GetMapping("/list")
    public JsonResult listBill() throws EntityNotFoundException {
        List<Bill> response = billService.listBill();
        return new JsonResult(true, response, "Listado obtenido correctamente.");
    }

    @PostMapping("/deposit")
    public JsonResult depositBill(@RequestBody Operation request) throws EntityNotFoundException {
        Bill response = billService.depositBill(request);
        return new JsonResult(true, response, "Deposito realizado correctamente.");
    }

    @PostMapping("/retreat")
    public JsonResult retreatBill(@RequestBody Operation request) throws EntityNotFoundException {
        Bill response = billService.retreatBill(request);
        return new JsonResult(true, response, "Retiro realizado correctamente.");
    }

    @GetMapping("/balance/{number}")
    public JsonResult balanceBill(@PathVariable("number") String number) throws EntityNotFoundException {
        String response = billService.balanceBill(number);
        return new JsonResult(true, response, "Saldo obtenido correctamente.");
    }

    @GetMapping("/history/list")
    public JsonResult historyList() throws EntityNotFoundException {
        List<Transaction> response = billService.historyList();
        return new JsonResult(true, response, "Historial de transacciones obtenido correctamente.");
    }
}
