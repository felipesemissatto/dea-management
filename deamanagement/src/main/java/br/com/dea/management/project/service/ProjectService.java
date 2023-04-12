package br.com.dea.management.project.service;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.dto.CreateProjectRequestDto;
import br.com.dea.management.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Project> findAllProjectPaginated(Integer page, Integer pageSize) {
        return this.projectRepository.findAllPaginated(PageRequest.of(page, pageSize, Sort.by("startDate").ascending()));
    }
    public Project findProjectById(Long id) {
        return this.projectRepository.findById(id).orElseThrow(() -> new NotFoundException(AcademyClass.class, id));
    }
    public Project createProject(CreateProjectRequestDto createProjectRequestDto) {
        Employee productOwner = this.employeeRepository.findById(createProjectRequestDto.getProductOwnerId())
                .orElseThrow(() -> new NotFoundException(Project.class, createProjectRequestDto.getProductOwnerId()));
        Employee scrumMaster = this.employeeRepository.findById(createProjectRequestDto.getScrumMasterId())
                .orElseThrow(() -> new NotFoundException(Project.class, createProjectRequestDto.getScrumMasterId()));

        Project project = Project.builder()
                .name(createProjectRequestDto.getName())
                .client(createProjectRequestDto.getClient())
                .externalProductManager(createProjectRequestDto.getExternalProductManager())
                .startDate(createProjectRequestDto.getStartDate())
                .endDate(createProjectRequestDto.getEndDate())
                .productOwner(productOwner)
                .scrumMaster(scrumMaster)
                .build();

        return this.projectRepository.save(project);
    }
}
