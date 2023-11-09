package com.uiautofree.controller.job;

import com.uiautofree.runner.service.JobService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description job
 * @author tsbxmw
 * @date 2023-07-06
 */
@RestController
@RequestMapping(value = "/api/job")
public class JobController {

    @Resource
    private JobService jobService;

}