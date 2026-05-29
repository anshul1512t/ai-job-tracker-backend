-- ============================================
-- JOB APPLICATIONS SEED DATA WITH AI-FRIENDLY
-- DETAILED JOB DESCRIPTIONS
-- ============================================

INSERT INTO job_applications (
    application_date,
    created_at,
    id,
    salary_expectation,
    updated_at,
    user_id,
    company_name,
    job_title,
    job_url,
    location,
    notes,
    job_type,
    status,
    job_description
) VALUES

-- USER 1
(
    '2026-05-01',
    '2026-05-17 17:50:36',
    1,
    1200000,
    '2026-05-17 17:50:36',
    1,
    'Google',
    'Backend Engineer Intern',
    'https://careers.google.com/jobs/1',
    'Bangalore',
    'Applied through careers page',
    'INTERNSHIP',
    'APPLIED',
    'Google is hiring a Backend Engineer Intern for the Bangalore office. The role involves developing scalable backend services, REST APIs, distributed systems, and cloud-native applications. Candidates should have strong knowledge of Java, Python, or Go along with data structures and algorithms. Experience with databases such as MySQL or PostgreSQL and understanding of system design concepts is preferred. Responsibilities include writing clean code, debugging production issues, collaborating with cross-functional teams, and improving service reliability. Required qualifications include pursuing or recently completing a Bachelor''s or Master''s degree in Computer Science or related field. Preferred skills include Git, Docker, Kubernetes, CI/CD pipelines, and familiarity with Google Cloud Platform.'
),

(
    '2026-05-02',
    '2026-05-17 17:50:36',
    2,
    1800000,
    '2026-05-17 17:50:36',
    1,
    'Microsoft',
    'Software Engineer',
    'https://careers.microsoft.com/jobs/2',
    'Hyderabad',
    'First round completed',
    'FULL_TIME',
    'INTERVIEW',
    'Microsoft is looking for a Software Engineer with strong expertise in full-stack application development, cloud computing, and scalable systems. The role requires proficiency in C#, Java, or Python, along with experience in Azure services, microservices architecture, and SQL databases. Responsibilities include designing enterprise-grade applications, optimizing performance, writing unit tests, and participating in agile development cycles. Candidates should have 1-3 years of experience in software development and a Bachelor''s degree in Computer Science or equivalent. Knowledge of React, Node.js, Docker, Kubernetes, and DevOps practices is considered an advantage.'
),

(
    '2026-04-20',
    '2026-05-17 17:50:36',
    3,
    1400000,
    '2026-05-17 17:50:36',
    1,
    'Amazon',
    'Java Developer',
    'https://amazon.jobs/3',
    'Pune',
    'Rejected after OA',
    'FULL_TIME',
    'REJECTED',
    'Amazon is hiring a Java Developer to build scalable backend systems and high-performance APIs for e-commerce platforms. Candidates must have strong expertise in Java, Spring Boot, RESTful services, and distributed systems. Responsibilities include designing backend services, integrating databases, implementing caching mechanisms, and ensuring system scalability and reliability. Experience with AWS cloud services, Kafka, Redis, and CI/CD pipelines is preferred. Applicants should have a Bachelor''s degree in Computer Science and at least 2 years of software development experience. Strong problem-solving and DSA skills are required.'
),

(
    '2026-05-03',
    '2026-05-17 17:50:36',
    4,
    1600000,
    '2026-05-17 17:50:36',
    1,
    'Adobe',
    'Full Stack Developer',
    'https://adobe.com/careers/4',
    'Noida',
    'Waiting for recruiter response',
    'FULL_TIME',
    'IN_REVIEW',
    'Adobe is seeking a Full Stack Developer responsible for building responsive web applications and scalable backend systems. The candidate should have experience with React.js, TypeScript, Node.js, Java, Spring Boot, and MongoDB. Responsibilities include developing UI components, creating REST APIs, optimizing performance, and collaborating with product teams. Knowledge of cloud services such as AWS or Azure, containerization tools, and CI/CD workflows is preferred. Required qualifications include a Bachelor''s degree in Computer Science and experience with agile software development.'
),

(
    '2026-05-04',
    '2026-05-17 17:50:36',
    5,
    1500000,
    '2026-05-17 17:50:36',
    1,
    'Flipkart',
    'Frontend Developer',
    'https://flipkartcareers.com/5',
    'Remote',
    'Offer received',
    'FULL_TIME',
    'OFFER',
    'Flipkart is hiring a Frontend Developer for remote work. The role focuses on developing modern e-commerce interfaces using React.js, Redux, TypeScript, HTML5, CSS3, and responsive design principles. Responsibilities include implementing UI features, optimizing page performance, integrating APIs, and collaborating with backend engineers and designers. Candidates should have strong knowledge of JavaScript frameworks, browser rendering, accessibility standards, and frontend testing tools. Experience with Next.js, GraphQL, and performance optimization is preferred. A Bachelor''s degree and 1-3 years of frontend experience are required.'
),

