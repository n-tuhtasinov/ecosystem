package uz.technocorp.ecosystem.modules.employee.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSelectView {

    private UUID id;
    private String pin;
    private String name;
}
