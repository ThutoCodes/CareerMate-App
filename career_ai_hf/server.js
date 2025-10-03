import "./config.js"; // <-- load env first

import express from "express";
import cors from "cors";
import jobDescriptionRouter from "./routes/jobDescription.js";

const app = express();

// middleware
app.use(cors());
app.use(express.json());

// routes
app.use("/analyze", jobDescriptionRouter);

// start server
const PORT = process.env.PORT || 5000;
app.listen(PORT, "0.0.0.0", () => {
  console.log(`🚀 Server running on http://0.0.0.0:${PORT}`);
});