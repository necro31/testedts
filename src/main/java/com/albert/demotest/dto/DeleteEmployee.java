package com.albert.demotest.dto;

import com.albert.demotest.enums.Grade;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.time.Instant;


@Data
@Accessors(chain = true)
public class DeleteEmployee {
    @NotEmpty
    private Instant deletedAt;
}
