const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");

const app = express();

// Middleware
app.use(cors()); // Allow requests from any origin
app.use(bodyParser.json()); // Parse JSON bodies

// Test route
app.get("/", (req, res) => {
  res.send("CareerMate Backend is running 🚀");
});

// Register route
app.post("/api/users/register", (req, res) => { // match the endpoint your Android client expects
  const { username, email, password } = req.body;

  if (!username || !email || !password) {
    return res.status(400).json({ message: "All fields are required" });
  }

  // TODO: Save user to database here
  res.status(201).json({
    message: "User registered successfully",
    user: { username, email },
  });
});

// Login route
app.post("/api/users/login", (req, res) => { // match Android endpoint
  const { email, password } = req.body;

  // TODO: Replace with real DB check later
  if (email === "test@example.com" && password === "123456") {
    return res.json({ message: "Login successful", token: "fake-jwt-token" });
  } else {
    return res.status(401).json({ message: "Invalid credentials" });
  }
});

// Start server
const PORT = 3000;
app.listen(PORT, "0.0.0.0", () => {
  console.log(`Server running on http://10.0.0.105:${PORT}`);
});