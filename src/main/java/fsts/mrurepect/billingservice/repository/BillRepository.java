package fsts.mrurepect.billingservice.repository;

import fsts.mrurepect.billingservice.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}