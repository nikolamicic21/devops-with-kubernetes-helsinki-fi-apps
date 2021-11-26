package io.nikolamicic21.mainapplication.health;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Status;
import org.springframework.lang.NonNull;

@Data
@RequiredArgsConstructor(onConstructor=@__(@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)))
public class CustomHealth {

    @NonNull
    private final Status status;

}
