package uz.technocorp.ecosystem.modules.irsappeal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
import uz.technocorp.ecosystem.modules.appeal.AppealService;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;

import java.time.LocalDate;

/**
 * @author Nurmuhammad Tuhtasinov
 * @version 1.0
 * @created 27.03.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class IrsAppealServiceImpl implements IrsAppealService {

    private final IrsAppealRepository irsAppealRepository;
    private final AppealService appealService;

    @Override
    @Transactional
    public void create(IrsDto dto) {

        Integer maxNumber = irsAppealRepository.getMaxSequence();
        int orderNumber = (maxNumber == null ? 0 : maxNumber) + 1;
        String number = orderNumber + "-INM-" + LocalDate.now().getYear();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.valueToTree(dto);

        IrsAppeal build = IrsAppeal.builder()
                .appealType(AppealType.REGISTER_IRS)
                .number(number)
                .orderNumber(orderNumber)
                .status(AppealStatus.New)
                .deadline(LocalDate.now().plusDays(15))
                .data(data)
                .build();

        irsAppealRepository.save(build);

//        appealService.create()


    }
}
