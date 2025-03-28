//package uz.technocorp.ecosystem.modules.irsappeal;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
//import uz.technocorp.ecosystem.modules.appeal.AppealRepository;
//import uz.technocorp.ecosystem.modules.appeal.AppealService;
//import uz.technocorp.ecosystem.modules.appeal.enums.AppealStatus;
//import uz.technocorp.ecosystem.modules.appeal.enums.AppealType;
//import uz.technocorp.ecosystem.modules.irs.dto.IrsDto;
//import uz.technocorp.ecosystem.modules.profile.Profile;
//import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
//import uz.technocorp.ecosystem.modules.user.User;
//
//import java.time.LocalDate;
//import java.util.UUID;
//
///**
// * @author Nurmuhammad Tuhtasinov
// * @version 1.0
// * @created 27.03.2025
// * @since v1.0
// */
//@Service
//@RequiredArgsConstructor
//public class IrsAppealServiceImpl implements IrsAppealService {
//
//    private final IrsAppealRepository irsAppealRepository;
//    private final AppealService appealService;
//    private final ProfileRepository profileRepository;
//
//    @Override
//    @Transactional
//    public void create(User user, IrsDto dto) {
//
//        Integer maxNumber = irsAppealRepository.getMaxSequence();
//        int orderNumber = (maxNumber == null ? 0 : maxNumber) + 1;
//        String number = orderNumber + "-INM-" + LocalDate.now().getYear();
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        JsonNode data = mapper.valueToTree(dto);
//
//        Profile profile = profileRepository.findById(user.getProfileId()).orElseThrow(() -> new ResourceNotFoundException("Profile", "ID", user.getProfileId()));
//
//
//        //save appeal
//        UUID appealId = appealService.create(dto, user.getProfileId(), number);
//
//        irsAppealRepository.save(
//                IrsAppeal.builder()
//                .appealType(AppealType.REGISTER_IRS)
//                .number(number)
//                .orderNumber(orderNumber)
//                .legalTin(profile.getTin())
//                .status(AppealStatus.NEW)
//                .deadline(LocalDate.now().plusDays(15))
//                .data(data)
//                .appealId(appealId)
//                .build());
//    }
//
//    @Override
//    public IrsAppeal getByAppealId(UUID appealId) {
//        return irsAppealRepository.findByAppealId(appealId);
//    }
//}
