package com.amaze.controller;

import com.amaze.dto.GraphDto;
import com.amaze.dto.StatDto;
import com.amaze.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    private AdminService adminService;

    @GetMapping("/stat")
    public StatDto getStats(){
        return adminService.getStats();
    }

    @GetMapping("/graph1")
    public GraphDto getGraph1(){
        return adminService.getGraph1();
    }
    @GetMapping("/graph2")
    public GraphDto getGraph2(){
        return adminService.getGraph2();
    }
}
