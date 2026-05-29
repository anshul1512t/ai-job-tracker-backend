package com.ai.job_tracker.ai.prompts;

import org.springframework.ai.chat.prompt.PromptTemplate;

public class SkillGapPromptTemplate {

  public static final PromptTemplate template = new PromptTemplate("""
      Compare the resume with the job description.

Analyze:
- missing technical skills
- missing tools/frameworks
- experience gaps
- keyword match
- ATS/job fit

Return ONLY valid JSON:

{{
  "matchScore": 0,
  "missingSkills": [],
  "recommendedSkills": [],
  "summary": ""
  }}

Resume:
{resume}

Job Description:
{jobDescription}
      """);
}