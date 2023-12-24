package com.a00n.web.rest;

import com.a00n.repository.GroupeRepository;
import com.a00n.repository.ProfessorRepository;
import com.a00n.repository.StudentPWRepository;
import com.a00n.repository.StudentRepository;
import com.a00n.service.dto.CountStatsDTO;
import com.a00n.service.dto.GroupStudentCountDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
@Transactional
public class StatsResource {

    private final Logger log = LoggerFactory.getLogger(StudentResource.class);

    private static final String ENTITY_NAME = "stats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentRepository studentRepository;
    private final GroupeRepository groupeRepository;
    private final ProfessorRepository professorRepository;
    private final StudentPWRepository studentPWRepository;

    public StatsResource(
        StudentRepository studentRepository,
        StudentPWRepository studentPWRepository,
        GroupeRepository groupeRepository,
        ProfessorRepository professorRepository
    ) {
        this.studentRepository = studentRepository;
        this.studentPWRepository = studentPWRepository;
        this.groupeRepository = groupeRepository;
        this.professorRepository = professorRepository;
    }

    @GetMapping("")
    public CountStatsDTO getCountStats() {
        return new CountStatsDTO(
            studentRepository.count(),
            groupeRepository.count(),
            professorRepository.count(),
            studentPWRepository.count()
        );
    }

    @GetMapping("/students-per-groupe")
    public List<GroupStudentCountDTO> getStudentCountPerGroup() {
        List<Object[]> result = groupeRepository.countStudentsPerGroupe();
        return result.stream().map(objarr -> new GroupStudentCountDTO((String) objarr[0], (Long) objarr[1])).collect(Collectors.toList());
    }

    @GetMapping("/pw-per-groupe")
    public List<GroupStudentCountDTO> getPWCountPerGroup() {
        List<Object[]> result = groupeRepository.countPWsPerGroupe();
        return result.stream().map(objarr -> new GroupStudentCountDTO((String) objarr[0], (Long) objarr[1])).collect(Collectors.toList());
    }
}