(
    '2026-05-05',
    '2026-05-17 17:50:36',
    6,
    1450000,
    '2026-05-17 17:50:36',
    1,
    'Oracle',
    'Java Backend Engineer',
    'https://oracle.com/jobs/6',
    'Bangalore',
    'Applied via referral',
    'FULL_TIME',
    'APPLIED',
    'Oracle is hiring a Java Backend Engineer to build enterprise-scale backend applications and cloud services. Required skills include Java, Spring Boot, Hibernate, SQL, REST APIs, and distributed system concepts. Responsibilities include writing scalable services, database optimization, debugging production issues, and collaborating with global engineering teams. Candidates should understand microservices architecture, CI/CD pipelines, Docker, and Kubernetes. Experience with Oracle Cloud Infrastructure is a plus. Applicants should possess a Bachelor''s degree in Computer Science and at least 2 years of backend development experience.'
),

(
    '2026-05-07',
    '2026-05-17 17:50:36',
    8,
    2500000,
    '2026-05-17 17:50:36',
    1,
    'Netflix',
    'Backend Engineer',
    'https://netflix.jobs/8',
    'Remote',
    'DSA interview scheduled',
    'REMOTE',
    'INTERVIEW',
    'Netflix is seeking a Backend Engineer to develop highly scalable streaming infrastructure and cloud-native backend services. Candidates should have expertise in Java, Kotlin, distributed systems, microservices, and AWS cloud technologies. Responsibilities include designing resilient systems, implementing APIs, monitoring performance, and improving reliability for large-scale applications serving millions of users. Strong knowledge of data structures, algorithms, concurrency, and system design is essential. Preferred skills include Kafka, Cassandra, Redis, Docker, and Kubernetes. Bachelor''s degree and strong backend engineering experience are required.'
),

(
    '2026-05-15',
    '2026-05-17 17:50:36',
    15,
    1350000,
    '2026-05-17 17:50:36',
    1,
    'IBM',
    'Application Developer',
    'https://ibm.com/careers/15',
    'Bangalore',
    'Accepted final offer',
    'FULL_TIME',
    'ACCEPTED',
    'IBM is hiring an Application Developer responsible for enterprise software development and cloud-based solutions. The role requires experience in Java, Spring Framework, REST APIs, SQL databases, and microservices architecture. Responsibilities include coding, debugging, unit testing, software deployment, and collaborating with stakeholders in agile teams. Preferred qualifications include knowledge of cloud platforms such as AWS or Azure, DevOps tools, Docker, Kubernetes, and CI/CD pipelines. Candidates should hold a Bachelor''s degree in Computer Science or Information Technology and possess strong analytical and communication skills.'
),

-- USER 2
(
    '2026-05-05',
    '2026-05-17 17:50:36',
    16,
    1100000,
    '2026-05-21 12:10:09',
    2,
    'Swiggy',
    'React Developer',
    'https://swiggy.com/jobs/16',
    'Pune',
    'Applied via LinkedIn',
    'HYBRID',
    'OFFER',
    'Swiggy is hiring a React Developer for hybrid work in Pune. The role involves building responsive web interfaces and improving customer-facing food delivery platforms. Candidates should have strong knowledge of React.js, Redux, JavaScript, TypeScript, HTML, CSS, and REST APIs. Responsibilities include frontend architecture, API integration, performance optimization, debugging, and collaborating with UI/UX designers. Familiarity with Next.js, testing frameworks, and responsive design principles is preferred. Applicants should have a Bachelor''s degree and 1-3 years of frontend development experience.'
),

(
    '2026-05-06',
    '2026-05-17 17:50:36',
    17,
    1300000,
    '2026-05-17 17:50:36',
    2,
    'Zomato',
    'Spring Boot Developer',
    'https://zomato.com/jobs/17',
    'Delhi',
    'Technical interview tomorrow',
    'HYBRID',
    'INTERVIEW',
    'Zomato is looking for a Spring Boot Developer to design scalable backend services for food delivery and restaurant management systems. Required skills include Java, Spring Boot, REST APIs, MySQL, Redis, Kafka, and microservices architecture. Responsibilities include backend development, database optimization, debugging distributed systems, and collaborating with frontend teams. Experience with AWS, Docker, Kubernetes, and CI/CD tools is preferred. Candidates should have strong problem-solving abilities, understanding of DSA, and a Bachelor''s degree in Computer Science.'
),

