package com.ai.job_tracker.service;

import com.ai.job_tracker.dto.request.AddJobApplicationRequestDTO;
import com.ai.job_tracker.dto.request.UpdateJobRequestDTO;
import com.ai.job_tracker.dto.response.JobApplicationResponseDTO;
import com.ai.job_tracker.enums.ApplicationStatus;
import com.ai.job_tracker.enums.JobType;
import com.ai.job_tracker.exception.JobApplicationIdNotExist;
import com.ai.job_tracker.model.JobApplication;
import com.ai.job_tracker.model.User;
import com.ai.job_tracker.repository.JobApplicationRepository;
import com.ai.job_tracker.utils.HelperFX;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @InjectMocks
    private JobApplicationService jobApplicationService;

    private User mockUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Anshul");
        user.setEmail("anshul@gmail.com");
        return user;
    }

    private JobApplication mockJob() {
        JobApplication job = new JobApplication();
        job.setId(10L);
        job.setCompanyName("Google");
        job.setJobTitle("Java Developer");
        job.setStatus(ApplicationStatus.APPLIED);
        job.setJobType(JobType.FULL_TIME);
        job.setLocation("Delhi");
        job.setSalaryExceptation(800000L);
        job.setJobDescription("Backend role");
        job.setNotes("Good company");
        job.setUser(mockUser());
        return job;
    }

    @Test
    void createJob_shouldSaveJobWithCurrentUser() {
        User user = mockUser();
        AddJobApplicationRequestDTO request = new AddJobApplicationRequestDTO();

        JobApplication job = mockJob();
        JobApplication savedJob = mockJob();
        JobApplicationResponseDTO responseDTO = new JobApplicationResponseDTO();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(modelMapper.map(request, JobApplication.class)).thenReturn(job);
            when(jobApplicationRepository.save(job)).thenReturn(savedJob);
            when(modelMapper.map(savedJob, JobApplicationResponseDTO.class)).thenReturn(responseDTO);

            JobApplicationResponseDTO result = jobApplicationService.createJob(request);

            assertNotNull(result);
            assertEquals(user, job.getUser());
            verify(jobApplicationRepository).save(job);
        }
    }

    @Test
    void getAllJobs_shouldReturnAllJobs_whenNoStatusAndNoKeyword() {
        User user = mockUser();
        Pageable pageable = PageRequest.of(0, 10);
        JobApplication job = mockJob();

        Page<JobApplication> page = new PageImpl<>(List.of(job));
        JobApplicationResponseDTO responseDTO = new JobApplicationResponseDTO();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByUserId(1L, pageable)).thenReturn(page);
            when(modelMapper.map(job, JobApplicationResponseDTO.class)).thenReturn(responseDTO);

            Page<JobApplicationResponseDTO> result =
                    jobApplicationService.getAlljobs(pageable, null, null);

            assertEquals(1, result.getTotalElements());
            verify(jobApplicationRepository).findByUserId(1L, pageable);
        }
    }

    @Test
    void getAllJobs_shouldFilterByStatus() {
        User user = mockUser();
        Pageable pageable = PageRequest.of(0, 10);
        JobApplication job = mockJob();

        Page<JobApplication> page = new PageImpl<>(List.of(job));

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByUserIdAndStatus(
                    1L, ApplicationStatus.APPLIED, pageable
            )).thenReturn(page);

            when(modelMapper.map(job, JobApplicationResponseDTO.class))
                    .thenReturn(new JobApplicationResponseDTO());

            Page<JobApplicationResponseDTO> result =
                    jobApplicationService.getAlljobs(pageable, ApplicationStatus.APPLIED, null);

            assertEquals(1, result.getTotalElements());
            verify(jobApplicationRepository)
                    .findByUserIdAndStatus(1L, ApplicationStatus.APPLIED, pageable);
        }
    }

    @Test
    void getAllJobs_shouldSearchByKeyword() {
        User user = mockUser();
        Pageable pageable = PageRequest.of(0, 10);
        JobApplication job = mockJob();

        Page<JobApplication> page = new PageImpl<>(List.of(job));

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.searchByUserAndKeyword(
                    1L, "java", pageable
            )).thenReturn(page);

            when(modelMapper.map(job, JobApplicationResponseDTO.class))
                    .thenReturn(new JobApplicationResponseDTO());

            Page<JobApplicationResponseDTO> result =
                    jobApplicationService.getAlljobs(pageable, null, "java");

            assertEquals(1, result.getTotalElements());
            verify(jobApplicationRepository)
                    .searchByUserAndKeyword(1L, "java", pageable);
        }
    }

    @Test
    void getAllJobs_shouldFilterByStatusAndKeyword() {
        User user = mockUser();
        Pageable pageable = PageRequest.of(0, 10);
        JobApplication job = mockJob();

        Page<JobApplication> page = new PageImpl<>(List.of(job));

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.searchByUserStatusAndKeyword(
                    1L, ApplicationStatus.INTERVIEW, "google", pageable
            )).thenReturn(page);

            when(modelMapper.map(job, JobApplicationResponseDTO.class))
                    .thenReturn(new JobApplicationResponseDTO());

            Page<JobApplicationResponseDTO> result =
                    jobApplicationService.getAlljobs(
                            pageable,
                            ApplicationStatus.INTERVIEW,
                            "google"
                    );

            assertEquals(1, result.getTotalElements());
            verify(jobApplicationRepository)
                    .searchByUserStatusAndKeyword(
                            1L,
                            ApplicationStatus.INTERVIEW,
                            "google",
                            pageable
                    );
        }
    }

    @Test
    void getJobById_shouldReturnJob_whenJobExists() {
        User user = mockUser();
        JobApplication job = mockJob();
        JobApplicationResponseDTO responseDTO = new JobApplicationResponseDTO();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(10L, 1L))
                    .thenReturn(Optional.of(job));

            when(modelMapper.map(job, JobApplicationResponseDTO.class))
                    .thenReturn(responseDTO);

            JobApplicationResponseDTO result = jobApplicationService.getJobById(10L);

            assertNotNull(result);
            verify(jobApplicationRepository).findByIdAndUserId(10L, 1L);
        }
    }

    @Test
    void getJobById_shouldThrowException_whenJobNotFound() {
        User user = mockUser();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(99L, 1L))
                    .thenReturn(Optional.empty());

            assertThrows(JobApplicationIdNotExist.class,
                    () -> jobApplicationService.getJobById(99L));

            verifyNoInteractions(modelMapper);
        }
    }

    @Test
    void deleteJob_shouldDeleteJob_whenJobExists() {
        User user = mockUser();
        JobApplication job = mockJob();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(10L, 1L))
                    .thenReturn(Optional.of(job));

            jobApplicationService.deleteJob(10L);

            verify(jobApplicationRepository).delete(job);
        }
    }

    @Test
    void deleteJob_shouldThrowException_whenJobNotFound() {
        User user = mockUser();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(99L, 1L))
                    .thenReturn(Optional.empty());

            assertThrows(JobApplicationIdNotExist.class,
                    () -> jobApplicationService.deleteJob(99L));

            verify(jobApplicationRepository, never()).delete(any());
        }
    }

    @Test
    void updateJob_shouldUpdateStatusAndLocation() {
        User user = mockUser();
        JobApplication job = mockJob();

        UpdateJobRequestDTO dto = new UpdateJobRequestDTO();
        dto.setStatus(ApplicationStatus.INTERVIEW);
        dto.setLocation("Bangalore");

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(10L, 1L))
                    .thenReturn(Optional.of(job));

            when(jobApplicationRepository.save(job)).thenReturn(job);
            when(modelMapper.map(job, JobApplicationResponseDTO.class))
                    .thenReturn(new JobApplicationResponseDTO());

            jobApplicationService.updateJob(10L, dto);

            assertEquals(ApplicationStatus.INTERVIEW, job.getStatus());
            assertEquals("Bangalore", job.getLocation());
            verify(jobApplicationRepository).save(job);
        }
    }

    @Test
    void updateJob_shouldUpdateSalaryExpectation() {
        User user = mockUser();
        JobApplication job = mockJob();

        UpdateJobRequestDTO dto = new UpdateJobRequestDTO();
        dto.setSalaryExpectation(1200000L);

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(10L, 1L))
                    .thenReturn(Optional.of(job));

            when(jobApplicationRepository.save(job)).thenReturn(job);
            when(modelMapper.map(job, JobApplicationResponseDTO.class))
                    .thenReturn(new JobApplicationResponseDTO());

            jobApplicationService.updateJob(10L, dto);

            assertEquals(1200000L, job.getSalaryExceptation());
        }
    }

    @Test
    void updateJob_shouldUpdateJobType() {
        User user = mockUser();
        JobApplication job = mockJob();

        UpdateJobRequestDTO dto = new UpdateJobRequestDTO();
        dto.setJobType(JobType.INTERNSHIP);

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(10L, 1L))
                    .thenReturn(Optional.of(job));

            when(jobApplicationRepository.save(job)).thenReturn(job);
            when(modelMapper.map(job, JobApplicationResponseDTO.class))
                    .thenReturn(new JobApplicationResponseDTO());

            jobApplicationService.updateJob(10L, dto);

            assertEquals(JobType.INTERNSHIP, job.getJobType());
        }
    }

    @Test
    void updateJob_shouldUpdateJobDescription() {
        User user = mockUser();
        JobApplication job = mockJob();

        UpdateJobRequestDTO dto = new UpdateJobRequestDTO();
        dto.setJobDescription("Updated backend developer role");

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(10L, 1L))
                    .thenReturn(Optional.of(job));

            when(jobApplicationRepository.save(job)).thenReturn(job);
            when(modelMapper.map(job, JobApplicationResponseDTO.class))
                    .thenReturn(new JobApplicationResponseDTO());

            jobApplicationService.updateJob(10L, dto);

            assertEquals("Updated backend developer role", job.getJobDescription());
        }
    }

    @Test
    void updateJob_shouldUpdateNotes() {
        User user = mockUser();
        JobApplication job = mockJob();

        UpdateJobRequestDTO dto = new UpdateJobRequestDTO();
        dto.setNotes("Interview scheduled");

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(10L, 1L))
                    .thenReturn(Optional.of(job));

            when(jobApplicationRepository.save(job)).thenReturn(job);
            when(modelMapper.map(job, JobApplicationResponseDTO.class))
                    .thenReturn(new JobApplicationResponseDTO());

            jobApplicationService.updateJob(10L, dto);

            assertEquals("Interview scheduled", job.getNotes());
        }
    }

    @Test
    void updateJob_shouldThrowException_whenJobNotFound() {
        User user = mockUser();
        UpdateJobRequestDTO dto = new UpdateJobRequestDTO();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.findByIdAndUserId(99L, 1L))
                    .thenReturn(Optional.empty());

            assertThrows(JobApplicationIdNotExist.class,
                    () -> jobApplicationService.updateJob(99L, dto));

            verify(jobApplicationRepository, never()).save(any());
        }
    }

    @Test
    void getTotalApplications_shouldReturnCount() {
        User user = mockUser();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.countByUserId(1L)).thenReturn(5L);

            long result = jobApplicationService.getTotalApplications();

            assertEquals(5L, result);
        }
    }

    @Test
    void getTotalInterviews_shouldReturnInterviewCount() {
        User user = mockUser();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.countByUserIdAndStatus(
                    1L,
                    ApplicationStatus.INTERVIEW
            )).thenReturn(2L);

            long result = jobApplicationService.getTotalInterviews();

            assertEquals(2L, result);
        }
    }

    @Test
    void getTotalOffers_shouldReturnOfferCount() {
        User user = mockUser();

        try (MockedStatic<HelperFX> mockedHelper = Mockito.mockStatic(HelperFX.class)) {
            mockedHelper.when(HelperFX::getCurrentUser).thenReturn(user);

            when(jobApplicationRepository.countByUserIdAndStatus(
                    1L,
                    ApplicationStatus.OFFER
            )).thenReturn(1L);

            long result = jobApplicationService.getTotalOffers();

            assertEquals(1L, result);
        }
    }
}