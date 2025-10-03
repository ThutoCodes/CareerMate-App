import express from "express";
import OpenAI from "openai";

const router = express.Router();

// initialize OpenAI client
const openai = new OpenAI({
  apiKey: process.env.OPENAI_API_KEY,
});

router.post("/", async (req, res) => {
  try {
    const { jobDescription } = req.body;
    if (!jobDescription) {
      return res.status(400).json({ error: "Job description required" });
    }

    const prompt = `You are a career coach AI. Given the following job description, provide:
1. Key skills
2. Interview preparation tips
3. 3 mock interview questions

Job Description:
${jobDescription}`;

    const modelName = process.env.OPENAI_MODEL || "gpt-4o-mini";

    const response = await openai.responses.create({
      model: modelName,
      input: prompt,
      max_output_tokens: 500,
    });

    // extract text
    let aiText = response.output_text || "No response";

    res.json({ tips: aiText.trim() });
  } catch (error) {
    console.error("OpenAI error:", error);
    res.status(500).json({ error: "Something went wrong", details: String(error) });
  }
});

export default router;