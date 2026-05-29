package com.ai.job_tracker.ai.prompts;

import org.springframework.ai.chat.prompt.PromptTemplate;

public class ResumeFeedbackPromptTemplate {

  public static final PromptTemplate template = new PromptTemplate("""
      Review the resume professionally.

Focus on:
- ATS friendliness
- formatting
- clarity
- project quality
- experience descriptions
- grammar
- impact of achievements
- resume structure

Return ONLY valid JSON:

{{
  "score": 0,
  "strengths": [],
  "improvements": [],
  "summary": ""
  }}

Resume:
{resume}
      """);
}