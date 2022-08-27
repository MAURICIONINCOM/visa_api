package bo.visa.nincom.service.impl;

import bo.visa.nincom.domain.Bill;
import bo.visa.nincom.domain.Transaction;
import bo.visa.nincom.exception.EntityNotFoundException;
import bo.visa.nincom.service.BillService;
import bo.visa.nincom.service.dto.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    public static List<Bill> billList = new ArrayList<>();
    public static List<Transaction> transactionList = new ArrayList<>();

    @Override
    public Bill addBill(Bill request) throws EntityNotFoundException {
        Bill bill = new Bill();
        bill.setNumber(request.getNumber());
        bill.setClientName(request.getClientName());
        bill.setStatus(request.getStatus());
        bill.setTypeCurrency(request.getTypeCurrency());
        bill.setMount(request.getMount());
        bill.setStartflag(true);
        billList.add(bill);
        bill.setId(billList.size());
        recordHistory(bill, "Creacion de la cuenta");
        return bill;
    }

    @Override
    public List<Bill> listBill() throws EntityNotFoundException {
        return billList;
    }

    @Override
    public Bill depositBill(Operation request) throws EntityNotFoundException {
        for (Bill currentBill : billList) {
            if (currentBill.getNumber().equals(request.getNumber())) {
                if (currentBill.getTypeCurrency().equals(request.getTypeCurrency())) {
                    Double currentMount = currentBill.getMount() + request.getMount();
                    if (currentBill.getStatus().equals("HOLD") && currentMount >= 0) {
                        currentBill.setStatus("ACTIVE");
                    }
                    currentBill.setMount(currentMount);
                    recordHistory(currentBill, "Deposito de " + request.getMount() + " " + request.getTypeCurrency());
                    return currentBill;
                } else {
                    throw new EntityNotFoundException(String.format(
                            "La cuenta solo permite depositos en: %s", currentBill.getTypeCurrency()
                    ));
                }
            }
        }
        throw new EntityNotFoundException(String.format(
                "No se encontro la cuenta numero: %s", request.getNumber()
        ));
    }

    @Override
    public Bill retreatBill(Operation request) throws EntityNotFoundException {
        for (Bill currentBill : billList) {
            if (currentBill.getNumber().equals(request.getNumber())) {
                switch (currentBill.getStatus()) {
                    case "ACTIVE":
                        if (currentBill.getTypeCurrency().equals(request.getTypeCurrency())) {
                            if (currentBill.getMount() < request.getMount()) {
                                if (!currentBill.getStartflag()) {
                                    throw new EntityNotFoundException(String.format(
                                            "Solo se permite un solo retiro mayor al saldo."
                                    ));
                                }
                                currentBill.setStartflag(false);
                                currentBill.setStatus("HOLD");
                            }
                            Double currentMount = currentBill.getMount() - request.getMount();
                            currentBill.setMount(currentMount);
                            recordHistory(currentBill, "Retiro de " + request.getMount() + " " + request.getTypeCurrency());
                            return currentBill;
                        } else {
                            throw new EntityNotFoundException(String.format(
                                    "No se permiten retiros en un tipo de moneda diferente, tipo moneda actual", currentBill.getTypeCurrency()
                            ));
                        }

                    case "HOLD":
                        throw new EntityNotFoundException(String.format(
                                "No se permiten retiros en cuentas con estado: HOLD"
                        ));
                }
            }
        }
        throw new EntityNotFoundException(String.format(
                "No se encontro la cuenta numero: %s", request.getNumber()
        ));
    }

    @Override
    public String balanceBill(String number) throws EntityNotFoundException {
        for (Bill currentBill : billList) {
            if (currentBill.getNumber().equals(number)) {
                return "Saldo actual: " + currentBill.getMount();
            }
        }
        throw new EntityNotFoundException(String.format(
                "No se encontro la cuenta numero: %s", number
        ));
    }

    @Override
    public List<Transaction> historyList() throws EntityNotFoundException {
        return transactionList;
    }

    public String getCurrentDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
    }

    public void recordHistory(Bill bill, String operation) {
        Transaction transaction = new Transaction();
        transaction.setDate(getCurrentDate());
        transaction.setBillNumber(bill.getNumber());
        transaction.setOperation(operation);
        transaction.setId(transactionList.size() + 1);
        transactionList.add(transaction);
    }
}