(
    '2026-05-07',
    '2026-05-17 17:50:36',
    18,
    1700000,
    '2026-05-17 17:50:36',
    2,
    'Paytm',
    'Backend Engineer',
    'https://paytm.com/careers/18',
    'Noida',
    'Accepted offer',
    'FULL_TIME',
    'ACCEPTED',
    'Paytm is hiring a Backend Engineer to develop secure fintech applications and payment systems. Candidates should be skilled in Java, Spring Boot, distributed systems, RESTful APIs, SQL and NoSQL databases. Responsibilities include developing financial transaction systems, implementing security standards, optimizing APIs, and maintaining high availability services. Preferred qualifications include knowledge of Kafka, Redis, AWS, Docker, and scalable architecture patterns. Applicants should possess strong DSA knowledge and experience with backend development.'
),

-- USER 3
(
    '2026-05-02',
    '2026-05-17 17:50:36',
    32,
    3000000,
    '2026-05-17 17:50:36',
    3,
    'Meta',
    'Frontend Engineer',
    'https://metacareers.com/jobs/32',
    'Remote',
    'Phone screening completed',
    'REMOTE',
    'INTERVIEW',
    'Meta is hiring a Frontend Engineer to build scalable social media and communication platforms. The role requires strong expertise in React.js, JavaScript, TypeScript, frontend architecture, GraphQL, and performance optimization. Responsibilities include developing reusable UI components, collaborating with backend engineers, improving user experience, and ensuring accessibility compliance. Candidates should understand browser internals, state management, testing frameworks, and responsive design. Experience with large-scale applications and system design is preferred. Bachelor''s degree and strong frontend engineering skills are required.'
),

(
    '2026-05-03',
    '2026-05-17 17:50:36',
    33,
    2800000,
    '2026-05-17 17:50:36',
    3,
    'Apple',
    'iOS Developer',
    'https://apple.com/careers/33',
    'Hyderabad',
    'Waiting for technical round',
    'FULL_TIME',
    'IN_REVIEW',
    'Apple is seeking an iOS Developer responsible for building high-performance mobile applications and user-centric experiences. Candidates should have expertise in Swift, Objective-C, UIKit, SwiftUI, REST APIs, and mobile architecture patterns. Responsibilities include developing iOS applications, optimizing app performance, integrating backend services, writing unit tests, and collaborating with designers and product teams. Experience with CI/CD pipelines, Xcode tools, and App Store deployment processes is preferred. Bachelor''s degree in Computer Science and prior mobile development experience are required.'
),

(
    '2026-05-05',
    '2026-05-17 17:50:36',
    35,
    2400000,
    '2026-05-17 17:50:36',
    3,
    'Spotify',
    'Backend Developer',
    'https://spotifyjobs.com/35',
    'Remote',
    'Offer discussion in progress',
    'REMOTE',
    'OFFER',
    'Spotify is hiring a Backend Developer to build scalable music streaming infrastructure and recommendation systems. Required skills include Java, Kotlin, Python, microservices architecture, REST APIs, and distributed systems. Responsibilities include developing backend services, improving recommendation engines, optimizing system performance, and ensuring scalability for millions of users. Preferred experience includes Kafka, Cassandra, Redis, Docker, Kubernetes, and AWS cloud services. Candidates should possess strong problem-solving abilities, system design knowledge, and software engineering experience.'
),

(
    '2026-05-10',
    '2026-05-17 17:50:36',
    40,
    1700000,
    '2026-05-17 17:50:36',
    3,
    'RedHat',
    'Java Middleware Developer',
    'https://redhat.com/jobs/40',
    'Pune',
    'Accepted after final HR round',
    'FULL_TIME',
    'ACCEPTED',
    'RedHat is hiring a Java Middleware Developer to build enterprise middleware solutions and cloud-native backend services. Candidates should have expertise in Java, Spring Boot, Linux systems, middleware technologies, REST APIs, and microservices architecture. Responsibilities include developing scalable backend systems, debugging production issues, integrating enterprise services, and improving deployment pipelines. Knowledge of OpenShift, Docker, Kubernetes, CI/CD, and distributed systems is highly preferred. Applicants should have a Bachelor''s degree and strong backend engineering experience.'
),

(
    '2026-05-15',
    '2026-05-17 17:50:36',
    45,
    2100000,
    '2026-05-17 17:50:36',
    3,
    'Qualcomm',
    'Embedded Systems Engineer',
    'https://qualcomm.com/jobs/45',
    'Hyderabad',
    'Offer letter expected soon',
    'FULL_TIME',
    'OFFER',
    'Qualcomm is seeking an Embedded Systems Engineer to work on low-level software and hardware integration for mobile chipsets and IoT devices. Required skills include C, C++, embedded Linux, RTOS, device drivers, debugging tools, and computer architecture concepts. Responsibilities include firmware development, performance optimization, hardware-software integration, debugging embedded systems, and collaborating with hardware teams. Preferred qualifications include experience with ARM processors, networking protocols, kernel programming, and embedded development tools. Candidates should hold a Bachelor''s or Master''s degree in Electronics, Computer Science, or related field.'
);