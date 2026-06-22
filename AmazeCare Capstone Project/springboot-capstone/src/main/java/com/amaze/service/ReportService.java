package com.amaze.service;

import com.amaze.dto.ReportDto;
import com.amaze.dto.ReportResDto;
import com.amaze.exception.ResourceNotFoundException;
import com.amaze.mapper.ReportMapper;
import com.amaze.model.Report;
import com.amaze.repository.ReportRepository;
import com.amaze.utility.FileUtility;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    private static final String UPLOAD_LOC = "C:/Users/Ishwarya/Documents/main-test-reports-capstone";
    private ReportRepository reportRepository;
    private PatientService patientService;
    private ReportMapper reportMapper;
    private AppointmentService appointmentService;
    
    public Report add(MultipartFile file,String testName, String date, String name) throws IOException {
        FileUtility.validateFile(file);

        String fileName = LocalDate.now(ZoneId.systemDefault())+"_"+file.getOriginalFilename();

        Path uploadPath = Paths.get(UPLOAD_LOC)
                .toAbsolutePath()
                .normalize();

        Path destinationPath = uploadPath.resolve(fileName)
                .normalize();

        if (!destinationPath.startsWith(uploadPath)) {
            throw new IllegalArgumentException("Invalid file path");
        }

        Files.copy(file.getInputStream(),
                destinationPath,
                StandardCopyOption.REPLACE_EXISTING);



        Report report=new Report();
        report.setTestName(testName);
        report.setDate(LocalDate.parse(date));
        report.setFileName(fileName);
        report.setPatient(patientService.getPatientByName(name));
        return reportRepository.save(report);
    }

    public Report update(int id, MultipartFile file, String testName, String date) throws IOException {

        Report report = reportRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("report id not found"));

        report.setTestName(testName);
        report.setDate(LocalDate.parse(date));

        if (file != null && !file.isEmpty()) {

            FileUtility.validateFile(file);

            String fileName = LocalDate.now(ZoneId.systemDefault()) + "_" + file.getOriginalFilename();

            Path uploadPath = Paths.get(UPLOAD_LOC)
                    .toAbsolutePath()
                    .normalize();

            Path destinationPath = uploadPath.resolve(fileName)
                    .normalize();

            if (!destinationPath.startsWith(uploadPath)) {
                throw new IllegalArgumentException("Invalid file path");
            }

            Files.copy(
                    file.getInputStream(),
                    destinationPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            report.setFileName(fileName);
        }

        return reportRepository.save(report);
    }

    public void delete(int id) {
        Report report=reportRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Report not found")
        );
        report.setIsActive(false);
        reportRepository.save(report);
    }

    public ReportResDto getReports(int page, int size, String name) {
        Pageable pageable=PageRequest.of(page,size,Sort.by("date").descending());
        Page<Report> reportPage=reportRepository.findAllByPatientUserNameAndIsActive(name,true,pageable);
        List<ReportDto> reportDtoList=reportPage
                .stream().map(reportMapper::mapEntityToDto).toList();
        return reportMapper.mapDtoToResDto(reportDtoList,reportPage);
    }

    public ReportResDto getReportsByDoctor(int appId, int page, int size) {
        String patientName=appointmentService.getById(appId).getPatient().getUser().getName();
        return getReports(page,size,patientName);
    }

    public  Resource getFilePath(int id) throws MalformedURLException {

        Report report = reportRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Report not found"));
        String path=Paths.get(UPLOAD_LOC,report.getFileName()).toString();
        return new UrlResource(Paths.get(path).toUri());


    }


}
