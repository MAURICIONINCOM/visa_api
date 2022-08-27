package bo.visa.nincom.service;

import bo.visa.nincom.domain.Bill;
import bo.visa.nincom.domain.Transaction;
import bo.visa.nincom.exception.EntityNotFoundException;
import bo.visa.nincom.service.dto.Operation;

import java.util.List;

public interface BillService {
    Bill addBill(Bill request) throws EntityNotFoundException;
    List<Bill> listBill() throws EntityNotFoundException;
    Bill depositBill(Operation request) throws EntityNotFoundException;
    Bill retreatBill(Operation request) throws EntityNotFoundException;
    String balanceBill(String number) throws EntityNotFoundException;
    List<Transaction> historyList() throws EntityNotFoundException;
}
