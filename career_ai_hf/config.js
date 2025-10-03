// config.js
import dotenv from "dotenv";

// load environment variables
dotenv.config({ path: ".env" });

// sanity check
if (!process.env.OPENAI_API_KEY) {
  console.error("❌ OPENAI_API_KEY is missing! Make sure your .env file exists and is correct.");
  process.exit(1);
} else {
  console.log("✅ OPENAI_API_KEY loaded successfully!");
}