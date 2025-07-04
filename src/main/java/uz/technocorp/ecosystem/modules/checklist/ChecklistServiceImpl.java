package uz.technocorp.ecosystem.modules.checklist;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.attachment.AttachmentService;
import uz.technocorp.ecosystem.modules.checklist.dto.ChecklistDto;
import uz.technocorp.ecosystem.modules.checklist.view.ChecklistView;
import uz.technocorp.ecosystem.modules.profile.Profile;
import uz.technocorp.ecosystem.modules.profile.ProfileRepository;
import uz.technocorp.ecosystem.modules.user.User;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 06.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistRepository repository;
    private final ProfileRepository profileRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentService attachmentService;

    @Override
    public void create(User user, ChecklistDto checklistDto) {
        Profile profile = profileRepository
                .findById(user.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", user.getProfileId()));
        Attachment attachment = attachmentRepository
                .findByPath(checklistDto.path())
                .orElseThrow(() -> new ResourceNotFoundException("Attachment", "path", checklistDto.path()));
        repository.save(
                Checklist
                        .builder()
                        .path(checklistDto.path())
                        .templateId(checklistDto.templateId())
                        .tin(profile.getTin())
                        .intervalId(checklistDto.intervalId())
                        .objectId(checklistDto.objectId())
                        .build()
        );
//        attachmentRepository.deleteByPath(checklistDto.path());
        attachmentRepository.deleteById(attachment.getId());
    }

    @Override
    public void update(UUID id, User user, ChecklistDto checklistDto) {
        Checklist checklist = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist", "Id", id.toString()));
        checklist.setTemplateId(checklistDto.templateId());
        if (!checklist.getPath().equals(checklistDto.path())) {
            Attachment attachment = attachmentRepository
                    .findByPath(checklistDto.path())
                    .orElseThrow(() -> new ResourceNotFoundException("Attachment", "path", checklistDto.path()));
            checklist.setPath(checklistDto.path());
            repository.save(checklist);
            attachmentRepository.deleteById(attachment.getId());
        } else repository.save(checklist);
    }

    @Override
    public void delete(UUID id) {
        Checklist checklist = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist", "Id", id.toString()));
        attachmentService.deleteFileFromStorage(checklist.getPath());
        repository.deleteById(checklist.getId());
    }

    @Override
    public List<ChecklistView> getChecklists(Long tin, UUID objectId, Integer intervalId) {

        return repository.findAllChecklist(tin, objectId);
    }

}
