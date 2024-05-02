package fsts.mrurepect.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class BillResponceDto {
    Long id;
    Date billDate;
    String customerName;
}
