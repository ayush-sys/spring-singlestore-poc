package com.singlestore.singlestore_application.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

/** The t_fact table entity. */

@Data
@Entity
@Table(name = "RC_T_FACT")
public class FactModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "msisdn", nullable = false)
    private int msisdn;

    @Column(name = "cam_id", nullable = false)
    private String camId;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "waitover_flag", nullable = false)
    private int waitoverFlag;

    @Column(name = "modified_time", nullable = false)
    private Timestamp modifiedTime;

}
