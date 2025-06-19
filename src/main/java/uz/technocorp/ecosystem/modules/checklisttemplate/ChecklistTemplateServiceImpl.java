package uz.technocorp.ecosystem.modules.checklisttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.technocorp.ecosystem.exceptions.ResourceNotFoundException;
import uz.technocorp.ecosystem.modules.attachment.Attachment;
import uz.technocorp.ecosystem.modules.attachment.AttachmentRepository;
import uz.technocorp.ecosystem.modules.checklist.Checklist;
import uz.technocorp.ecosystem.modules.checklist.ChecklistRepository;
import uz.technocorp.ecosystem.modules.checklisttemplate.dto.ChecklistTemplateDto;
import uz.technocorp.ecosystem.modules.checklisttemplate.view.ChecklistTemplateView;

import java.io.File;
import java.util.List;

/**
 * @author Rasulov Komil
 * @version 1.0
 * @created 05.05.2025
 * @since v1.0
 */
@Service
@RequiredArgsConstructor
public class ChecklistTemplateServiceImpl implements ChecklistTemplateService {

    private final ChecklistTemplateRepository repository;
    private final AttachmentRepository attachmentRepository;
    private final ChecklistRepository checklistRepository;

    @Override
    public void create(ChecklistTemplateDto dto) {
        Attachment attachment = attachmentRepository
                .findByPath(dto.path())
                .orElseThrow(() -> new ResourceNotFoundException("File", "qiymat", dto.path()));
        repository.save(
                ChecklistTemplate
                        .builder()
                        .name(dto.name())
                        .path(dto.path())
                        .active(true)
                        .build()
        );
        attachmentRepository.deleteById(attachment.getId());
    }

    @Override
    public void update(Integer id, ChecklistTemplateDto dto) {
        ChecklistTemplate checklistTemplate = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist shabloni", "id", id));
        checklistTemplate.setName(dto.name());
        if (!dto.path().equals(checklistTemplate.getPath())) {
            Attachment attachment = attachmentRepository
                    .findByPath(dto.path())
                    .orElseThrow(() -> new ResourceNotFoundException("File", "qiymat", dto.path()));
            checklistTemplate.setPath(dto.path());
            repository.save(checklistTemplate);
            attachmentRepository.deleteById(attachment.getId());
        } else repository.save(checklistTemplate);
    }

    @Override
    public void updateActivate(Integer id) {
        ChecklistTemplate checklistTemplate = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist shabloni", "id", id));
        checklistTemplate.setActive(!checklistTemplate.isActive());
        repository.save(checklistTemplate);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        ChecklistTemplate checklistTemplate = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist shabloni", "id", id));
        List<Checklist> checklists = checklistRepository.findAllByTemplateId(id);
        if (!checklists.isEmpty()) {
            throw new RuntimeException("Checklist shabloni asosida checklist yaratilganligi bois ushbu shablonni o'chirib bo'lmaydi!");
        }
        File file = new File(checklistTemplate.getPath());
        if (file.exists()) {
            if (!file.canWrite()) {
                throw new RuntimeException("Faylni o‘chirishga ruxsat yo‘q: " + file.getAbsolutePath());
            }
            boolean deleted = file.delete();
            if (!deleted) {
                throw new RuntimeException("Faylni o‘chirishda muammo yuz berdi: " + file.getAbsolutePath());
            }
        } else {
            throw new RuntimeException("Fayl mavjud emas: " + file.getAbsolutePath());
        }
        repository.deleteById(id);
    }

    @Override
    public ChecklistTemplateView getChecklistTemplate(Integer id) {
        return repository
                .getChecklistTemplatesById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist shabloni", "id", id));
    }

    @Override
    public Page<ChecklistTemplateView> getAll(int page, int size, String name, Boolean active) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "name");
        return repository.getAllByName(pageable, name, active);
    }

    @Override
    public List<ChecklistTemplateView> findAll(String name) {
        return repository.findAllByName(name);
    }
}
