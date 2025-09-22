# Career Mate 📱

Career Mate is a mobile application powered by **Artificial Intelligence** to help users create impactful CVs and prepare for interviews more effectively.  
The app is designed to **analyze job descriptions, tailor your CV, and provide interview tips** — ensuring you highlight the most relevant skills and experiences for every opportunity.  

---

## 🚀 Features (Work in Progress)
- 📝 **AI-Powered CV Builder** – Generate and refine CVs that closely match job descriptions.  
- 🤖 **Interview Preparation** – Personalized interview tips and focus areas based on the job post.  
- 🎯 **Skill Highlighting** – Identify and emphasize relevant skills to increase job relevance.  
- 🔑 **Authentication** – Secure login and registration using **Node.js REST APIs**.  
- ☁️ **Cloud Integration** – Store and sync data with **Firebase**.  

---

## 🛠️ Tech Stack
- **Android (Java/Kotlin)** – Core mobile app development.  
- **Node.js (Express)** – Backend REST APIs for authentication.  
- **Firebase** – Real-time database, storage, and cloud services.  
- **Room Database** – Local persistence for offline access.  
- **Artificial Intelligence APIs** – Used for CV analysis and interview preparation.  
- **XML (Material Design)** – UI layouts and styling.  

---

## 📸 Mockups
<img width="1536" height="1024" alt="Image" src="https://github.com/user-attachments/assets/864534d1-ac59-4598-9bb4-5b7853edba6f" />
<img width="1536" height="1024" alt="Image" src="https://github.com/user-attachments/assets/b92c571a-fa86-4c76-97ac-fad461ee426d" />
<img width="1536" height="1024" alt="Image" src="https://github.com/user-attachments/assets/ed9dfc23-0f7a-4f04-936c-6f7fbfdfb113" />

---

## 📂 Project Structure

CareerMate/
├── app/ # Source code
│ ├── java/ # Activities, Fragments, ViewModels
│ ├── res/ # Layout XMLs, drawables, values
│ └── AndroidManifest.xml
├── backend/ # Node.js REST API for auth
│ ├── server.js
│ ├── routes/
│ └── models/
├── gradle/ # Build system files
└── README.md # Documentation

yaml
Copy code

---

## 📅 Current Status
Career Mate is under **active development**.  
- ✅ UI mockups designed.  
- ✅ Authentication with Node.js backend.  
- 🔄 Firebase integration in progress.  
- 🔮 AI features (CV + Interview Prep) planned for upcoming iterations.  

---

## 📌 Roadmap
- [ ] Integrate AI for CV generation and job description analysis.  
- [ ] Implement Interview Prep module with AI-driven insights.  
- [ ] Expand dashboard for personalized career tracking.  
- [ ] Notifications & reminders for applications/interviews.  
- [ ] Dark mode support.  

---

## ⚙️ Getting Started & Local Development Setup

These instructions will get you a copy of the project up and running on your local machine for development and testing.

### Prerequisites
- Android Studio (latest stable version recommended)  
- Java Development Kit (JDK) – bundled with Android Studio  
- Git  
- Node.js and npm/yarn (for backend)  

---

### 1. Clone the Repository

```bash
git clone https://github.com/ThutoCodes/CareerMate-App.git
cd CareerMate-App
2. Backend Setup (Crucial)
This app requires a running backend server for authentication and data operations.

Default API URL: http://10.0.2.2:3000/api/

10.0.2.2 is an Android Emulator alias for your host machine (localhost).

Steps:

Navigate to backend/

Install dependencies:

bash
Copy code
npm install
(Optional) Configure .env if needed

Start the backend:

bash
Copy code
npm start
Ensure it runs on port 3000.

3. Open and Build the Android Project
Open Android Studio

Select Open an Existing Project → choose CareerMate-App

Let Gradle sync

Build the project: Build > Make Project

4. Running the App
On an Android Emulator
Ensure backend server is running

Create/start an emulator in AVD Manager

Run the app (Run > Run 'app')

It should connect to http://10.0.2.2:3000/api/

On a Physical Device
Ensure backend server is running

Connect device & dev machine to the same Wi-Fi

Get your machine’s local IP (e.g., 192.168.1.X)

Windows: ipconfig

macOS/Linux: ifconfig or ip addr

Update BACKEND_URL in app config to:

kotlin
Copy code
const val BACKEND_URL = "http://192.168.1.X:3000/api/"
Run the app on your device

✅ With this, any new contributor can clone, set up, and run the app + backend locally.
