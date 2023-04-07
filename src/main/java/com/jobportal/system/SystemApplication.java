package com.jobportal.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jobportal.system.entity.Candidate;
import com.jobportal.system.entity.Certification;
import com.jobportal.system.entity.Education;
import com.jobportal.system.entity.Employer;
import com.jobportal.system.entity.File;
import com.jobportal.system.entity.Job;
import com.jobportal.system.entity.Role;
import com.jobportal.system.entity.User;
import com.jobportal.system.entity.VerificationToken;
import com.jobportal.system.entity.WorkExperience;

@SpringBootApplication
public class SystemApplication  implements CommandLineRunner{

	
	  
	public static Long idautoPlus = 50l;

	public static List<Candidate> candidates = new ArrayList<>();
	public static List<Certification> certifications = new ArrayList<>();
	public static List<Education> educations = new ArrayList<>();
	public static List<Employer> employers = new ArrayList<>();
	public static List<Job> jobs = new ArrayList<>();
	public static List<User> users = new ArrayList<>();
	public static List<WorkExperience> workExperiences = new ArrayList<>();
	public static List<File> files = new ArrayList<>();
	public static final Set<Role> employerRole = new HashSet<>(List.of(new Role(3l,"EMPLOYER")));
	public static final Set<Role> candidateRole = new HashSet<>(List.of(new Role(2l, "CANDIDATE")));
	public static final Set<Role> adminRole = new HashSet<>(List.of(new Role(1l, "ADMIN")));
	

	public static List<VerificationToken> verificationTokens = new ArrayList<>();

	@Autowired
	PasswordEncoder passwordEncoder;

	

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
          
		if (users.isEmpty()) {

			




			// adding amdin 
		
			User adminUser = User.builder().id(1l).firstName("Admin").lastName("JobPortal").username("admin").email("tk967724@gmail.com")
					.contact("9112589961").adress("Pune").enabled(true).password(passwordEncoder.encode("Admin@123")).roles(adminRole).build();

			


			User employerUser = User.builder().id(4l).firstName("Brijesh").lastName("Yadav").username("Brijesh").email("byadav2751@gmail.com").adress("pune").contact("9112589991").enabled(true).password(passwordEncoder.encode("Brijesh@123")).roles(employerRole).build();


			//Adding Employer and Jobs
			Job job1 =  Job.builder().id(1l).title("Java Developer").description(
					"We are looking for highly skilled programmers with experience building web applications in Java. Java Developers are responsible for analyzing user requirements and business objectives, determining application features and functionality, and recommending changes to existing Java-based applications, among other duties.\nJava Developers need to compile detailed technical documentation and user assistance material, requiring excellent written communication.")
					.location("Pune").jobType("Full Time")
					.requirements(new ArrayList<String>(Arrays.asList("Java Associate Developer"))).build();
					
					

			Employer employer1 = Employer.builder().user(employerUser).company("Tichkule and Tichkule Products")
					.industry("IT").createdJobs(List.of(job1)).build();

					job1.setEmployer(employer1);
			User user1 = User.builder().id(2l).firstName("Rithik").lastName("Roshan").username("ritik").email("rithikgondralwar25@gmail.com")
					.contact("9112589971").adress("Pune").enabled(true).password(passwordEncoder.encode("Ritik@123")).roles(candidateRole).build();
			
			Candidate candidate1 = Candidate.builder().user(user1).appliedJobs(jobs).skills(List.of("Java","Spring Boot")).build();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2018);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date startDatee = cal.getTime();

			cal.set(Calendar.YEAR, 2018);
			Date endDatee = cal.getTime();


			Education education1 = Education.builder().school("Red Rose School").degree("B.Tech.").fieldOfStudy("CSE")
					.startDate(startDatee).endDate(endDatee).grade("8")
					.description("this is description of red rose school").build();




			//certifications

			 cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2022);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date issueDatee = cal.getTime();

		


			Certification certification1 = Certification.builder().name("OCJA").issuingOrganization("ORACLE")
					.issueDate(issueDatee).credentialId("OCJA123454").build();

			//workExperiences

			
			cal.set(Calendar.YEAR, 2022);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			startDatee = cal.getTime();

			cal.set(Calendar.YEAR, 2023);
			endDatee = cal.getTime();



			WorkExperience workExperience1 = WorkExperience.builder().companyName("tcs").employementType("Full time")
					.isCurrentlyWorking(false).location("Lucknow").title("java Developer")
					.startDate(startDatee)
					.endDate(endDatee).build();




			job1.setCandidatesApplied(List.of(candidate1));
			candidate1.setEducation(List.of(education1));
			candidate1.setCertification(List.of(certification1));
			candidate1.setWorkExperiences(List.of(workExperience1));
					

	users.add(adminUser);
	users.add(user1);
	users.add(employerUser);
	educations.add(education1);
	employers.add(employer1);
	candidates.add(candidate1);
	jobs.add(job1);
	certifications.add(certification1);

	workExperiences.add(workExperience1);

	System.out.println(users);

	










			





		}

	}


}
