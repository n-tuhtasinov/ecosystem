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
        try {
            repository.save(
                    ChecklistTemplate
                            .builder()
                            .name(dto.name())
                            .path(dto.path())
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Bunday nomli shablon mavjud!");
        }
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
            try {
                repository.save(checklistTemplate);
            } catch (DataIntegrityViolationException e) {
                throw new RuntimeException("Bunday nomli shablon mavjud!");
            }
            attachmentRepository.deleteById(attachment.getId());
        } else repository.save(checklistTemplate);
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
        boolean delete = new File(checklistTemplate.getPath()).delete();
        if (delete) {
            repository.deleteById(id);
        }
    }

    @Override
    public Page<ChecklistTemplateView> getAll(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "name");
        return repository.getAllByName(pageable, name);
    }

    @Override
    public List<ChecklistTemplateView> findAll(String name) {
        return repository.findAllByName(name);
    }
}
