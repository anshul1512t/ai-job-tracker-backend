package com.ai.job_tracker.ai.prompts;

import org.springframework.ai.chat.prompt.PromptTemplate;

public class CoverLetterPromptTemplate {

    public static final PromptTemplate template = new PromptTemplate("""
        You are an expert professional career assistant.

        Generate a professional, concise, and ATS-friendly cover letter.

        Rules:
        - Keep tone professional
        - Keep length around 150-200 words
        - Focus on relevant skills and experience
        - Do not hallucinate fake experience
        - Use proper formatting
        - Make it personalized for the job role

        Resume:
        {resume}

        Job Description:
        {jobDescription}

        Return response in JSON format:

        {{
        "coverLetter": "generated cover letter here"
    }}
        """);
}