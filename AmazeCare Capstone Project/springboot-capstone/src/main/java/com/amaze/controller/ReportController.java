package com.amaze.controller;

import com.amaze.dto.ReportResDto;
import com.amaze.model.Report;
import com.amaze.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/report")
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    private ReportService reportService;

    @PostMapping("/add")
    public Report addReport(
            @RequestParam("file") MultipartFile file,
            @RequestParam String testName,
            @RequestParam String date,
            Principal principal
            ) throws IOException {
        return reportService.add(file,testName,date,principal.getName());
    }

    @PutMapping("/update/{id}")
    public Report updateById(
            @PathVariable int id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam String testName,
            @RequestParam String date
    ) throws IOException{
       return  reportService.update(id,file,testName,date);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(
            @PathVariable int id
    ){
        reportService.delete(id);
    }

    @GetMapping("/all")
    public ReportResDto getAll(
            Principal principal,
            @RequestParam int page,
            @RequestParam int size
    ){
        return reportService.getReports(page, size, principal.getName());
    }

    @GetMapping("/all/{appId}")
    public ReportResDto getAll(
            @PathVariable int appId,
            @RequestParam int page,
            @RequestParam int size

    ){
        return reportService.getReportsByDoctor(appId, page, size);
    }



    @GetMapping("/path/{id}")
    public ResponseEntity<Resource> getFilePath(
            @PathVariable int id
    ) throws IOException {
        Resource resource=reportService.getFilePath(id);
        Path path = resource.getFile().toPath();

        String contentType = Files.probeContentType(path);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);


    }




}
