package ru.practicum.main_service.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.main_service.constant.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NewCompilationDto {

    @NotBlank
    @Size(min = Constants.MIN_TITLE, max = Constants.MAX_TITLE)
    private String title;

    private Boolean pinned = false;

    private List<Long> events = new ArrayList<>();

}
