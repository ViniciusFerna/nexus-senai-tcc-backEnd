package com.nexus.nexus.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SimulacaoDto {

    private Long id;

    private Long userId;

    private String name;

    private String origin;

    private String destination;

    private Double distanceKm;

    private Double estimatedTimeH;

    private Double fuelCost;

    private Double tollCost;

    private Double totalCost;

    private String status;

}